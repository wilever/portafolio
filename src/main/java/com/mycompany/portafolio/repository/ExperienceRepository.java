package com.mycompany.portafolio.repository;

import com.mycompany.portafolio.domain.Experience;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Experience entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

}
