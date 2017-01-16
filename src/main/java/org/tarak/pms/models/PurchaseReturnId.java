package org.tarak.pms.models;

import java.io.Serializable;

public class PurchaseReturnId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int purchaseReturnId;
	private String finYear;
	public int getPurchaseReturnId() {
		return purchaseReturnId;
	}
	public void setPurchaseReturnId(int purchaseReturnId) {
		this.purchaseReturnId = purchaseReturnId;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
}
