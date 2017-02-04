package org.tarak.pms.barCode;

import java.util.List;

public class BarCode {
	List<String> select;
	boolean showLink;
	int goodsReceiveChallanId;
	String fileName;
	public List<String> getSelect() {
		return select;
	}

	public void setSelect(List<String> select) {
		this.select = select;
	}

	public boolean isShowLink() {
		return showLink;
	}

	public void setShowLink(boolean showLink) {
		this.showLink = showLink;
	}

	public int getGoodsReceiveChallanId() {
		return goodsReceiveChallanId;
	}

	public void setGoodsReceiveChallanId(int goodsReceiveChallanId) {
		this.goodsReceiveChallanId = goodsReceiveChallanId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
}
