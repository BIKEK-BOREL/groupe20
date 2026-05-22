package mbemnova.taxi_manager.service;



import mbemnova.taxi_manager.DTO.CourseDTO;
import mbemnova.taxi_manager.DTO.CourseResponseDTO;
import mbemnova.taxi_manager.model.*;
import mbemnova.taxi_manager.exection.BadRequestException;
import mbemnova.taxi_manager.exection.ResourceNotFoundException;
import mbemnova.taxi_manager.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service métier pour la gestion des courses.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;
    private final ChauffeurRepository chauffeurRepository;
    private final ClientRepository clientRepository;
    private final TaxiRepository taxiRepository;

    /**
     * Crée une nouvelle course.
     */
    @Transactional
    public CourseResponseDTO creerCourse(CourseDTO dto) {
        log.info("Création d'une course de '{}' vers '{}'", dto.getPointDepart(), dto.getDestination());

        Chauffeur chauffeur = chauffeurRepository.findById(dto.getChauffeurId())
                .orElseThrow(() -> new ResourceNotFoundException("Chauffeur", dto.getChauffeurId()));

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", dto.getClientId()));

        Taxi taxi = taxiRepository.findById(dto.getTaxiId())
                .orElseThrow(() -> new ResourceNotFoundException("Taxi", dto.getTaxiId()));

        Course course = Course.builder()
                .pointDepart(dto.getPointDepart())
                .destination(dto.getDestination())
                .distance(dto.getDistance())
                .prix(dto.getPrix())
                .dateCourse(dto.getDateCourse())
                .statut(dto.getStatut())
                .chauffeur(chauffeur)
                .client(client)
                .taxi(taxi)
                .build();

        Course saved = courseRepository.save(course);
        log.info("Course créée avec succès, ID : {}", saved.getId());
        return toResponseDTO(saved);
    }

    /**
     * Retourne toutes les courses.
     */
    @Transactional(readOnly = true)
    public List<CourseResponseDTO> getToutesLesCourses() {
        log.info("Récupération de toutes les courses");
        return courseRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retourne une course par son ID.
     */
    @Transactional(readOnly = true)
    public CourseResponseDTO getCourseParId(Long id) {
        log.info("Recherche de la course ID : {}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", id));
        return toResponseDTO(course);
    }

    /**
     * Retourne l'historique des courses d'un client.
     */
    @Transactional(readOnly = true)
    public List<CourseResponseDTO> getCoursesParClient(Long clientId) {
        log.info("Historique des courses du client ID : {}", clientId);
        if (!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("Client", clientId);
        }
        return courseRepository.findByClientId(clientId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retourne l'historique des courses d'un chauffeur.
     */
    @Transactional(readOnly = true)
    public List<CourseResponseDTO> getCoursesParChauffeur(Long chauffeurId) {
        log.info("Historique des courses du chauffeur ID : {}", chauffeurId);
        if (!chauffeurRepository.existsById(chauffeurId)) {
            throw new ResourceNotFoundException("Chauffeur", chauffeurId);
        }
        return courseRepository.findByChauffeurId(chauffeurId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Met à jour le statut d'une course.
     */
    @Transactional
    public CourseResponseDTO modifierStatutCourse(Long id, CourseStatus nouveauStatut) {
        log.info("Modification du statut de la course ID {} vers {}", id, nouveauStatut);

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", id));

        // Validation de la transition de statut
        validerTransitionStatut(course.getStatut(), nouveauStatut);

        course.setStatut(nouveauStatut);
        Course updated = courseRepository.save(course);
        return toResponseDTO(updated);
    }

    /**
     * Met à jour tous les champs d'une course.
     */
    @Transactional
    public CourseResponseDTO modifierCourse(Long id, CourseDTO dto) {
        log.info("Modification de la course ID : {}", id);

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", id));

        Chauffeur chauffeur = chauffeurRepository.findById(dto.getChauffeurId())
                .orElseThrow(() -> new ResourceNotFoundException("Chauffeur", dto.getChauffeurId()));

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", dto.getClientId()));

        Taxi taxi = taxiRepository.findById(dto.getTaxiId())
                .orElseThrow(() -> new ResourceNotFoundException("Taxi", dto.getTaxiId()));

        course.setPointDepart(dto.getPointDepart());
        course.setDestination(dto.getDestination());
        course.setDistance(dto.getDistance());
        course.setPrix(dto.getPrix());
        course.setDateCourse(dto.getDateCourse());
        course.setStatut(dto.getStatut());
        course.setChauffeur(chauffeur);
        course.setClient(client);
        course.setTaxi(taxi);

        return toResponseDTO(courseRepository.save(course));
    }

    /**
     * Supprime une course.
     */
    @Transactional
    public void supprimerCourse(Long id) {
        log.info("Suppression de la course ID : {}", id);
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course", id);
        }
        courseRepository.deleteById(id);
    }

    /**
     * Valide que la transition de statut est logique.
     */
    private void validerTransitionStatut(CourseStatus actuel, CourseStatus nouveau) {
        if (actuel == CourseStatus.TERMINEE || actuel == CourseStatus.ANNULEE) {
            throw new BadRequestException("Impossible de modifier une course déjà " + actuel.name().toLowerCase() + ".");
        }
    }

    /**
     * Convertit une entité Course en DTO de réponse.
     */
    private CourseResponseDTO toResponseDTO(Course course) {
        return CourseResponseDTO.builder()
                .id(course.getId())
                .pointDepart(course.getPointDepart())
                .destination(course.getDestination())
                .distance(course.getDistance())
                .prix(course.getPrix())
                .dateCourse(course.getDateCourse())
                .statut(course.getStatut())
                .chauffeurId(course.getChauffeur().getId())
                .chauffeurNom(course.getChauffeur().getNom())
                .clientId(course.getClient().getId())
                .clientNom(course.getClient().getNom())
                .taxiId(course.getTaxi().getId())
                .taxiImmatriculation(course.getTaxi().getImmatriculation())
                .build();
    }
}