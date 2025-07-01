package com.userService.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userService.user.model.Purchase;

@Repository
public interface PurchaseRepo extends JpaRepository<Purchase, Long>{

}
