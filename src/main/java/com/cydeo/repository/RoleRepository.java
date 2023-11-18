package com.cydeo.repository;

import com.cydeo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    /**

    1.build all queries that will bring data from db
    2.We have JPA Repository of Spring to use ready queries,
     if those query conditions are not enough, we can follow below step
    3. we can write derived queries or @Query(JPQL or Native query)

     JPA Repository has more than 20 different types of ready queries that we can use
     save(), findAll().....
  */
}
