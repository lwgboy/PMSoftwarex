package org.tarak.pms.utils;

import java.util.LinkedList;
import java.util.List;

import org.tarak.pms.models.PurchaseInvoice;
import org.tarak.pms.models.PurchaseInvoiceItem;
import org.tarak.pms.models.GoodsReceiveChallan;
import org.tarak.pms.models.GoodsReceiveChallanItem;

public class PurchaseInvoiceUtils {
	public static PurchaseInvoice populatePurchaseInvoice(GoodsReceiveChallan goodsReceiveChallan,PurchaseInvoice purchaseInvoice)
	{
		List<PurchaseInvoiceItem> purchaseInvoiceItems=populatePurchaseInvoiceItems(goodsReceiveChallan.getGoodsReceiveChallanItems());
		purchaseInvoice.setGoodsReceiveChallan(goodsReceiveChallan);
		double totalCost=0;
		for(PurchaseInvoiceItem purchaseInvoiceItem: purchaseInvoiceItems)
		{
			totalCost+=purchaseInvoiceItem.getRate()*purchaseInvoiceItem.getQuantity();
		}
		purchaseInvoice.setTotalCost(totalCost);
		purchaseInvoice.setPurchaseInvoiceItems(purchaseInvoiceItems);
		purchaseInvoice.setFinYear(goodsReceiveChallan.getFinYear());
		purchaseInvoice.setVendor(goodsReceiveChallan.getVendor());
		return purchaseInvoice;
	}

	public static List<PurchaseInvoiceItem> populatePurchaseInvoiceItems(List<GoodsReceiveChallanItem> goodsReceiveChallanItems) 
	{
		List<PurchaseInvoiceItem> purchaseInvoiceItems=new LinkedList<PurchaseInvoiceItem>(); 
		for(GoodsReceiveChallanItem goodsReceiveChallanItem: goodsReceiveChallanItems)
		{
			PurchaseInvoiceItem purchaseInvoiceItem=new PurchaseInvoiceItem();
			purchaseInvoiceItem.setBrand(goodsReceiveChallanItem.getBrand());
			purchaseInvoiceItem.setCategory(goodsReceiveChallanItem.getCategory());
			purchaseInvoiceItem.setDescription(goodsReceiveChallanItem.getDescription());
			purchaseInvoiceItem.setFinYear(goodsReceiveChallanItem.getFinYear());
			purchaseInvoiceItem.setMeasurement(goodsReceiveChallanItem.getMeasurement());
			purchaseInvoiceItem.setQuantity(goodsReceiveChallanItem.getQuantity());
			purchaseInvoiceItem.setRate(goodsReceiveChallanItem.getRate());
			purchaseInvoiceItem.setSrNo(goodsReceiveChallanItem.getSrNo());
			purchaseInvoiceItem.setStyle(goodsReceiveChallanItem.getStyle());
			purchaseInvoiceItem.setVariant(goodsReceiveChallanItem.getVariant());
			purchaseInvoiceItems.add(purchaseInvoiceItem);
		}
		return purchaseInvoiceItems;
	}
	

}
