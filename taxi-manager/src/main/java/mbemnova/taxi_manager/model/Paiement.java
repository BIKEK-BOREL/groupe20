package mbemnova.taxi_manager.model;



import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entité représentant un paiement pour une course.
 */
@Entity
@Table(name = "paiements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Montant payé en FCFA */
    @Column(nullable = false)
    private BigDecimal montant;

    /** Méthode de paiement utilisée */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MethodePaiement methodePaiement;

    /** Date et heure du paiement */
    @Column(nullable = false)
    private LocalDateTime datePaiement;

    /** Course associée à ce paiement */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false, unique = true)
    private Course course;
}