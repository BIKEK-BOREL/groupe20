package mbemnova.taxi_manager.DTO;



import lombok.*;

/**
 * DTO de réponse pour l'affichage d'un chauffeur.
 */
import io.swagger.v3.oas.annotations.media.Schema;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Informations complètes d'un chauffeur")
public class ChauffeurResponseDTO {
    @Schema(description = "Identifiant unique du chauffeur", example = "1")
    private Long id;

    @Schema(description = "Nom complet", example = "Jean-Pierre Mbarga")
    private String nom;

    @Schema(description = "Numéro de téléphone", example = "+237690000001")
    private String telephone;

    @Schema(description = "Numéro de permis de conduire", example = "CMR-2020-001")
    private String numeroPermis;

    @Schema(description = "Adresse du domicile", example = "Akwa, Douala")
    private String adresse;

    @Schema(description = "ID du taxi affecté (null si aucun)", example = "1")
    private Long taxiId;

    @Schema(description = "Immatriculation du taxi affecté", example = "LT-1001-A")
    private String taxiImmatriculation;
}