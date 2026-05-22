package mbemnova.taxi_manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mbemnova.taxi_manager.DTO.PaiementDTO;
import mbemnova.taxi_manager.DTO.PaiementResponseDTO;
import mbemnova.taxi_manager.service.PaiementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des paiements.
 * Base URL : /api/paiements
 */
@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
@Tag(name = "💳 Paiements", description = "Enregistrement et historique des paiements (Cash & Mobile Money)")
public class PaiementController {

    private final PaiementService paiementService;

    @Operation(summary = "Enregistrer un paiement",
            description = "Enregistre le paiement d'une course. Chaque course ne peut avoir qu'un seul paiement.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Paiement enregistré avec succès",
                    content = @Content(schema = @Schema(implementation = PaiementResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Paiement déjà existant pour cette course, ou données invalides"),
            @ApiResponse(responseCode = "404", description = "Course introuvable")
    })
    @PostMapping
    public ResponseEntity<PaiementResponseDTO> enregistrerPaiement(@Valid @RequestBody PaiementDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paiementService.enregistrerPaiement(dto));
    }

    @Operation(summary = "Historique de tous les paiements", description = "Retourne tous les paiements enregistrés dans le système.")
    @ApiResponse(responseCode = "200", description = "Historique retourné avec succès")
    @GetMapping
    public ResponseEntity<List<PaiementResponseDTO>> getTousLesPaiements() {
        return ResponseEntity.ok(paiementService.getTousLesPaiements());
    }

    @Operation(summary = "Trouver un paiement par ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paiement trouvé"),
            @ApiResponse(responseCode = "404", description = "Paiement introuvable")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaiementResponseDTO> getPaiementParId(
            @Parameter(description = "ID du paiement", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(paiementService.getPaiementParId(id));
    }

    @Operation(summary = "Paiement d'une course spécifique",
            description = "Retourne le paiement associé à une course donnée.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paiement trouvé"),
            @ApiResponse(responseCode = "404", description = "Aucun paiement pour cette course")
    })
    @GetMapping("/course/{courseId}")
    public ResponseEntity<PaiementResponseDTO> getPaiementParCourse(
            @Parameter(description = "ID de la course", example = "1", required = true)
            @PathVariable Long courseId) {
        return ResponseEntity.ok(paiementService.getPaiementParCourse(courseId));
    }
}