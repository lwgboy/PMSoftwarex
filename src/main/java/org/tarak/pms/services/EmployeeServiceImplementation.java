package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.Employee;
import org.tarak.pms.repositories.EmployeeRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class EmployeeServiceImplementation implements ServiceInterface<Employee, Integer>
{
    @Autowired
    private EmployeeRepository repository;

    @Override
    public List<Employee> findAll() {
        return repository.findAll();
    }

    @Override
    public Employee saveAndFlush(Employee employee) {
        return repository.saveAndFlush(employee);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Employee findOne(Integer id) {
        return repository.findOne(id);
    }
}
