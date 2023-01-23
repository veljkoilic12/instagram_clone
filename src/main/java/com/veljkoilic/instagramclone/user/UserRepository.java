package com.veljkoilic.instagramclone.user;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.veljkoilic.instagramclone.user.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    void deleteByUsername(String username);

    @Query(value = "SELECT * FROM user_details u WHERE u.is_user_deleted=false", nativeQuery = true)
    Collection<User> findAllActiveUsers();

    @Query(value = "SELECT u FROM user_details u WHERE u.username LIKE %?1% AND u.isUserDeleted=false")
    List<User> search(String username);
}
