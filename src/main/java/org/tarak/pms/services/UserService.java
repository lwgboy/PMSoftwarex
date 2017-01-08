package org.tarak.pms.services;


import org.tarak.pms.models.User;

/**
 * Created by Tarak on 12/7/2016.
 */
public interface UserService extends ServiceInterface<User, Integer> {
	public User findByUsername(String username);
}
