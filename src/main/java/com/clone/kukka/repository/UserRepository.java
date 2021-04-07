package com.clone.kukka.repository;

import com.clone.kukka.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities") // 해당 쿼리가 수행될 때, Lazy 조회가 아니고, Eager 조회로 authorities 정보를 같이 가져오게 함
    Optional<User> findOneWithAuthoritiesByUsername(String username); // username 기준으로 authorities(권한 정보들)도 가져옴
    Optional<User> findByUserId(String id);
}
