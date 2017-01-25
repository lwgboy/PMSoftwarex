package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.Transporter;
import org.tarak.pms.repositories.TransporterRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class TransporterServiceImplementation implements ServiceInterface<Transporter, Integer>
{
    @Autowired
    private TransporterRepository repository;

    @Override
    public List<Transporter> findAll() {
        return repository.findAll();
    }

    @Override
    public Transporter saveAndFlush(Transporter transporter) {
        return repository.saveAndFlush(transporter);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Transporter findOne(Integer id) {
        return repository.findOne(id);
    }
}
