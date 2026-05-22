package mbemnova.taxi_manager.DTO;



import mbemnova.taxi_manager.model.CourseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO pour la création et la modification d'une course.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Données pour créer ou modifier une course")
public class CourseDTO {

    @Schema(description = "Lieu de prise en charge", example = "Akwa, Douala", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Le point de départ est obligatoire")
    private String pointDepart;

    @Schema(description = "Destination finale", example = "Bonamoussadi, Douala", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La destination est obligatoire")
    private String destination;

    @Schema(description = "Distance estimée en kilomètres", example = "8.5")
    private Double distance;

    @Schema(description = "Prix de la course en FCFA", example = "2500")
    private BigDecimal prix;

    @Schema(description = "Date et heure de la course (ISO 8601)", example = "2025-05-20T10:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La date de course est obligatoire")
    private LocalDateTime dateCourse;

    @Schema(description = "Statut initial de la course", example = "EN_ATTENTE", requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {"EN_ATTENTE", "EN_COURS", "TERMINEE", "ANNULEE"})
    @NotNull(message = "Le statut est obligatoire")
    private CourseStatus statut;

    @Schema(description = "ID du chauffeur assigné", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "L'ID du chauffeur est obligatoire")
    private Long chauffeurId;

    @Schema(description = "ID du client demandeur", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "L'ID du client est obligatoire")
    private Long clientId;

    @Schema(description = "ID du taxi utilisé", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "L'ID du taxi est obligatoire")
    private Long taxiId;
}
