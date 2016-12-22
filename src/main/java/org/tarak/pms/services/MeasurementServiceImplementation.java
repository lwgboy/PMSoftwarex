package org.tarak.pms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.Measurement;
import org.tarak.pms.repositories.MeasurementRepository;

import java.util.List;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class MeasurementServiceImplementation implements ServiceInterface<Measurement, Integer>
{
    @Autowired
    private MeasurementRepository repository;

    @Override
    public List<Measurement> findAll() {
        return repository.findAll();
    }

    @Override
    public Measurement saveAndFlush(Measurement measurement) {
        return repository.saveAndFlush(measurement);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Measurement findOne(Integer id) {
        return repository.findOne(id);
    }
}
