package org.kolinek.restaurantorchestration;

import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

@WebService
public interface DinnerService {
	void organizeDinner(int numberOfPeople, XMLGregorianCalendar date) throws CannotOrganizeException;
}
