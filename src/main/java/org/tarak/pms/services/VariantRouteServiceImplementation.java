package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.VariantRoute;
import org.tarak.pms.repositories.VariantRouteRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class VariantRouteServiceImplementation implements ServiceInterface<VariantRoute, Integer>
{
    @Autowired
    private VariantRouteRepository repository;

    @Override
    public List<VariantRoute> findAll() {
        return repository.findAll();
    }

    @Override
    public VariantRoute saveAndFlush(VariantRoute variantRoute) {
        return repository.saveAndFlush(variantRoute);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public VariantRoute findOne(Integer id) {
        return repository.findOne(id);
    }
}
