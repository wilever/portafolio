package com.mycompany.portafolio.repository;

import com.mycompany.portafolio.domain.Languague;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Languague entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LanguagueRepository extends JpaRepository<Languague, Long> {

}
