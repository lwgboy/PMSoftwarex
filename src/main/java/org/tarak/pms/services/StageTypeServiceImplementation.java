package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.StageType;
import org.tarak.pms.repositories.StageTypeRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class StageTypeServiceImplementation implements ServiceInterface<StageType, Integer>
{
    @Autowired
    private StageTypeRepository repository;

    @Override
    public List<StageType> findAll() {
        return repository.findAll();
    }

    @Override
    public StageType saveAndFlush(StageType stage) {
        return repository.saveAndFlush(stage);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public StageType findOne(Integer id) {
        return repository.findOne(id);
    }
}
