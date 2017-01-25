package org.tarak.pms.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.User;
import org.tarak.pms.repositories.UserRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
@Transactional
public class UserServiceImplementation implements UserService
{
    @Autowired
    private UserRepository repository;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User saveAndFlush(User user) {
        return repository.saveAndFlush(user);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public User findOne(Integer id) {
        return repository.findOne(id);
    }
    
    public User findByUsername(String username)
    {
    	return repository.findByUsername(username);
    }
}
