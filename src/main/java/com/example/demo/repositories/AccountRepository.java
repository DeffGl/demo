package com.example.demo.repositories;

import com.example.demo.models.ClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<ClientAccount, Integer> {
}
