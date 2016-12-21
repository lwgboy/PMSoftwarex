package org.tarak.pms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.ProductType;
import org.tarak.pms.repositories.ProductTypeRepository;

import java.util.List;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class ProductTypeServiceImplementation implements ServiceInterface<ProductType, Integer>
{
    @Autowired
    private ProductTypeRepository repository;

    @Override
    public List<ProductType> findAll() {
        return repository.findAll();
    }

    @Override
    public ProductType saveAndFlush(ProductType productType) {
        return repository.saveAndFlush(productType);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public ProductType findOne(Integer id) {
        return repository.findOne(id);
    }
}
