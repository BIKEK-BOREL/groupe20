package mbemnova.taxi_manager.repository;


import mbemnova.taxi_manager.model.Chauffeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository JPA pour la gestion des chauffeurs.
 */
@Repository
public interface ChauffeurRepository extends JpaRepository<Chauffeur, Long> {

    /** Vérifie si un numéro de téléphone existe déjà */
    boolean existsByTelephone(String telephone);

    /** Vérifie si un numéro de permis existe déjà */
    boolean existsByNumeroPermis(String numeroPermis);

    /** Cherche par numéro de téléphone */
    Optional<Chauffeur> findByTelephone(String telephone);
}
