package org.tarak.pms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tarak.pms.models.PurchaseInvoice;

/**
 * Created by Tarak on 12/7/2016.
 */
@Repository
public interface PurchaseInvoiceRepository extends JpaRepository<PurchaseInvoice, Integer> {
	public PurchaseInvoice findByPurchaseInvoiceIdAndFinYear(int purchaseOrderId, String finYear);
	public void deleteByPurchaseInvoiceIdAndFinYear(int purchaseInvoiceId, String finYear);
}