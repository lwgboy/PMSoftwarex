package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.Buyer;
import org.tarak.pms.repositories.BuyerRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class BuyerServiceImplementation implements ServiceInterface<Buyer, Integer>
{
    @Autowired
    private BuyerRepository repository;

    @Override
    public List<Buyer> findAll() {
        return repository.findAll();
    }

    @Override
    public Buyer saveAndFlush(Buyer buyer) {
        return repository.saveAndFlush(buyer);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Buyer findOne(Integer id) {
        return repository.findOne(id);
    }
}
