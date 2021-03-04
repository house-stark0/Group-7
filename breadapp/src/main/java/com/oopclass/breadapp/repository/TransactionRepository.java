package com.oopclass.breadapp.repository;


import com.oopclass.breadapp.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	//User findByEmail(String email);
}
