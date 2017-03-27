package org.tarak.pms.models;

import java.io.Serializable;

public class PickListId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pickListId;
	private String finYear;
	public int getPickListId() {
		return pickListId;
	}
	public void setPickListId(int pickListId) {
		this.pickListId = pickListId;
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
		result = prime * result + pickListId;
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
		PickListId other = (PickListId) obj;
		if (finYear == null) {
			if (other.finYear != null)
				return false;
		} else if (!finYear.equals(other.finYear))
			return false;
		if (pickListId != other.pickListId)
			return false;
		return true;
	}
	
}
