package com.hotel.usermanagement.repositories;

import com.hotel.usermanagement.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    @Query("select u from user u where id= :userId")
    User findById(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("delete  from user  where id= :userId")
    void deleteById(@Param("userId") Long userId);

    @Query("select u from user u where email= :mail")
    User findByEmail(@Param("mail") String mail);

    @Query("select u from user u where email= :mail and password = :password")
    User signIn(@Param("mail") String mail, @Param("password") String password);

}
