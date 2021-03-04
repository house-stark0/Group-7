package com.oopclass.breadapp.repository;


import com.oopclass.breadapp.models.Cancellation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Repository
public interface CancellationRepository extends JpaRepository<Cancellation, Long> {

	//User findByEmail(String email);
}
