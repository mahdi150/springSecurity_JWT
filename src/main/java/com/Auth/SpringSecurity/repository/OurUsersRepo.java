package com.Auth.SpringSecurity.repository;
import com.Auth.SpringSecurity.entity.OurUsers;
import java.util.* ;

import org.springframework.data.jpa.repository.JpaRepository;


public interface OurUsersRepo extends JpaRepository<OurUsers,Integer> {
	
	Optional<OurUsers> findByEmail(String email);

}




