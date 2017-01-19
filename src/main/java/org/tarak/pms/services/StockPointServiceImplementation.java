package org.tarak.pms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.StockPoint;
import org.tarak.pms.repositories.StockPointRepository;

import java.util.List;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class StockPointServiceImplementation implements ServiceInterface<StockPoint, Integer>
{
    @Autowired
    private StockPointRepository repository;

    @Override
    public List<StockPoint> findAll() {
        return repository.findAll();
    }

    @Override
    public StockPoint saveAndFlush(StockPoint stockPoint) {
        return repository.saveAndFlush(stockPoint);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public StockPoint findOne(Integer id) {
        return repository.findOne(id);
    }
}
