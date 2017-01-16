package org.tarak.pms.services;


import org.tarak.pms.models.PurchaseOrder;

/**
 * Created by Tarak on 12/7/2016.
 */
public interface PurchaseOrderService extends ServiceInterface<PurchaseOrder, Integer> {
	public PurchaseOrder findByPurchaseOrderIdAndFinYear(int purchaseOrderId,String finYear);
	public void deleteByPurchaseOrderIdAndFinYear(int purchaseOrderId, String finYear);
}
