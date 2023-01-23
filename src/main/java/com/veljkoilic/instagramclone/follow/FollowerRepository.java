package com.veljkoilic.instagramclone.follow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowerRepository extends JpaRepository<Follow, Integer> {
    Optional<Follow> findFollowByFollowerIdAndFollowingId(int followerId, int followingId);
}
