package com.clients.clients.repository;

import com.clients.shared.entities.Client;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
	Client findByIdentityAndActiveTrue(String identity);
	List<Client> findAllByActiveTrue();
	Client findByIdentity(String identity);
	Optional<Client> findByIdAndActiveTrue(Long id);
}
