package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepository extends JpaRepository<User,Long> {

    //Derived query
    User findByUserName(String username);

    @Transactional
    //@Transactional We are using for Derived queries
    //@Modifying we use with JPQL or Native queries
    void deleteByUserName(String username);

}
