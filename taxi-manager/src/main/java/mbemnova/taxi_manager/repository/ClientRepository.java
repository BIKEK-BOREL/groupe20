package mbemnova.taxi_manager.repository;



import mbemnova.taxi_manager.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository JPA pour la gestion des clients.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    /** Vérifie si un numéro de téléphone existe déjà */
    boolean existsByTelephone(String telephone);

    /** Vérifie si un email existe déjà */
    boolean existsByEmail(String email);

    /** Cherche par email */
    Optional<Client> findByEmail(String email);
}