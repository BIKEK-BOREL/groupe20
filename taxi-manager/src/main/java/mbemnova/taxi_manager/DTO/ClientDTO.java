package mbemnova.taxi_manager.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO pour la création et la modification d'un client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Données pour créer ou modifier un client")
public class ClientDTO {

    @Schema(description = "Nom complet du client", example = "Marie Mballa", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @Schema(description = "Numéro de téléphone", example = "+237670000001", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Le téléphone est obligatoire")
    private String telephone;

    @Schema(description = "Adresse email (optionnelle)", example = "marie.mballa@gmail.com")
    @Email(message = "L'email doit être valide")
    private String email;
}
