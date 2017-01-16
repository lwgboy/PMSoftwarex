package org.tarak.pms.services;


import org.tarak.pms.models.PurchaseReturn;

/**
 * Created by Tarak on 12/7/2016.
 */
public interface PurchaseReturnService extends ServiceInterface<PurchaseReturn, Integer> {
	public PurchaseReturn findByPurchaseReturnIdAndFinYear(int id,String finYear);
	public void deleteByPurchaseReturnIdAndFinYear(int purchaseReturnId, String finYear);
}
