package org.tarak.pms.models;

import java.io.Serializable;

public class DeliveryChallanItemId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int deliveryChallanId;
	private String finYear;
	private int srNo;
	public int getDeliveryChallanId() {
		return deliveryChallanId;
	}
	public void setDeliveryChallanId(int deliveryChallanId) {
		this.deliveryChallanId = deliveryChallanId;
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
		result = prime * result + deliveryChallanId;
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
		DeliveryChallanItemId other = (DeliveryChallanItemId) obj;
		if (finYear == null) {
			if (other.finYear != null)
				return false;
		} else if (!finYear.equals(other.finYear))
			return false;
		if (deliveryChallanId != other.deliveryChallanId)
			return false;
		if (srNo != other.srNo)
			return false;
		return true;
	}
	
}
