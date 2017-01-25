package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.Brand;
import org.tarak.pms.repositories.BrandRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class BrandServiceImplementation implements ServiceInterface<Brand, Integer>
{
    @Autowired
    private BrandRepository repository;

    @Override
    public List<Brand> findAll() {
        return repository.findAll();
    }

    @Override
    public Brand saveAndFlush(Brand brand) {
        return repository.saveAndFlush(brand);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Brand findOne(Integer id) {
        return repository.findOne(id);
    }
}
