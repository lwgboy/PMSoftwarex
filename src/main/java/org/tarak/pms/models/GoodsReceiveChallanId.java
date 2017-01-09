package org.tarak.pms.models;

import java.io.Serializable;

public class GoodsReceiveChallanId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long goodsReceiveChallanId;
	private String finYear;
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
}
