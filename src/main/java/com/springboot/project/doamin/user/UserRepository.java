package com.springboot.project.doamin.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, Long> {


    @Query(value = "from User where username=:username")
    User findByUsername(@Param("username") String username);

    @Query(value = "from User where password=:password")
    User findByPassword(@Param("password") String password);

    @Query(value = "from User where name =:name and email=:email")
    User findByNM(@Param("name") String name, @Param("email") String email);

    @Query(value = "from User where username=:username and name =:name and email=:email")
    User findByUNM(@Param("username") String username, @Param("name") String name, @Param("email") String email);

    @Modifying
    @Query(value = "update User set password =:password, name =:name, email =:email where username =:username", nativeQuery = true)
    void updateUser(@Param("username") String username, @Param("password") String password, @Param("name") String name, @Param("email") String email) throws Exception;

}
