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
}
