package com.dkb.bankaccount.repository;

import com.dkb.bankaccount.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<BankAccount, Long> {
}
