package mbemnova.taxi_manager.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mbemnova.taxi_manager.DTO.TaxiDTO;
import mbemnova.taxi_manager.DTO.TaxiResponseDTO;
import mbemnova.taxi_manager.service.TaxiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des taxis.
 * Base URL : /api/taxis
 */
@RestController
@RequestMapping("/api/taxis")
@RequiredArgsConstructor
@Tag(name = "🚗 Taxis", description = "Gestion du parc de taxis urbains")
public class TaxiController {

    private final TaxiService taxiService;

    @Operation(summary = "Créer un taxi", description = "Enregistre un nouveau taxi dans le système. L'immatriculation doit être unique.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Taxi créé avec succès",
                    content = @Content(schema = @Schema(implementation = TaxiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides ou immatriculation déjà existante",
                    content = @Content(schema = @Schema(implementation = java.util.Map.class)))
    })
    @PostMapping
    public ResponseEntity<TaxiResponseDTO> creerTaxi(@Valid @RequestBody TaxiDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taxiService.creerTaxi(dto));
    }

    @Operation(summary = "Liste tous les taxis", description = "Retourne la liste complète de tous les taxis enregistrés.")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GetMapping
    public ResponseEntity<List<TaxiResponseDTO>> getTousLesTaxis() {
        return ResponseEntity.ok(taxiService.getTousLesTaxis());
    }

    @Operation(summary = "Trouver un taxi par ID", description = "Retourne les détails d'un taxi spécifique.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Taxi trouvé"),
            @ApiResponse(responseCode = "404", description = "Taxi introuvable")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaxiResponseDTO> getTaxiParId(
            @Parameter(description = "ID du taxi", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(taxiService.getTaxiParId(id));
    }

    @Operation(summary = "Modifier un taxi", description = "Met à jour les informations d'un taxi existant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Taxi modifié avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Taxi introuvable")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaxiResponseDTO> modifierTaxi(
            @Parameter(description = "ID du taxi à modifier", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody TaxiDTO dto) {
        return ResponseEntity.ok(taxiService.modifierTaxi(id, dto));
    }

    @Operation(summary = "Supprimer un taxi", description = "Supprime définitivement un taxi du système.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Taxi supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Taxi introuvable")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerTaxi(
            @Parameter(description = "ID du taxi à supprimer", example = "1", required = true)
            @PathVariable Long id) {
        taxiService.supprimerTaxi(id);
        return ResponseEntity.noContent().build();
    }
}