package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.Warehouse;
import org.tarak.pms.repositories.WarehouseRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class WarehouseServiceImplementation implements ServiceInterface<Warehouse, Integer>
{
    @Autowired
    private WarehouseRepository repository;

    @Override
    public List<Warehouse> findAll() {
        return repository.findAll();
    }

    @Override
    public Warehouse saveAndFlush(Warehouse warehouse) {
        return repository.saveAndFlush(warehouse);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Warehouse findOne(Integer id) {
        return repository.findOne(id);
    }
}
