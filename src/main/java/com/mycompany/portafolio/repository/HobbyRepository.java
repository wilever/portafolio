package com.mycompany.portafolio.repository;

import com.mycompany.portafolio.domain.Hobby;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Hobby entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Long> {

}
