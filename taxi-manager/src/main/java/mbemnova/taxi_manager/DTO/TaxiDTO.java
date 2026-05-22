package mbemnova.taxi_manager.DTO;



import mbemnova.taxi_manager.model.TaxiStatus;
import mbemnova.taxi_manager.model.TypeVehicule;
import jakarta.validation.constraints.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * DTO pour la création et la modification d'un taxi.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Données pour créer ou modifier un taxi")
public class TaxiDTO {

    @Schema(description = "Plaque d'immatriculation unique du véhicule", example = "LT-1234-A", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "L'immatriculation est obligatoire")
    private String immatriculation;

    @Schema(description = "Marque et modèle du véhicule", example = "Toyota Corolla", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La marque est obligatoire")
    private String marque;

    @Schema(description = "Couleur du véhicule", example = "Blanc", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La couleur est obligatoire")
    private String couleur;

    @Schema(description = "Nombre de places passagers", example = "4", minimum = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La capacité est obligatoire")
    @Min(value = 1, message = "La capacité doit être au moins 1")
    private Integer capacite;

    @Schema(description = "Statut actuel du taxi", example = "DISPONIBLE", requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {"DISPONIBLE", "OCCUPE", "EN_PANNE"})
    @NotNull(message = "Le statut est obligatoire")
    private TaxiStatus statut;

    @Schema(description = "Catégorie du véhicule", example = "ECONOMIQUE", requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {"ECONOMIQUE", "VIP", "BUS"})
    @NotNull(message = "Le type de véhicule est obligatoire")
    private TypeVehicule typeVehicule;
}
