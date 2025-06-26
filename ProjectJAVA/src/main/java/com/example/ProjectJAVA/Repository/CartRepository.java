package com.example.ProjectJAVA.Repository;

import com.example.ProjectJAVA.Entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Carts, Integer> {

    Optional<Carts> findByUsers_id(int userId);
}
