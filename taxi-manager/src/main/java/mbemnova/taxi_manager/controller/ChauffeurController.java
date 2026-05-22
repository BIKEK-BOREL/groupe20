package mbemnova.taxi_manager.controller;



import mbemnova.taxi_manager.DTO.ChauffeurDTO;
import mbemnova.taxi_manager.DTO.ChauffeurResponseDTO;
import mbemnova.taxi_manager.service.ChauffeurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des chauffeurs.
 * Base URL : /api/chauffeurs
 */
@RestController
@RequestMapping("/api/chauffeurs")
@RequiredArgsConstructor
@Tag(name = "👨‍✈️ Chauffeurs", description = "Gestion des chauffeurs et affectation des taxis")
public class ChauffeurController {

    private final ChauffeurService chauffeurService;

    @Operation(summary = "Créer un chauffeur", description = "Enregistre un nouveau chauffeur. Le téléphone et le numéro de permis doivent être uniques.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Chauffeur créé avec succès",
                    content = @Content(schema = @Schema(implementation = ChauffeurResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides ou doublons détectés"),
            @ApiResponse(responseCode = "404", description = "Taxi introuvable si taxiId fourni")
    })
    @PostMapping
    public ResponseEntity<ChauffeurResponseDTO> creerChauffeur(@Valid @RequestBody ChauffeurDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chauffeurService.creerChauffeur(dto));
    }

    @Operation(summary = "Liste tous les chauffeurs", description = "Retourne la liste complète des chauffeurs avec leur taxi affecté.")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GetMapping
    public ResponseEntity<List<ChauffeurResponseDTO>> getTousLesChauffeurs() {
        return ResponseEntity.ok(chauffeurService.getTousLesChauffeurs());
    }

    @Operation(summary = "Trouver un chauffeur par ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Chauffeur trouvé"),
            @ApiResponse(responseCode = "404", description = "Chauffeur introuvable")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ChauffeurResponseDTO> getChauffeurParId(
            @Parameter(description = "ID du chauffeur", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(chauffeurService.getChauffeurParId(id));
    }

    @Operation(summary = "Modifier un chauffeur", description = "Met à jour les informations d'un chauffeur et/ou change son taxi affecté.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Chauffeur modifié avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Chauffeur ou taxi introuvable")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ChauffeurResponseDTO> modifierChauffeur(
            @Parameter(description = "ID du chauffeur à modifier", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody ChauffeurDTO dto) {
        return ResponseEntity.ok(chauffeurService.modifierChauffeur(id, dto));
    }

    @Operation(summary = "Affecter un taxi à un chauffeur",
            description = "Associe un taxi spécifique à un chauffeur. Remplace l'affectation précédente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Taxi affecté avec succès"),
            @ApiResponse(responseCode = "404", description = "Chauffeur ou taxi introuvable")
    })
    @PatchMapping("/{id}/taxi/{taxiId}")
    public ResponseEntity<ChauffeurResponseDTO> affecterTaxi(
            @Parameter(description = "ID du chauffeur", example = "1", required = true) @PathVariable Long id,
            @Parameter(description = "ID du taxi à affecter", example = "2", required = true) @PathVariable Long taxiId) {
        return ResponseEntity.ok(chauffeurService.affecterTaxi(id, taxiId));
    }

    @Operation(summary = "Supprimer un chauffeur")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Chauffeur supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Chauffeur introuvable")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerChauffeur(
            @Parameter(description = "ID du chauffeur à supprimer", example = "1", required = true)
            @PathVariable Long id) {
        chauffeurService.supprimerChauffeur(id);
        return ResponseEntity.noContent().build();
    }
}
