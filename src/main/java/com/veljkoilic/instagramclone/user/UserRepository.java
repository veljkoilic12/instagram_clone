package com.veljkoilic.instagramclone.user;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	void deleteByUsername(String username);
	
	@Query(value = "SELECT * FROM user_details u WHERE u.is_user_deleted=false", nativeQuery = true)
	Collection<User> findAllActiveUsers();
}
