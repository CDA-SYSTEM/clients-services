package com.clients.clients.service;

import com.clients.clients.dto.ClientSearchFilterDTO;
import com.clients.clients.dto.ClientListResponseDTO;
import com.clients.clients.dto.ClientResponseDTO;
import com.clients.clients.repository.ClientRepository;
import com.clients.shared.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientSearchServiceImpl implements ClientSearchService {
    private final ClientRepository clientRepository;

    public ClientSearchServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientListResponseDTO searchClients(ClientSearchFilterDTO filter) {
        Specification<Client> spec = buildSpecification(filter);
        int page = filter.getPage() != null ? filter.getPage() : 0;
        int size = filter.getSize() != null ? filter.getSize() : 10;
        Page<Client> clientPage = clientRepository.findAll(spec, PageRequest.of(page, size));
        List<ClientResponseDTO> dtos = new ArrayList<>();
        for (Client client : clientPage.getContent()) {
            ClientResponseDTO dto = new ClientResponseDTO();
            dto.setId(client.getId());
            dto.setNombre(client.getNombre());
            dto.setApellido(client.getApellido());
            dto.setBirthDate(client.getBirthDate());
            dto.setCelular(client.getCelular());
            dto.setEmail(client.getEmail());
            dto.setActive(client.isActive());
            dto.setIdentity(client.getIdentity());
            dto.setDireccion(client.getDireccion());
            if (client.getDocumentType() != null) {
                ClientResponseDTO.DocumentTypeDTO docDto = new ClientResponseDTO.DocumentTypeDTO();
                docDto.setId(client.getDocumentType().getId());
                docDto.setType(client.getDocumentType().getType());
                dto.setDocumentType(docDto);
            }
            if (client.getPersonType() != null) {
                ClientResponseDTO.PersonTypeDTO perDto = new ClientResponseDTO.PersonTypeDTO();
                perDto.setId(client.getPersonType().getId());
                perDto.setType(client.getPersonType().getType());
                dto.setPersonType(perDto);
            }
            dtos.add(dto);
        }
        ClientListResponseDTO response = new ClientListResponseDTO();
        response.setClients(dtos);
        response.setPage(page);
        response.setSize(size);
        response.setTotalElements(clientPage.getTotalElements());
        response.setTotalPages(clientPage.getTotalPages());
        return response;
    }

    private Specification<Client> buildSpecification(ClientSearchFilterDTO filter) {
        return (root, query, cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();
            if (filter.getSearch() != null && !filter.getSearch().isEmpty()) {
                String search = filter.getSearch().toLowerCase();
                List<jakarta.persistence.criteria.Predicate> searchPredicates = new ArrayList<>();
                searchPredicates.add(cb.like(cb.lower(root.get("identity")), "%" + search + "%"));
                searchPredicates.add(cb.like(cb.lower(root.get("nombre")), "%" + search + "%"));
                searchPredicates.add(cb.like(cb.lower(root.get("apellido")), "%" + search + "%"));
                searchPredicates.add(cb.like(cb.lower(root.get("email")), "%" + search + "%"));
                searchPredicates.add(cb.like(cb.lower(root.get("celular")), "%" + search + "%"));
                predicates.add(cb.or(searchPredicates.toArray(new jakarta.persistence.criteria.Predicate[0])));
            }
            // Eliminar filtro por direccion, ya no está en el DTO
            if (filter.getDocumentTypeId() != null) {
                predicates.add(cb.equal(root.get("documentType").get("id"), filter.getDocumentTypeId()));
            }
            if (filter.getPersonTypeId() != null) {
                predicates.add(cb.equal(root.get("personType").get("id"), filter.getPersonTypeId()));
            }
            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }
}
