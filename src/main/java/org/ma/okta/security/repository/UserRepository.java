package org.ma.okta.security.repository;

import org.ma.okta.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    public User findByUserName(String userName);
    public User findByUserNameAndPassword(String userName, String password);
}
