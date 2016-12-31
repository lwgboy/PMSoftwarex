package org.tarak.pms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.Route;
import org.tarak.pms.repositories.RouteRepository;

import java.util.List;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class RouteServiceImplementation implements ServiceInterface<Route, Integer>
{
    @Autowired
    private RouteRepository repository;

    @Override
    public List<Route> findAll() {
        return repository.findAll();
    }

    @Override
    public Route saveAndFlush(Route route) {
        return repository.saveAndFlush(route);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Route findOne(Integer id) {
        return repository.findOne(id);
    }
}
