package com.example.vendingmachine.stats;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatRepository extends JpaRepository<Stat, Integer> {

    Optional<Stat> findByKey(StatKey key);
}
