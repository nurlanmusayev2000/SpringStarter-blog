package Mn.learning.SpringStarter.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Mn.learning.SpringStarter.models.Authority;

@Repository
public interface AutorityRepository extends JpaRepository<Authority,Long> {

}
