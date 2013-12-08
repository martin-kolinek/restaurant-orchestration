package org.kolinek.restaurantorchestration.impl;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.kolinek.restaurant_reservation.Reservation;
import org.kolinek.restaurant_reservation.ReservationService;
import org.kolinek.restaurantorchestration.CannotOrganizeException;
import org.kolinek.restaurantorchestration.DinnerService;
import org.kolinek.restaurantorder.Menu;
import org.kolinek.restaurantorder.NoSuchItemException_Exception;
import org.kolinek.restaurantorder.Order;
import org.kolinek.restaurantorder.OrderItem;
import org.kolinek.restaurantorder.OrderService;
import org.kolinek.restaurantpayment.PaymentService;
import org.kolinek.restaurantride.RideService;

public class DinnerServiceImpl implements DinnerService {
	private DatatypeFactory datatypeFactory;
	private ReservationService reserv;
	private PaymentService payment;
	private RideService ride;
	private OrderService order;

	public DatatypeFactory getDatatypeFactory() {
		return datatypeFactory;
	}

	public void setDatatypeFactory(DatatypeFactory datatypeFactory) {
		this.datatypeFactory = datatypeFactory;
	}

	public ReservationService getReserv() {
		return reserv;
	}

	public void setReserv(ReservationService reserv) {
		this.reserv = reserv;
	}

	public PaymentService getPayment() {
		return payment;
	}

	public void setPayment(PaymentService payment) {
		this.payment = payment;
	}

	public RideService getRide() {
		return ride;
	}

	public void setRide(RideService ride) {
		this.ride = ride;
	}

	public OrderService getOrder() {
		return order;
	}

	public void setOrder(OrderService order) {
		this.order = order;
	}

	@Override
	public void organizeDinner(int numberOfPeople, XMLGregorianCalendar date)
			throws CannotOrganizeException {

		Reservation reservation = new Reservation();
		reservation.setDate(date);
		reservation.setDuration(datatypeFactory.newDuration(true, 0, 0, 0, 4,
				0, 0));
		reservation.setNumberOfPeople(numberOfPeople);
		if (reserv.reserve(reservation))
			throw new CannotOrganizeException("Unable to reserve table");
		Menu m = order.getMenu();
		Order o = new Order();
		org.kolinek.restaurantpayment.Order payord = new org.kolinek.restaurantpayment.Order();
		OrderItem oitem = new OrderItem();
		org.kolinek.restaurantpayment.OrderItem poitem = new org.kolinek.restaurantpayment.OrderItem();
		int id = m.getItems().get(0).getId();
		oitem.setId(id);
		oitem.setQuantity(numberOfPeople);
		poitem.setId(id);
		poitem.setQuantity(numberOfPeople);
		o.getItems().add(oitem);
		payord.getItems().add(poitem);
		try {
			order.placeOrder(o);
		} catch (NoSuchItemException_Exception e) {
			throw new CannotOrganizeException("Dish not available");
		}
		payment.getPrice(payord);
		payment.pay(payord);
		XMLGregorianCalendar rideDate = (XMLGregorianCalendar) date.clone();
		rideDate.add(datatypeFactory.newDuration(false, 0, 0, 0, 1, 0, 0));
		ride.reserveRide(numberOfPeople, rideDate);
	}
}
