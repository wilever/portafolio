package com.mycompany.portafolio.repository;

import com.mycompany.portafolio.domain.SocialAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SocialAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {

}
