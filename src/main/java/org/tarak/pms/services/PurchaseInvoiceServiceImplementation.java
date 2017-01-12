package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.PurchaseInvoice;
import org.tarak.pms.repositories.PurchaseInvoiceRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class PurchaseInvoiceServiceImplementation implements PurchaseInvoiceService
{
    @Autowired
    private PurchaseInvoiceRepository repository;

    @Override
    public List<PurchaseInvoice> findAll() {
        return repository.findAll();
    }

    @Override
    public PurchaseInvoice saveAndFlush(PurchaseInvoice purchaseInvoice) {
        return repository.saveAndFlush(purchaseInvoice);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public PurchaseInvoice findOne(Integer id) {
        return repository.findOne(id);
    }
    
	@Override
	public PurchaseInvoice findByPurchaseInvoiceIdAndFinYear(int id, String finYear) {
		return repository.findByPurchaseInvoiceIdAndFinYear(id, finYear);
	}
    
 }
