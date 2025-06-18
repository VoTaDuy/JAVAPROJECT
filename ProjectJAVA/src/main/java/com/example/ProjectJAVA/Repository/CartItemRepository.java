package com.example.ProjectJAVA.Repository;

import com.example.ProjectJAVA.Entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Integer> {

    void deleteAllByCartsId(int id);

}
