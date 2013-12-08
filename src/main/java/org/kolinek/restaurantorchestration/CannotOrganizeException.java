package org.kolinek.restaurantorchestration;

public class CannotOrganizeException extends Exception {
	private static final long serialVersionUID = 1L;

	private String reason;

	public CannotOrganizeException(String reason) {
		this.setReason(reason);
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
