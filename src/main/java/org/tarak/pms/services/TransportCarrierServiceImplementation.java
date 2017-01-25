package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.TransportCarrier;
import org.tarak.pms.repositories.TransportCarrierRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class TransportCarrierServiceImplementation implements ServiceInterface<TransportCarrier, Integer>
{
    @Autowired
    private TransportCarrierRepository repository;

    @Override
    public List<TransportCarrier> findAll() {
        return repository.findAll();
    }

    @Override
    public TransportCarrier saveAndFlush(TransportCarrier transportCarrier) {
        return repository.saveAndFlush(transportCarrier);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public TransportCarrier findOne(Integer id) {
        return repository.findOne(id);
    }
}
