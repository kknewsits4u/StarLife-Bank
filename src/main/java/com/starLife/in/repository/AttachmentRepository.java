package com.starLife.in.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.starLife.in.Entity.Customer;


@Repository
public interface AttachmentRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findBycImage(String cImage); // Assuming `cImage` is the column storing the image filename
}
