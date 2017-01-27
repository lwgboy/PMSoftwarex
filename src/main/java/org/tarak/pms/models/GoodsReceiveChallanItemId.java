package org.tarak.pms.models;

import java.io.Serializable;

public class GoodsReceiveChallanItemId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int goodsReceiveChallanId;
	private String finYear;
	private int srNo;
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
		result = prime * result + goodsReceiveChallanId;
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
		GoodsReceiveChallanItemId other = (GoodsReceiveChallanItemId) obj;
		if (finYear == null) {
			if (other.finYear != null)
				return false;
		} else if (!finYear.equals(other.finYear))
			return false;
		if (goodsReceiveChallanId != other.goodsReceiveChallanId)
			return false;
		if (srNo != other.srNo)
			return false;
		return true;
	}
	
}
