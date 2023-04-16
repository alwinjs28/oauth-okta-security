package org.ma.okta.security.service;

import org.ma.okta.security.exception.UserNotFoundException;
import org.ma.okta.security.model.User;
import org.ma.okta.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserByName(String name) throws UserNotFoundException {
        User user = userRepository.findByUserName(name);
        return user;
    }
    @Override
    public User findByUserNameAndPassword(String name, String password) throws UserNotFoundException {
        User user = userRepository.findByUserNameAndPassword(name, password);
        return user;
    }
}
