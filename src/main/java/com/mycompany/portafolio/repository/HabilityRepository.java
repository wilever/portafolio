package com.mycompany.portafolio.repository;

import com.mycompany.portafolio.domain.Hability;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Hability entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HabilityRepository extends JpaRepository<Hability, Long> {

}
