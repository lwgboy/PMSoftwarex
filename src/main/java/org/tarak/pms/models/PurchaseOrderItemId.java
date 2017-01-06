package org.tarak.pms.models;

import java.io.Serializable;

public class PurchaseOrderItemId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long purchaseOrderId;
	private String finYear;
	private int srNo;
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
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
}
