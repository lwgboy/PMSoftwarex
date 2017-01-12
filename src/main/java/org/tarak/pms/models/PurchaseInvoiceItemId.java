package org.tarak.pms.models;

import java.io.Serializable;

public class PurchaseInvoiceItemId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int purchaseInvoiceId;
	private String finYear;
	private int srNo;
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
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
}
