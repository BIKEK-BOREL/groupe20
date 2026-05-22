package mbemnova.taxi_manager.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mbemnova.taxi_manager.DTO.CourseDTO;
import mbemnova.taxi_manager.DTO.CourseResponseDTO;
import mbemnova.taxi_manager.model.CourseStatus;
import mbemnova.taxi_manager.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des courses.
 * Base URL : /api/courses
 */
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = "🛣️ Courses", description = "Création, suivi et historique des courses de taxi")
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "Créer une course",
            description = "Crée une nouvelle course en associant un client, un chauffeur et un taxi.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Course créée avec succès",
                    content = @Content(schema = @Schema(implementation = CourseResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Chauffeur, client ou taxi introuvable")
    })
    @PostMapping
    public ResponseEntity<CourseResponseDTO> creerCourse(@Valid @RequestBody CourseDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.creerCourse(dto));
    }

    @Operation(summary = "Liste toutes les courses", description = "Retourne toutes les courses toutes dates confondues.")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getToutesLesCourses() {
        return ResponseEntity.ok(courseService.getToutesLesCourses());
    }

    @Operation(summary = "Trouver une course par ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Course trouvée"),
            @ApiResponse(responseCode = "404", description = "Course introuvable")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseParId(
            @Parameter(description = "ID de la course", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseParId(id));
    }

    @Operation(summary = "Historique des courses d'un client",
            description = "Retourne toutes les courses effectuées par un client donné.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historique retourné"),
            @ApiResponse(responseCode = "404", description = "Client introuvable")
    })
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CourseResponseDTO>> getCoursesParClient(
            @Parameter(description = "ID du client", example = "1", required = true)
            @PathVariable Long clientId) {
        return ResponseEntity.ok(courseService.getCoursesParClient(clientId));
    }

    @Operation(summary = "Historique des courses d'un chauffeur",
            description = "Retourne toutes les courses effectuées par un chauffeur donné.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historique retourné"),
            @ApiResponse(responseCode = "404", description = "Chauffeur introuvable")
    })
    @GetMapping("/chauffeur/{chauffeurId}")
    public ResponseEntity<List<CourseResponseDTO>> getCoursesParChauffeur(
            @Parameter(description = "ID du chauffeur", example = "1", required = true)
            @PathVariable Long chauffeurId) {
        return ResponseEntity.ok(courseService.getCoursesParChauffeur(chauffeurId));
    }

    @Operation(summary = "Modifier une course", description = "Met à jour tous les champs d'une course existante.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Course modifiée avec succès"),
            @ApiResponse(responseCode = "404", description = "Course introuvable")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> modifierCourse(
            @Parameter(description = "ID de la course à modifier", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody CourseDTO dto) {
        return ResponseEntity.ok(courseService.modifierCourse(id, dto));
    }

    @Operation(summary = "Changer le statut d'une course",
            description = """
                       Met à jour uniquement le statut d'une course.
                       
                       **Cycle de vie :** `EN_ATTENTE` → `EN_COURS` → `TERMINEE`
                       
                       Une course déjà `TERMINEE` ou `ANNULEE` ne peut plus être modifiée.
                       """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Statut mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Transition de statut invalide"),
            @ApiResponse(responseCode = "404", description = "Course introuvable")
    })
    @PatchMapping("/{id}/statut")
    public ResponseEntity<CourseResponseDTO> modifierStatutCourse(
            @Parameter(description = "ID de la course", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nouveau statut", example = "EN_COURS", required = true,
                    schema = @Schema(allowableValues = {"EN_ATTENTE", "EN_COURS", "TERMINEE", "ANNULEE"}))
            @RequestParam CourseStatus statut) {
        return ResponseEntity.ok(courseService.modifierStatutCourse(id, statut));
    }

    @Operation(summary = "Supprimer une course")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Course supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Course introuvable")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCourse(
            @Parameter(description = "ID de la course à supprimer", example = "1", required = true)
            @PathVariable Long id) {
        courseService.supprimerCourse(id);
        return ResponseEntity.noContent().build();
    }
}