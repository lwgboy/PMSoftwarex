package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.GoodsReceiveChallanItem;
import org.tarak.pms.repositories.GoodsReceiveChallanItemRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class GoodsReceiveChallanItemServiceImplementation implements ServiceInterface<GoodsReceiveChallanItem, Integer>
{
    @Autowired
    private GoodsReceiveChallanItemRepository repository;

    @Override
    public List<GoodsReceiveChallanItem> findAll() {
        return repository.findAll();
    }

    @Override
    public GoodsReceiveChallanItem saveAndFlush(GoodsReceiveChallanItem productLineItem) {
        return repository.saveAndFlush(productLineItem);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public GoodsReceiveChallanItem findOne(Integer id) {
        return repository.findOne(id);
    }
 }
