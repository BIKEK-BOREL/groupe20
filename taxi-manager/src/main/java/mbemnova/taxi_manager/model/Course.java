package mbemnova.taxi_manager.model;



import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entité représentant une course de taxi.
 */
@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Point de départ de la course */
    @Column(nullable = false)
    private String pointDepart;

    /** Destination de la course */
    @Column(nullable = false)
    private String destination;

    /** Distance en kilomètres */
    @Column
    private Double distance;

    /** Prix de la course en FCFA */
    @Column
    private BigDecimal prix;

    /** Date et heure de la course */
    @Column(nullable = false)
    private LocalDateTime dateCourse;

    /** Statut actuel de la course */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseStatus statut;

    /** Chauffeur assigné à cette course */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chauffeur_id", nullable = false)
    private Chauffeur chauffeur;

    /** Client demandeur de la course */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    /** Taxi utilisé pour la course */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taxi_id", nullable = false)
    private Taxi taxi;
}