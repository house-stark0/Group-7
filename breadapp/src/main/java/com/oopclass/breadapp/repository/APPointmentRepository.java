package com.oopclass.breadapp.repository;


import com.oopclass.breadapp.models.APPointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Repository
public interface APPointmentRepository extends JpaRepository<APPointment, Long> {

	//User findByEmail(String email);
}
