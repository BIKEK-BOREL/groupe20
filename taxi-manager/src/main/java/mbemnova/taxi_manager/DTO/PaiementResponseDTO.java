package mbemnova.taxi_manager.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import mbemnova.taxi_manager.model.MethodePaiement;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de réponse pour l'affichage d'un paiement.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Informations complètes d'un paiement")
public class PaiementResponseDTO {

    @Schema(description = "Identifiant unique du paiement", example = "1")
    private Long id;

    @Schema(description = "Montant en FCFA", example = "2500")
    private BigDecimal montant;

    @Schema(description = "Méthode de paiement", example = "MOBILE_MONEY")
    private MethodePaiement methodePaiement;

    @Schema(description = "Date et heure du paiement", example = "2025-05-20T11:00:00")
    private LocalDateTime datePaiement;

    @Schema(description = "ID de la course concernée", example = "1")
    private Long courseId;

    @Schema(description = "Point de départ de la course", example = "Akwa, Douala")
    private String coursePointDepart;

    @Schema(description = "Destination de la course", example = "Bonamoussadi, Douala")
    private String courseDestination;
}