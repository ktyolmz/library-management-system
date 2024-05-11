package com.ktyolmz.library.repository;

import com.ktyolmz.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    boolean existsByTelephoneIn(List<String> telephoneList);
}
