package mbemnova.taxi_manager.service;


import mbemnova.taxi_manager.DTO.ChauffeurDTO;
import mbemnova.taxi_manager.DTO.ChauffeurResponseDTO;
import mbemnova.taxi_manager.model.Chauffeur;
import mbemnova.taxi_manager.model.Taxi;
import mbemnova.taxi_manager.exection.BadRequestException;
import mbemnova.taxi_manager.exection.ResourceNotFoundException;
import mbemnova.taxi_manager.repository.ChauffeurRepository;
import mbemnova.taxi_manager.repository.TaxiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service métier pour la gestion des chauffeurs.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ChauffeurService {

    private final ChauffeurRepository chauffeurRepository;
    private final TaxiRepository taxiRepository;

    /**
     * Crée un nouveau chauffeur.
     */
    @Transactional
    public ChauffeurResponseDTO creerChauffeur(ChauffeurDTO dto) {
        log.info("Création d'un chauffeur : {}", dto.getNom());

        if (chauffeurRepository.existsByTelephone(dto.getTelephone())) {
            throw new BadRequestException("Un chauffeur avec le téléphone '" + dto.getTelephone() + "' existe déjà.");
        }
        if (chauffeurRepository.existsByNumeroPermis(dto.getNumeroPermis())) {
            throw new BadRequestException("Un chauffeur avec le permis '" + dto.getNumeroPermis() + "' existe déjà.");
        }

        Taxi taxi = null;
        if (dto.getTaxiId() != null) {
            taxi = taxiRepository.findById(dto.getTaxiId())
                    .orElseThrow(() -> new ResourceNotFoundException("Taxi", dto.getTaxiId()));
        }

        Chauffeur chauffeur = Chauffeur.builder()
                .nom(dto.getNom())
                .telephone(dto.getTelephone())
                .numeroPermis(dto.getNumeroPermis())
                .adresse(dto.getAdresse())
                .taxi(taxi)
                .build();

        Chauffeur saved = chauffeurRepository.save(chauffeur);
        log.info("Chauffeur créé avec succès, ID : {}", saved.getId());
        return toResponseDTO(saved);
    }

    /**
     * Retourne tous les chauffeurs.
     */
    @Transactional(readOnly = true)
    public List<ChauffeurResponseDTO> getTousLesChauffeurs() {
        log.info("Récupération de tous les chauffeurs");
        return chauffeurRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retourne un chauffeur par son ID.
     */
    @Transactional(readOnly = true)
    public ChauffeurResponseDTO getChauffeurParId(Long id) {
        log.info("Recherche du chauffeur ID : {}", id);
        Chauffeur chauffeur = chauffeurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chauffeur", id));
        return toResponseDTO(chauffeur);
    }

    /**
     * Met à jour un chauffeur existant.
     */
    @Transactional
    public ChauffeurResponseDTO modifierChauffeur(Long id, ChauffeurDTO dto) {
        log.info("Modification du chauffeur ID : {}", id);

        Chauffeur chauffeur = chauffeurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chauffeur", id));

        if (!chauffeur.getTelephone().equals(dto.getTelephone())
                && chauffeurRepository.existsByTelephone(dto.getTelephone())) {
            throw new BadRequestException("Le téléphone '" + dto.getTelephone() + "' est déjà utilisé.");
        }
        if (!chauffeur.getNumeroPermis().equals(dto.getNumeroPermis())
                && chauffeurRepository.existsByNumeroPermis(dto.getNumeroPermis())) {
            throw new BadRequestException("Le numéro de permis '" + dto.getNumeroPermis() + "' est déjà utilisé.");
        }

        Taxi taxi = null;
        if (dto.getTaxiId() != null) {
            taxi = taxiRepository.findById(dto.getTaxiId())
                    .orElseThrow(() -> new ResourceNotFoundException("Taxi", dto.getTaxiId()));
        }

        chauffeur.setNom(dto.getNom());
        chauffeur.setTelephone(dto.getTelephone());
        chauffeur.setNumeroPermis(dto.getNumeroPermis());
        chauffeur.setAdresse(dto.getAdresse());
        chauffeur.setTaxi(taxi);

        Chauffeur updated = chauffeurRepository.save(chauffeur);
        log.info("Chauffeur ID {} modifié avec succès", id);
        return toResponseDTO(updated);
    }

    /**
     * Affecte un taxi à un chauffeur.
     */
    @Transactional
    public ChauffeurResponseDTO affecterTaxi(Long chauffeurId, Long taxiId) {
        log.info("Affectation du taxi ID {} au chauffeur ID {}", taxiId, chauffeurId);

        Chauffeur chauffeur = chauffeurRepository.findById(chauffeurId)
                .orElseThrow(() -> new ResourceNotFoundException("Chauffeur", chauffeurId));

        Taxi taxi = taxiRepository.findById(taxiId)
                .orElseThrow(() -> new ResourceNotFoundException("Taxi", taxiId));

        chauffeur.setTaxi(taxi);
        Chauffeur updated = chauffeurRepository.save(chauffeur);
        return toResponseDTO(updated);
    }

    /**
     * Supprime un chauffeur.
     */
    @Transactional
    public void supprimerChauffeur(Long id) {
        log.info("Suppression du chauffeur ID : {}", id);

        if (!chauffeurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Chauffeur", id);
        }

        chauffeurRepository.deleteById(id);
        log.info("Chauffeur ID {} supprimé avec succès", id);
    }

    /**
     * Convertit une entité Chauffeur en DTO de réponse.
     */
    private ChauffeurResponseDTO toResponseDTO(Chauffeur chauffeur) {
        return ChauffeurResponseDTO.builder()
                .id(chauffeur.getId())
                .nom(chauffeur.getNom())
                .telephone(chauffeur.getTelephone())
                .numeroPermis(chauffeur.getNumeroPermis())
                .adresse(chauffeur.getAdresse())
                .taxiId(chauffeur.getTaxi() != null ? chauffeur.getTaxi().getId() : null)
                .taxiImmatriculation(chauffeur.getTaxi() != null ? chauffeur.getTaxi().getImmatriculation() : null)
                .build();
    }
}