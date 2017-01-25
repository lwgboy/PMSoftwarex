package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.Vendor;
import org.tarak.pms.repositories.VendorRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class VendorServiceImplementation implements ServiceInterface<Vendor, Integer>
{
    @Autowired
    private VendorRepository repository;

    @Override
    public List<Vendor> findAll() {
        return repository.findAll();
    }

    @Override
    public Vendor saveAndFlush(Vendor vendor) {
        return repository.saveAndFlush(vendor);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Vendor findOne(Integer id) {
        return repository.findOne(id);
    }
}
