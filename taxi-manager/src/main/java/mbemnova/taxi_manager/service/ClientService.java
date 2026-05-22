package mbemnova.taxi_manager.service;



import mbemnova.taxi_manager.DTO.ClientDTO;
import mbemnova.taxi_manager.DTO.ClientResponseDTO;
import mbemnova.taxi_manager.model.Client;
import mbemnova.taxi_manager.exection.BadRequestException;
import mbemnova.taxi_manager.exection.ResourceNotFoundException;
import mbemnova.taxi_manager.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service métier pour la gestion des clients.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;

    /**
     * Crée un nouveau client.
     */
    @Transactional
    public ClientResponseDTO creerClient(ClientDTO dto) {
        log.info("Création d'un client : {}", dto.getNom());

        if (clientRepository.existsByTelephone(dto.getTelephone())) {
            throw new BadRequestException("Un client avec le téléphone '" + dto.getTelephone() + "' existe déjà.");
        }
        if (dto.getEmail() != null && clientRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Un client avec l'email '" + dto.getEmail() + "' existe déjà.");
        }

        Client client = Client.builder()
                .nom(dto.getNom())
                .telephone(dto.getTelephone())
                .email(dto.getEmail())
                .build();

        Client saved = clientRepository.save(client);
        log.info("Client créé avec succès, ID : {}", saved.getId());
        return toResponseDTO(saved);
    }

    /**
     * Retourne tous les clients.
     */
    @Transactional(readOnly = true)
    public List<ClientResponseDTO> getTousLesClients() {
        log.info("Récupération de tous les clients");
        return clientRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retourne un client par son ID.
     */
    @Transactional(readOnly = true)
    public ClientResponseDTO getClientParId(Long id) {
        log.info("Recherche du client ID : {}", id);
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
        return toResponseDTO(client);
    }

    /**
     * Met à jour un client existant.
     */
    @Transactional
    public ClientResponseDTO modifierClient(Long id, ClientDTO dto) {
        log.info("Modification du client ID : {}", id);

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));

        if (!client.getTelephone().equals(dto.getTelephone())
                && clientRepository.existsByTelephone(dto.getTelephone())) {
            throw new BadRequestException("Le téléphone '" + dto.getTelephone() + "' est déjà utilisé.");
        }
        if (dto.getEmail() != null && !dto.getEmail().equals(client.getEmail())
                && clientRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("L'email '" + dto.getEmail() + "' est déjà utilisé.");
        }

        client.setNom(dto.getNom());
        client.setTelephone(dto.getTelephone());
        client.setEmail(dto.getEmail());

        Client updated = clientRepository.save(client);
        log.info("Client ID {} modifié avec succès", id);
        return toResponseDTO(updated);
    }

    /**
     * Supprime un client.
     */
    @Transactional
    public void supprimerClient(Long id) {
        log.info("Suppression du client ID : {}", id);

        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }

        clientRepository.deleteById(id);
        log.info("Client ID {} supprimé avec succès", id);
    }

    /**
     * Convertit une entité Client en DTO de réponse.
     */
    private ClientResponseDTO toResponseDTO(Client client) {
        return ClientResponseDTO.builder()
                .id(client.getId())
                .nom(client.getNom())
                .telephone(client.getTelephone())
                .email(client.getEmail())
                .build();
    }
}
