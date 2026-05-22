package mbemnova.taxi_manager.service;



import mbemnova.taxi_manager.DTO.TaxiDTO;
import mbemnova.taxi_manager.DTO.TaxiResponseDTO;
import mbemnova.taxi_manager.model.Taxi;
import mbemnova.taxi_manager.exection.BadRequestException;
import mbemnova.taxi_manager.exection.ResourceNotFoundException;
import mbemnova.taxi_manager.repository.TaxiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service métier pour la gestion des taxis.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TaxiService {

    private final TaxiRepository taxiRepository;

    /**
     * Crée un nouveau taxi.
     */
    @Transactional
    public TaxiResponseDTO creerTaxi(TaxiDTO dto) {
        log.info("Création d'un taxi avec l'immatriculation : {}", dto.getImmatriculation());

        if (taxiRepository.existsByImmatriculation(dto.getImmatriculation())) {
            throw new BadRequestException("Un taxi avec l'immatriculation '" + dto.getImmatriculation() + "' existe déjà.");
        }

        Taxi taxi = Taxi.builder()
                .immatriculation(dto.getImmatriculation())
                .marque(dto.getMarque())
                .couleur(dto.getCouleur())
                .capacite(dto.getCapacite())
                .statut(dto.getStatut())
                .typeVehicule(dto.getTypeVehicule())
                .build();

        Taxi saved = taxiRepository.save(taxi);
        log.info("Taxi créé avec succès, ID : {}", saved.getId());
        return toResponseDTO(saved);
    }

    /**
     * Retourne tous les taxis.
     */
    @Transactional(readOnly = true)
    public List<TaxiResponseDTO> getTousLesTaxis() {
        log.info("Récupération de tous les taxis");
        return taxiRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retourne un taxi par son ID.
     */
    @Transactional(readOnly = true)
    public TaxiResponseDTO getTaxiParId(Long id) {
        log.info("Recherche du taxi ID : {}", id);
        Taxi taxi = taxiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Taxi", id));
        return toResponseDTO(taxi);
    }

    /**
     * Met à jour un taxi existant.
     */
    @Transactional
    public TaxiResponseDTO modifierTaxi(Long id, TaxiDTO dto) {
        log.info("Modification du taxi ID : {}", id);

        Taxi taxi = taxiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Taxi", id));

        // Vérifie que la nouvelle immatriculation n'appartient pas à un autre taxi
        if (!taxi.getImmatriculation().equals(dto.getImmatriculation())
                && taxiRepository.existsByImmatriculation(dto.getImmatriculation())) {
            throw new BadRequestException("Un taxi avec l'immatriculation '" + dto.getImmatriculation() + "' existe déjà.");
        }

        taxi.setImmatriculation(dto.getImmatriculation());
        taxi.setMarque(dto.getMarque());
        taxi.setCouleur(dto.getCouleur());
        taxi.setCapacite(dto.getCapacite());
        taxi.setStatut(dto.getStatut());
        taxi.setTypeVehicule(dto.getTypeVehicule());

        Taxi updated = taxiRepository.save(taxi);
        log.info("Taxi ID {} modifié avec succès", id);
        return toResponseDTO(updated);
    }

    /**
     * Supprime un taxi par son ID.
     */
    @Transactional
    public void supprimerTaxi(Long id) {
        log.info("Suppression du taxi ID : {}", id);

        if (!taxiRepository.existsById(id)) {
            throw new ResourceNotFoundException("Taxi", id);
        }

        taxiRepository.deleteById(id);
        log.info("Taxi ID {} supprimé avec succès", id);
    }

    /**
     * Convertit une entité Taxi en DTO de réponse.
     */
    private TaxiResponseDTO toResponseDTO(Taxi taxi) {
        return TaxiResponseDTO.builder()
                .id(taxi.getId())
                .immatriculation(taxi.getImmatriculation())
                .marque(taxi.getMarque())
                .couleur(taxi.getCouleur())
                .capacite(taxi.getCapacite())
                .statut(taxi.getStatut())
                .typeVehicule(taxi.getTypeVehicule())
                .build();
    }
}
