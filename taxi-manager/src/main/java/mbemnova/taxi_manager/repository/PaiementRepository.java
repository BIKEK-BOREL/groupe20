package mbemnova.taxi_manager.repository;



import mbemnova.taxi_manager.model.MethodePaiement;
import mbemnova.taxi_manager.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Repository JPA pour la gestion des paiements.
 */
@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    boolean existsByCourseId(Long courseId);

    Optional<Paiement> findByCourseId(Long courseId);

    long countByMethodePaiement(MethodePaiement methode);

    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM Paiement p")
    BigDecimal sumMontantTotal();
}
