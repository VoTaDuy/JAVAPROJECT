package com.example.ProjectJAVA.Repository;

import com.example.ProjectJAVA.Entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Carts, Integer> {

}
