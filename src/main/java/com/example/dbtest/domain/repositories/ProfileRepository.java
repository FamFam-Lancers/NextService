package com.example.dbtest.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dbtest.domain.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
	Optional<Profile> findByUserInfoId(int userInfoId);
	void deleteById(int userInfoId);
	boolean existsByUserInfoId(int userInfoId);
}
