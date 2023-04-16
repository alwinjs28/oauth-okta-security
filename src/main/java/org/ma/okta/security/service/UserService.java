package org.ma.okta.security.service;

import org.ma.okta.security.exception.UserNotFoundException;
import org.ma.okta.security.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public void saveUser(User user);
    public User getUserByName(String name) throws UserNotFoundException;
    public User findByUserNameAndPassword(String name, String password) throws UserNotFoundException;
}
