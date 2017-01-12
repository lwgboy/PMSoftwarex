package org.tarak.pms.models;

import java.io.Serializable;

public class PurchaseInvoiceId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int purchaseInvoiceId;
	private String finYear;
	public int getPurchaseInvoiceId() {
		return purchaseInvoiceId;
	}
	public void setPurchaseInvoiceId(int purchaseInvoiceId) {
		this.purchaseInvoiceId = purchaseInvoiceId;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
}
