package com.seamus.aibazaar.repository;

import com.seamus.aibazaar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    List<User> findByUsernameAndPassword(String username, String password);


}
