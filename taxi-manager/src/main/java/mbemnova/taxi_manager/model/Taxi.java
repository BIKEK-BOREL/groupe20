package mbemnova.taxi_manager.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entité représentant un taxi dans le système.
 */
@Entity
@Table(name = "taxis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Taxi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Plaque d'immatriculation du taxi
     */
    @Column(nullable = false, unique = true)
    private String immatriculation;

    /**
     * Marque du véhicule (ex: Toyota, Renault)
     */
    @Column(nullable = false)
    private String marque;

    /**
     * Couleur du véhicule
     */
    @Column(nullable = false)
    private String couleur;

    /**
     * Nombre de places
     */
    @Column(nullable = false)
    private Integer capacite;

    /**
     * Statut actuel du taxi
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaxiStatus statut;

    /**
     * Catégorie du véhicule
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeVehicule typeVehicule;
}
