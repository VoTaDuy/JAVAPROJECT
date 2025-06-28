package com.example.ProjectJAVA.Repository;

import com.example.ProjectJAVA.Entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Integer> {
}
