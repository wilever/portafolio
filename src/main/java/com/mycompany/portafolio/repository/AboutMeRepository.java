package com.mycompany.portafolio.repository;

import com.mycompany.portafolio.domain.AboutMe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AboutMe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AboutMeRepository extends JpaRepository<AboutMe, Long> {

}
