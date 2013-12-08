package org.kolinek.restaurantorchestration.client;

import javax.xml.datatype.DatatypeFactory;

import org.kolinek.restaurantorchestration.DinnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
	private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

	public static void main(String args[]) throws Exception {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"client-context.xml");
		DinnerService dinnerService = (DinnerService) context
				.getBean("dinnerService");

		DatatypeFactory f = DatatypeFactory.newInstance();
		dinnerService.organizeDinner(10,
				f.newXMLGregorianCalendar(2013, 12, 10, 20, 0, 0, 0, 1));
		LOGGER.info("organized dinner");
		context.close();
		System.exit(0);
	}
}
