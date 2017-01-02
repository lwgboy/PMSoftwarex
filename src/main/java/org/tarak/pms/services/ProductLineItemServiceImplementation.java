package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.ProductLineItem;
import org.tarak.pms.repositories.ProductLineItemRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class ProductLineItemServiceImplementation implements ServiceInterface<ProductLineItem, Integer>
{
    @Autowired
    private ProductLineItemRepository repository;

    @Override
    public List<ProductLineItem> findAll() {
        return repository.findAll();
    }

    @Override
    public ProductLineItem saveAndFlush(ProductLineItem productLineItem) {
        return repository.saveAndFlush(productLineItem);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public ProductLineItem findOne(Integer id) {
        return repository.findOne(id);
    }
 }
