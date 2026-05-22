package mbemnova.taxi_manager.model;


import jakarta.persistence.*;
import lombok.*;

/**
 * Entité représentant un client du service de taxi.
 */
@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nom complet du client */
    @Column(nullable = false)
    private String nom;

    /** Numéro de téléphone */
    @Column(nullable = false, unique = true)
    private String telephone;

    /** Adresse email */
    @Column(unique = true)
    private String email;
}
