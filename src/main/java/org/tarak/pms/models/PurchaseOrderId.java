package org.tarak.pms.models;

import java.io.Serializable;

public class PurchaseOrderId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long purchaseOrderId;
	private String finYear;
	public long getPurchaseOrderId() {
		return purchaseOrderId;
	}
	public void setPurchaseOrderId(long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
}
