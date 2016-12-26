package org.tarak.pms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.Product;
import org.tarak.pms.repositories.ProductRepository;

import java.util.List;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class ProductServiceImplementation implements ServiceInterface<Product, Integer>
{
    @Autowired
    private ProductRepository repository;

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Product saveAndFlush(Product product) {
        return repository.saveAndFlush(product);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Product findOne(Integer id) {
        return repository.findOne(id);
    }
}
