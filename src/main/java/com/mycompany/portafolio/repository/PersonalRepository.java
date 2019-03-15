package com.mycompany.portafolio.repository;

import com.mycompany.portafolio.domain.Personal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Personal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long> {

}
