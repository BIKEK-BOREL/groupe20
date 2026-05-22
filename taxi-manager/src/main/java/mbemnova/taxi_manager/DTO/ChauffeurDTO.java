package mbemnova.taxi_manager.DTO;


import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO pour la création et la modification d'un chauffeur.
 */
import io.swagger.v3.oas.annotations.media.Schema;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Données pour créer ou modifier un chauffeur")
public class ChauffeurDTO {

    @Schema(description = "Nom complet du chauffeur", example = "Jean-Pierre Mbarga", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @Schema(description = "Numéro de téléphone (format camerounais)", example = "+237690000001", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Le téléphone est obligatoire")
    private String telephone;

    @Schema(description = "Numéro de permis de conduire", example = "CMR-2020-001", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Le numéro de permis est obligatoire")
    private String numeroPermis;

    @Schema(description = "Adresse du domicile", example = "Akwa, Douala")
    private String adresse;

    /** ID du taxi à affecter (optionnel) */
    @Schema(description = "ID du taxi à affecter (optionnel)", example = "1")
    private Long taxiId;
}