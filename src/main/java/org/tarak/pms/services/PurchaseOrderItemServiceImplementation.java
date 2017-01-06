package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.PurchaseOrderItem;
import org.tarak.pms.repositories.PurchaseOrderItemRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class PurchaseOrderItemServiceImplementation implements ServiceInterface<PurchaseOrderItem, Integer>
{
    @Autowired
    private PurchaseOrderItemRepository repository;

    @Override
    public List<PurchaseOrderItem> findAll() {
        return repository.findAll();
    }

    @Override
    public PurchaseOrderItem saveAndFlush(PurchaseOrderItem productLineItem) {
        return repository.saveAndFlush(productLineItem);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public PurchaseOrderItem findOne(Integer id) {
        return repository.findOne(id);
    }
 }
