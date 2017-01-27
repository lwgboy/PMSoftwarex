package org.tarak.pms.models;

import java.io.Serializable;

public class PurchaseOrderItemId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int purchaseOrderId;
	private String finYear;
	private int srNo;
	public int getPurchaseOrderId() {
		return purchaseOrderId;
	}
	public void setPurchaseOrderId(int purchaseOrderId) {
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((finYear == null) ? 0 : finYear.hashCode());
		result = prime * result + purchaseOrderId;
		result = prime * result + srNo;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PurchaseOrderItemId other = (PurchaseOrderItemId) obj;
		if (finYear == null) {
			if (other.finYear != null)
				return false;
		} else if (!finYear.equals(other.finYear))
			return false;
		if (purchaseOrderId != other.purchaseOrderId)
			return false;
		if (srNo != other.srNo)
			return false;
		return true;
	}
	
	
}
