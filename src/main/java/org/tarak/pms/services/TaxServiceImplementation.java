package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.Tax;
import org.tarak.pms.repositories.TaxRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class TaxServiceImplementation implements ServiceInterface<Tax, Integer>
{
    @Autowired
    private TaxRepository repository;

    @Override
    public List<Tax> findAll() {
        return repository.findAll();
    }

    @Override
    public Tax saveAndFlush(Tax tax) {
        return repository.saveAndFlush(tax);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Tax findOne(Integer id) {
        return repository.findOne(id);
    }
}
