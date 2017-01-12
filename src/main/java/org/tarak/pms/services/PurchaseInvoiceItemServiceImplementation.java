package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.PurchaseInvoiceItem;
import org.tarak.pms.repositories.PurchaseInvoiceItemRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class PurchaseInvoiceItemServiceImplementation implements ServiceInterface<PurchaseInvoiceItem, Integer>
{
    @Autowired
    private PurchaseInvoiceItemRepository repository;

    @Override
    public List<PurchaseInvoiceItem> findAll() {
        return repository.findAll();
    }

    @Override
    public PurchaseInvoiceItem saveAndFlush(PurchaseInvoiceItem productLineItem) {
        return repository.saveAndFlush(productLineItem);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public PurchaseInvoiceItem findOne(Integer id) {
        return repository.findOne(id);
    }
 }
