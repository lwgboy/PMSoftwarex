package org.tarak.pms.models;

import java.io.Serializable;

public class GoodsReceiveChallanItemId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long goodsReceiveChallanId;
	private String finYear;
	private int srNo;
	public long getGoodsReceiveChallanId() {
		return goodsReceiveChallanId;
	}
	public void setGoodsReceiveChallanId(long goodsReceiveChallanId) {
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
}
