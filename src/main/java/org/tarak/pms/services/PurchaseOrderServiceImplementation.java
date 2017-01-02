package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.PurchaseOrder;
import org.tarak.pms.repositories.PurchaseOrderRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class PurchaseOrderServiceImplementation implements ServiceInterface<PurchaseOrder, Integer>
{
    @Autowired
    private PurchaseOrderRepository repository;

    @Override
    public List<PurchaseOrder> findAll() {
        return repository.findAll();
    }

    @Override
    public PurchaseOrder saveAndFlush(PurchaseOrder purchaseOrder) {
        return repository.saveAndFlush(purchaseOrder);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public PurchaseOrder findOne(Integer id) {
        return repository.findOne(id);
    }
 }
