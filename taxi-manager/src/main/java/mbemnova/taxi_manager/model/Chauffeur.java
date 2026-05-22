package mbemnova.taxi_manager.model;



import jakarta.persistence.*;
import lombok.*;

/**
 * Entité représentant un chauffeur de taxi.
 */
@Entity
@Table(name = "chauffeurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chauffeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nom complet du chauffeur */
    @Column(nullable = false)
    private String nom;

    /** Numéro de téléphone */
    @Column(nullable = false, unique = true)
    private String telephone;

    /** Numéro de permis de conduire */
    @Column(nullable = false, unique = true)
    private String numeroPermis;

    /** Adresse du chauffeur */
    @Column
    private String adresse;

    /**
     * Le taxi affecté à ce chauffeur (relation optionnelle).
     * Un chauffeur peut ne pas avoir de taxi affecté.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taxi_id")
    private Taxi taxi;
}