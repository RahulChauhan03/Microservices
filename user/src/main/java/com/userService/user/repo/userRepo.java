package com.userService.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userService.user.model.User;

@Repository
public interface userRepo extends JpaRepository<User, Long>{

}
