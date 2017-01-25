package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.VariantType;
import org.tarak.pms.repositories.VariantTypeRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class VariantTypeServiceImplementation implements ServiceInterface<VariantType, Integer>
{
    @Autowired
    private VariantTypeRepository repository;

    @Override
    public List<VariantType> findAll() {
        return repository.findAll();
    }

    @Override
    public VariantType saveAndFlush(VariantType variant) {
        return repository.saveAndFlush(variant);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public VariantType findOne(Integer id) {
        return repository.findOne(id);
    }
}
