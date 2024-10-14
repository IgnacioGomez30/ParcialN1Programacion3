package org.example.parcial_n1.repositories;

import org.example.parcial_n1.entities.Adn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdnRepository extends JpaRepository<Adn, Long> {
    Optional<Adn> findByAdn(String adnSequence);

    long countByIsMutant(boolean isMutant);
}
