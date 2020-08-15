package com.dkb.bankaccount.repository;

import com.dkb.bankaccount.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findFirstByIban(String iban);
}
