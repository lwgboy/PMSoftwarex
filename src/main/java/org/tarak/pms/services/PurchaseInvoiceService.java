package org.tarak.pms.services;


import org.tarak.pms.models.PurchaseInvoice;

/**
 * Created by Tarak on 12/7/2016.
 */
public interface PurchaseInvoiceService extends ServiceInterface<PurchaseInvoice, Integer> {
	public PurchaseInvoice findByPurchaseInvoiceIdAndFinYear(int id,String finYear);
}
