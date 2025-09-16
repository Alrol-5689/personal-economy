package com.economy.persistence.jpa;

import com.economy.model.User;
import com.economy.persistence.dao.UserDao;

public class UserDaoJpa extends GenericDaoJpa<User, Long> implements UserDao {
    
    public UserDaoJpa() {
        super(User.class);
    }

}
