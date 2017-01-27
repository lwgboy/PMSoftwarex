package org.tarak.pms.models;

import java.io.Serializable;

public class GoodsReceiveChallanId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int goodsReceiveChallanId;
	private String finYear;
	public int getGoodsReceiveChallanId() {
		return goodsReceiveChallanId;
	}
	public void setGoodsReceiveChallanId(int goodsReceiveChallanId) {
		this.goodsReceiveChallanId = goodsReceiveChallanId;
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
		result = prime * result + goodsReceiveChallanId;
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
		GoodsReceiveChallanId other = (GoodsReceiveChallanId) obj;
		if (finYear == null) {
			if (other.finYear != null)
				return false;
		} else if (!finYear.equals(other.finYear))
			return false;
		if (goodsReceiveChallanId != other.goodsReceiveChallanId)
			return false;
		return true;
	}
	
}
