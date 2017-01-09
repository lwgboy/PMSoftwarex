package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.GoodsReceiveChallan;
import org.tarak.pms.repositories.GoodsReceiveChallanRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class GoodsReceiveChallanServiceImplementation implements ServiceInterface<GoodsReceiveChallan, Integer>
{
    @Autowired
    private GoodsReceiveChallanRepository repository;

    @Override
    public List<GoodsReceiveChallan> findAll() {
        return repository.findAll();
    }

    @Override
    public GoodsReceiveChallan saveAndFlush(GoodsReceiveChallan goodsReceiveChallan) {
        return repository.saveAndFlush(goodsReceiveChallan);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public GoodsReceiveChallan findOne(Integer id) {
        return repository.findOne(id);
    }
 }
