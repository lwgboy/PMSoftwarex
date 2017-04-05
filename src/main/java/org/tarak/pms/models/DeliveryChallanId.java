package org.tarak.pms.models;

import java.io.Serializable;

public class DeliveryChallanId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int deliveryChallanId;
	private String finYear;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((finYear == null) ? 0 : finYear.hashCode());
		result = prime * result + deliveryChallanId;
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
		DeliveryChallanId other = (DeliveryChallanId) obj;
		if (finYear == null) {
			if (other.finYear != null)
				return false;
		} else if (!finYear.equals(other.finYear))
			return false;
		if (deliveryChallanId != other.deliveryChallanId)
			return false;
		return true;
	}
	
}
