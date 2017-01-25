package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.Stage;
import org.tarak.pms.repositories.StageRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class StageServiceImplementation implements ServiceInterface<Stage, Integer>
{
    @Autowired
    private StageRepository repository;

    @Override
    public List<Stage> findAll() {
        return repository.findAll();
    }

    @Override
    public Stage saveAndFlush(Stage stage) {
        return repository.saveAndFlush(stage);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Stage findOne(Integer id) {
        return repository.findOne(id);
    }
}
