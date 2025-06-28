package com.example.ProjectJAVA.Repository;

import com.example.ProjectJAVA.Entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Integer> {
    List<Reviews> findAllByProducts_productId(Integer productId);
}
