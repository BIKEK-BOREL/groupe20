package mbemnova.taxi_manager.DTO;



import mbemnova.taxi_manager.model.CourseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de réponse pour l'affichage d'une course.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Informations complètes d'une course")
public class CourseResponseDTO {

    @Schema(description = "Identifiant unique de la course", example = "1")
    private Long id;

    @Schema(description = "Lieu de départ", example = "Akwa, Douala")
    private String pointDepart;

    @Schema(description = "Destination", example = "Bonamoussadi, Douala")
    private String destination;

    @Schema(description = "Distance en km", example = "8.5")
    private Double distance;

    @Schema(description = "Prix en FCFA", example = "2500")
    private BigDecimal prix;

    @Schema(description = "Date et heure de la course", example = "2025-05-20T10:30:00")
    private LocalDateTime dateCourse;

    @Schema(description = "Statut actuel", example = "EN_ATTENTE")
    private CourseStatus statut;

    @Schema(description = "ID du chauffeur", example = "1")
    private Long chauffeurId;

    @Schema(description = "Nom du chauffeur", example = "Jean-Pierre Mbarga")
    private String chauffeurNom;

    @Schema(description = "ID du client", example = "1")
    private Long clientId;

    @Schema(description = "Nom du client", example = "Marie Mballa")
    private String clientNom;

    @Schema(description = "ID du taxi", example = "1")
    private Long taxiId;

    @Schema(description = "Immatriculation du taxi", example = "LT-1001-A")
    private String taxiImmatriculation;
}
