package mbemnova.taxi_manager.DTO;



import mbemnova.taxi_manager.model.MethodePaiement;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * DTO pour l'enregistrement d'un paiement.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Données pour enregistrer un paiement")
public class PaiementDTO {

    @Schema(description = "Montant payé en FCFA", example = "2500", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.01", message = "Le montant doit être positif")
    private BigDecimal montant;

    @Schema(description = "Méthode de paiement utilisée", example = "MOBILE_MONEY", requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {"CASH", "MOBILE_MONEY"})
    @NotNull(message = "La méthode de paiement est obligatoire")
    private MethodePaiement methodePaiement;

    @Schema(description = "Date et heure du paiement", example = "2025-05-20T11:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La date de paiement est obligatoire")
    private LocalDateTime datePaiement;

    @Schema(description = "ID de la course à payer", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "L'ID de la course est obligatoire")
    private Long courseId;
}
