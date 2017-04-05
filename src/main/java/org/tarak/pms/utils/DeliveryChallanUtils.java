package org.tarak.pms.utils;

import java.util.LinkedList;
import java.util.List;

import org.tarak.pms.models.DeliveryChallan;
import org.tarak.pms.models.DeliveryChallanItem;
import org.tarak.pms.models.ProductItem;
import org.tarak.pms.models.SalesOrder;
import org.tarak.pms.models.SalesOrderItem;

public class DeliveryChallanUtils 
{
	public static DeliveryChallan populateDeliveryChallan(SalesOrder salesOrder,DeliveryChallan deliveryChallan)
	{
		List<DeliveryChallanItem> deliveryChallanItems=populateDeliveryChallanItems(salesOrder.getSalesOrderItems());
		deliveryChallan.setSalesOrder(salesOrder);
		double totalCost=0;
		for(DeliveryChallanItem deliveryChallanItem: deliveryChallanItems)
		{
			totalCost+=deliveryChallanItem.getRate()*deliveryChallanItem.getQuantity();
		}
		deliveryChallan.setTotalCost(totalCost);
		deliveryChallan.setDeliveryChallanItems(deliveryChallanItems);
		deliveryChallan.setFinYear(salesOrder.getFinYear());
		deliveryChallan.setBuyer(salesOrder.getBuyer());
		return deliveryChallan;
	}

	public static List<DeliveryChallanItem> populateDeliveryChallanItems(List<SalesOrderItem> salesOrderItems) 
	{
		List<DeliveryChallanItem> deliveryChallanItems=new LinkedList<DeliveryChallanItem>(); 
		for(SalesOrderItem salesOrderItem: salesOrderItems)
		{
			if (salesOrderItem.isProcessed()) 
			{
				DeliveryChallanItem deliveryChallanItem = new DeliveryChallanItem();
				deliveryChallanItem.setBrand(salesOrderItem.getBrand());
				deliveryChallanItem.setDescription(salesOrderItem.getDescription());
				deliveryChallanItem.setFinYear(salesOrderItem.getFinYear());
				deliveryChallanItem.setMeasurement(salesOrderItem.getMeasurement());
				deliveryChallanItem.setQuantity(salesOrderItem.getQuantity());
				deliveryChallanItem.setRate(salesOrderItem.getRate());
				deliveryChallanItem.setSrNo(salesOrderItem.getSrNo());
				deliveryChallanItem.setStyle(salesOrderItem.getStyle());
				deliveryChallanItem.setPoDate(salesOrderItem.getDeliveryDate());
				deliveryChallanItem.setPoiSrNo(salesOrderItem.getSrNo());
				if (salesOrderItem.getProduct() != null) 
				{
					ProductItem productItem = new ProductItem();
					productItem.setProduct(salesOrderItem.getProduct());
					productItem.setVariant(salesOrderItem.getVariant());
					productItem.setQuantity(salesOrderItem.getQuantity());
					if (deliveryChallanItem.getProductItems() != null) 
					{
						deliveryChallanItem.getProductItems().add(productItem);
					} 
					else 
					{
						List<ProductItem> productItems = new LinkedList<ProductItem>();
						productItems.add(productItem);
						deliveryChallanItem.setProductItems(productItems);
					}
				}
				deliveryChallanItem.setDeliveryDate(salesOrderItem.getDeliveryDate());
				deliveryChallanItems.add(deliveryChallanItem);
			}
		}
		return deliveryChallanItems;
	}
	public static void setProcessed(DeliveryChallan deliveryChallan,boolean flag,SalesOrder po)
    {
    	deliveryChallan.getDeliveryChallanItems().forEach(grcitem->{
    		final int poiSrNo=grcitem.getPoiSrNo();
    		po.getSalesOrderItems().stream().filter(i->i.getSrNo()==poiSrNo).findFirst().ifPresent(item->item.setProcessed(flag));
    	});
    }
}
