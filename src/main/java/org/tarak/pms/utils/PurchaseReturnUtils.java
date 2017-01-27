package org.tarak.pms.utils;

import java.util.LinkedList;
import java.util.List;

import org.tarak.pms.models.PurchaseInvoice;
import org.tarak.pms.models.PurchaseInvoiceItem;
import org.tarak.pms.models.PurchaseReturn;
import org.tarak.pms.models.PurchaseReturnItem;

public class PurchaseReturnUtils {
	public static PurchaseReturn populatePurchaseReturn(PurchaseInvoice goodsReceiveChallan,PurchaseReturn purchaseReturn)
	{
		List<PurchaseReturnItem> purchaseReturnItems=populatePurchaseReturnItems(goodsReceiveChallan.getPurchaseInvoiceItems());
		purchaseReturn.setPurchaseInvoice(goodsReceiveChallan);
		double totalCost=0;
		for(PurchaseReturnItem purchaseReturnItem: purchaseReturnItems)
		{
			totalCost+=purchaseReturnItem.getRate()*purchaseReturnItem.getQuantity();
		}
		purchaseReturn.setTotalCost(totalCost);
		purchaseReturn.setPurchaseReturnItems(purchaseReturnItems);
		purchaseReturn.setFinYear(goodsReceiveChallan.getFinYear());
		purchaseReturn.setVendor(goodsReceiveChallan.getVendor());
		return purchaseReturn;
	}

	public static List<PurchaseReturnItem> populatePurchaseReturnItems(List<PurchaseInvoiceItem> goodsReceiveChallanItems) 
	{
		List<PurchaseReturnItem> purchaseReturnItems=new LinkedList<PurchaseReturnItem>(); 
		for(PurchaseInvoiceItem goodsReceiveChallanItem: goodsReceiveChallanItems)
		{
			PurchaseReturnItem purchaseReturnItem=new PurchaseReturnItem();
			purchaseReturnItem.setBrand(goodsReceiveChallanItem.getBrand());
			purchaseReturnItem.setDescription(goodsReceiveChallanItem.getDescription());
			purchaseReturnItem.setFinYear(goodsReceiveChallanItem.getFinYear());
			purchaseReturnItem.setMeasurement(goodsReceiveChallanItem.getMeasurement());
			purchaseReturnItem.setQuantity(goodsReceiveChallanItem.getQuantity());
			purchaseReturnItem.setRate(goodsReceiveChallanItem.getRate());
			purchaseReturnItem.setSrNo(goodsReceiveChallanItem.getSrNo());
			purchaseReturnItem.setStyle(goodsReceiveChallanItem.getStyle());
			//purchaseReturnItem.setVariant(goodsReceiveChallanItem.getVariant());
			purchaseReturnItems.add(purchaseReturnItem);
		}
		return purchaseReturnItems;
	}
	

}
