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
}
