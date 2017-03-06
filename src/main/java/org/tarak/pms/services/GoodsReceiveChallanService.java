package org.tarak.pms.services;


import org.tarak.pms.models.GoodsReceiveChallan;
import org.tarak.pms.models.Variant;

/**
 * Created by Tarak on 12/7/2016.
 */
public interface GoodsReceiveChallanService extends ServiceInterface<GoodsReceiveChallan, Integer> 
{
	public GoodsReceiveChallan findByGoodsReceiveChallanIdAndFinYear(int goodsReceiveChallanId,String finYear);
	public void deleteByGoodsReceiveChallanIdAndFinYear(int goodsReceiveChallanId, String finYear);
	public void saveAndProcessPO(GoodsReceiveChallan goodsReceiveChallan, GoodsReceiveChallanService goodsReceiveChallanService, PurchaseOrderService purchaseOrderService,ServiceInterface<Variant,Integer> variantService);
}
