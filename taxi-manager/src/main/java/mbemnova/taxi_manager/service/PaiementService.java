package mbemnova.taxi_manager.service;



import mbemnova.taxi_manager.DTO.PaiementDTO;
import mbemnova.taxi_manager.DTO.PaiementResponseDTO;
import mbemnova.taxi_manager.model.Course;
import mbemnova.taxi_manager.model.Paiement;
import mbemnova.taxi_manager.exection.BadRequestException;
import mbemnova.taxi_manager.exection.ResourceNotFoundException;
import mbemnova.taxi_manager.repository.CourseRepository;
import mbemnova.taxi_manager.repository.PaiementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service métier pour la gestion des paiements.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PaiementService {

    private final PaiementRepository paiementRepository;
    private final CourseRepository courseRepository;

    /**
     * Enregistre un nouveau paiement pour une course.
     */
    @Transactional
    public PaiementResponseDTO enregistrerPaiement(PaiementDTO dto) {
        log.info("Enregistrement d'un paiement pour la course ID : {}", dto.getCourseId());

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", dto.getCourseId()));

        if (paiementRepository.existsByCourseId(dto.getCourseId())) {
            throw new BadRequestException("Un paiement existe déjà pour la course ID " + dto.getCourseId() + ".");
        }

        Paiement paiement = Paiement.builder()
                .montant(dto.getMontant())
                .methodePaiement(dto.getMethodePaiement())
                .datePaiement(dto.getDatePaiement())
                .course(course)
                .build();

        Paiement saved = paiementRepository.save(paiement);
        log.info("Paiement enregistré avec succès, ID : {}", saved.getId());
        return toResponseDTO(saved);
    }

    /**
     * Retourne tous les paiements.
     */
    @Transactional(readOnly = true)
    public List<PaiementResponseDTO> getTousLesPaiements() {
        log.info("Récupération de tous les paiements");
        return paiementRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retourne un paiement par son ID.
     */
    @Transactional(readOnly = true)
    public PaiementResponseDTO getPaiementParId(Long id) {
        log.info("Recherche du paiement ID : {}", id);
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
        return toResponseDTO(paiement);
    }

    /**
     * Retourne le paiement d'une course.
     */
    @Transactional(readOnly = true)
    public PaiementResponseDTO getPaiementParCourse(Long courseId) {
        log.info("Recherche du paiement pour la course ID : {}", courseId);
        Paiement paiement = paiementRepository.findByCourseId(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement pour la course ID " + courseId));
        return toResponseDTO(paiement);
    }

    /**
     * Convertit une entité Paiement en DTO de réponse.
     */
    private PaiementResponseDTO toResponseDTO(Paiement paiement) {
        return PaiementResponseDTO.builder()
                .id(paiement.getId())
                .montant(paiement.getMontant())
                .methodePaiement(paiement.getMethodePaiement())
                .datePaiement(paiement.getDatePaiement())
                .courseId(paiement.getCourse().getId())
                .coursePointDepart(paiement.getCourse().getPointDepart())
                .courseDestination(paiement.getCourse().getDestination())
                .build();
    }
}