package org.tarak.pms.utils;

import java.util.LinkedList;
import java.util.List;

import org.tarak.pms.models.GoodsReceiveChallan;
import org.tarak.pms.models.GoodsReceiveChallanItem;
import org.tarak.pms.models.ProductItem;
import org.tarak.pms.models.PurchaseOrder;
import org.tarak.pms.models.PurchaseOrderItem;

public class GoodsReceiveChallanUtils 
{
	public static GoodsReceiveChallan populateGoodsReceiveChallan(PurchaseOrder purchaseOrder,GoodsReceiveChallan goodsReceiveChallan)
	{
		List<GoodsReceiveChallanItem> goodsReceiveChallanItems=populateGoodsReceiveChallanItems(purchaseOrder.getPurchaseOrderItems());
		goodsReceiveChallan.setPurchaseOrder(purchaseOrder);
		double totalCost=0;
		for(GoodsReceiveChallanItem goodsReceiveChallanItem: goodsReceiveChallanItems)
		{
			totalCost+=goodsReceiveChallanItem.getRate()*goodsReceiveChallanItem.getQuantity();
		}
		goodsReceiveChallan.setTotalCost(totalCost);
		goodsReceiveChallan.setGoodsReceiveChallanItems(goodsReceiveChallanItems);
		goodsReceiveChallan.setFinYear(purchaseOrder.getFinYear());
		goodsReceiveChallan.setVendor(purchaseOrder.getVendor());
		return goodsReceiveChallan;
	}

	public static List<GoodsReceiveChallanItem> populateGoodsReceiveChallanItems(List<PurchaseOrderItem> purchaseOrderItems) 
	{
		List<GoodsReceiveChallanItem> goodsReceiveChallanItems=new LinkedList<GoodsReceiveChallanItem>(); 
		for(PurchaseOrderItem purchaseOrderItem: purchaseOrderItems)
		{
			if (!purchaseOrderItem.isProcessed()) 
			{
				GoodsReceiveChallanItem goodsReceiveChallanItem = new GoodsReceiveChallanItem();
				goodsReceiveChallanItem.setBrand(purchaseOrderItem.getBrand());
				goodsReceiveChallanItem.setDescription(purchaseOrderItem.getDescription());
				goodsReceiveChallanItem.setFinYear(purchaseOrderItem.getFinYear());
				goodsReceiveChallanItem.setMeasurement(purchaseOrderItem.getMeasurement());
				goodsReceiveChallanItem.setQuantity(purchaseOrderItem.getQuantity());
				goodsReceiveChallanItem.setRate(purchaseOrderItem.getRate());
				goodsReceiveChallanItem.setSrNo(purchaseOrderItem.getSrNo());
				goodsReceiveChallanItem.setStyle(purchaseOrderItem.getStyle());
				goodsReceiveChallanItem.setPoDate(purchaseOrderItem.getDeliveryDate());
				goodsReceiveChallanItem.setPoiSrNo(purchaseOrderItem.getSrNo());
				if (purchaseOrderItem.getProduct() != null) 
				{
					ProductItem productItem = new ProductItem();
					productItem.setProduct(purchaseOrderItem.getProduct());
					productItem.setVariant(purchaseOrderItem.getVariant());
					productItem.setQuantity(purchaseOrderItem.getQuantity());
					if (goodsReceiveChallanItem.getProductItems() != null) 
					{
						goodsReceiveChallanItem.getProductItems().add(productItem);
					} 
					else 
					{
						List<ProductItem> productItems = new LinkedList<ProductItem>();
						productItems.add(productItem);
						goodsReceiveChallanItem.setProductItems(productItems);
					}
				}
				goodsReceiveChallanItem.setDeliveryDate(purchaseOrderItem.getDeliveryDate());
				goodsReceiveChallanItems.add(goodsReceiveChallanItem);
			}
		}
		return goodsReceiveChallanItems;
	}
	public static void setProcessed(GoodsReceiveChallan goodsReceiveChallan,boolean flag,PurchaseOrder po)
    {
    	goodsReceiveChallan.getGoodsReceiveChallanItems().forEach(grcitem->{
    		final int poiSrNo=grcitem.getPoiSrNo();
    		po.getPurchaseOrderItems().stream().filter(i->i.getSrNo()==poiSrNo).findFirst().ifPresent(item->item.setProcessed(flag));
    	});
    }
}
