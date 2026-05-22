package mbemnova.taxi_manager.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mbemnova.taxi_manager.DTO.ClientDTO;
import mbemnova.taxi_manager.DTO.ClientResponseDTO;
import mbemnova.taxi_manager.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des clients.
 * Base URL : /api/clients
 */
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "👤 Clients", description = "Gestion des clients du service de taxi")
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Créer un client", description = "Enregistre un nouveau client. Le téléphone et l'email doivent être uniques.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Client créé avec succès",
                    content = @Content(schema = @Schema(implementation = ClientResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides ou doublons")
    })
    @PostMapping
    public ResponseEntity<ClientResponseDTO> creerClient(@Valid @RequestBody ClientDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.creerClient(dto));
    }

    @Operation(summary = "Liste tous les clients")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getTousLesClients() {
        return ResponseEntity.ok(clientService.getTousLesClients());
    }

    @Operation(summary = "Trouver un client par ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client trouvé"),
            @ApiResponse(responseCode = "404", description = "Client introuvable")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientParId(
            @Parameter(description = "ID du client", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientParId(id));
    }

    @Operation(summary = "Modifier un client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client modifié avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Client introuvable")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> modifierClient(
            @Parameter(description = "ID du client à modifier", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody ClientDTO dto) {
        return ResponseEntity.ok(clientService.modifierClient(id, dto));
    }

    @Operation(summary = "Supprimer un client")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Client supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Client introuvable")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerClient(
            @Parameter(description = "ID du client à supprimer", example = "1", required = true)
            @PathVariable Long id) {
        clientService.supprimerClient(id);
        return ResponseEntity.noContent().build();
    }
}
