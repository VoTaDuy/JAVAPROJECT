package com.example.ProjectJAVA.Repository;

import com.example.ProjectJAVA.Entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payments, Integer> {
}
