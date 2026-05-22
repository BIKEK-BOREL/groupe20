package mbemnova.taxi_manager.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import mbemnova.taxi_manager.model.TaxiStatus;
import mbemnova.taxi_manager.model.TypeVehicule;
import lombok.*;

/**
 * DTO de réponse pour l'affichage d'un taxi.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Informations complètes d'un taxi")
public class TaxiResponseDTO {

    @Schema(description = "Identifiant unique du taxi", example = "1")
    private Long id;

    @Schema(description = "Plaque d'immatriculation", example = "LT-1001-A")
    private String immatriculation;

    @Schema(description = "Marque et modèle", example = "Toyota Corolla")
    private String marque;

    @Schema(description = "Couleur du véhicule", example = "Blanc")
    private String couleur;

    @Schema(description = "Nombre de places", example = "4")
    private Integer capacite;

    @Schema(description = "Statut actuel", example = "DISPONIBLE")
    private TaxiStatus statut;

    @Schema(description = "Type de véhicule", example = "ECONOMIQUE")
    private TypeVehicule typeVehicule;
}