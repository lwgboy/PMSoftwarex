package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.DeliveryChallanItem;
import org.tarak.pms.repositories.DeliveryChallanItemRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class DeliveryChallanItemServiceImplementation implements ServiceInterface<DeliveryChallanItem, Integer>
{
    @Autowired
    private DeliveryChallanItemRepository repository;

    @Override
    public List<DeliveryChallanItem> findAll() {
        return repository.findAll();
    }

    @Override
    public DeliveryChallanItem saveAndFlush(DeliveryChallanItem productLineItem) {
        return repository.saveAndFlush(productLineItem);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public DeliveryChallanItem findOne(Integer id) {
        return repository.findOne(id);
    }
 }
