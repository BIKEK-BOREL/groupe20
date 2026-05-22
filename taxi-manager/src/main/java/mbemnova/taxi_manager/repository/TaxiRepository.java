package mbemnova.taxi_manager.repository;



import mbemnova.taxi_manager.model.Taxi;
import mbemnova.taxi_manager.model.TaxiStatus;
import mbemnova.taxi_manager.model.TypeVehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository JPA pour la gestion des taxis.
 */
@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Long> {

    boolean existsByImmatriculation(String immatriculation);

    Optional<Taxi> findByImmatriculation(String immatriculation);

    List<Taxi> findByStatut(TaxiStatus statut);

    List<Taxi> findByTypeVehicule(TypeVehicule typeVehicule);

    long countByStatut(TaxiStatus statut);
}