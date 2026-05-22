package mbemnova.taxi_manager.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

/**
 * DTO de réponse pour l'affichage d'un client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Informations complètes d'un client")
public class ClientResponseDTO {

    @Schema(description = "Identifiant unique du client", example = "1")
    private Long id;

    @Schema(description = "Nom complet", example = "Marie Mballa")
    private String nom;

    @Schema(description = "Numéro de téléphone", example = "+237670000001")
    private String telephone;

    @Schema(description = "Adresse email", example = "marie.mballa@gmail.com")
    private String email;
}