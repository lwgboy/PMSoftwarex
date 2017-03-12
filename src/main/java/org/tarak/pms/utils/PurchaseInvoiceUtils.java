package org.tarak.pms.utils;

import java.util.LinkedList;
import java.util.List;

import org.tarak.pms.models.GoodsReceiveChallan;
import org.tarak.pms.models.GoodsReceiveChallanItem;
import org.tarak.pms.models.ProductItem;
import org.tarak.pms.models.PurchaseInvoice;
import org.tarak.pms.models.PurchaseInvoiceItem;
import org.tarak.pms.models.Variant;
import org.tarak.pms.services.ServiceInterface;

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
			goodsReceiveChallanItem.getProductItems().forEach(item->{
				PurchaseInvoiceItem purchaseInvoiceItem=new PurchaseInvoiceItem();
				purchaseInvoiceItem.setBrand(goodsReceiveChallanItem.getBrand());
				purchaseInvoiceItem.setDescription(goodsReceiveChallanItem.getDescription());
				purchaseInvoiceItem.setFinYear(goodsReceiveChallanItem.getFinYear());
				purchaseInvoiceItem.setMeasurement(goodsReceiveChallanItem.getMeasurement());
				purchaseInvoiceItem.setQuantity(goodsReceiveChallanItem.getQuantity());
				purchaseInvoiceItem.setRate(goodsReceiveChallanItem.getRate());
				purchaseInvoiceItem.setSrNo(goodsReceiveChallanItem.getSrNo());
				purchaseInvoiceItem.setStyle(goodsReceiveChallanItem.getStyle());
				List<ProductItem> piList=new LinkedList<ProductItem>();
				piList.add(item);
				purchaseInvoiceItem.setProductItems(piList);
				purchaseInvoiceItems.add(purchaseInvoiceItem);
			});
		}
		return purchaseInvoiceItems;
	}
	
	public static void updateVariants(PurchaseInvoice purchaseInvoice,PurchaseInvoice purchaseInvoiceO,ServiceInterface<Variant,Integer> variantService,boolean add,boolean fresh)
	{
		if(fresh)
		{
			purchaseInvoice.getPurchaseInvoiceItems().forEach(item->{
				item.getProductItems().forEach(productItem->{
					double udquantity=0;
					if(item.isDefective() && !productItem.getVariant().isDefective())
					{
						udquantity=item.getProductItems().stream().filter(piitem->piitem.getVariant().isDefective()).map(i->i.getQuantity()).reduce(0.0,Double::sum);
						udquantity=productItem.getQuantity()-udquantity;
					}
					else
					{
						udquantity=productItem.getQuantity();
					}
					Variant variant=variantService.findOne(productItem.getVariant().getId());
					if(add)
					{
						variant.setQuantity(variant.getQuantity()+udquantity);
					}
					else
					{
						variant.setQuantity(variant.getQuantity()-udquantity);
					}
					variantService.saveAndFlush(variant);
				});
			});
		}
		else
		{
			purchaseInvoice.getPurchaseInvoiceItems().forEach(item->{
				purchaseInvoiceO.getPurchaseInvoiceItems().forEach(itemO->{
					if(item.getSrNo()==itemO.getSrNo())
					{
						if(item.getQuantity()!=itemO.getQuantity())
						{
							double diff=item.getQuantity()-itemO.getQuantity();
							Variant variant=variantService.findOne(item.getProductItems().get(0).getVariant().getId());
							variant.setQuantity(variant.getQuantity()-diff);
							variantService.saveAndFlush(variant);
						}
					}
				});
			});
		}
	}
}
