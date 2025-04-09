package Mn.learning.SpringStarter.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Mn.learning.SpringStarter.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account , Long>{
    Optional<Account> findByEmailIgnoreCase(String email);

}
