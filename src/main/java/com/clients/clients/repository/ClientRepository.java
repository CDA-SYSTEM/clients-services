package com.clients.clients.repository;

import com.clients.shared.entities.Client;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
	Client findByIdentityAndActiveTrue(String identity);
	List<Client> findAllByActiveTrue();
	Client findByIdentity(String identity);
	Optional<Client> findByIdAndActiveTrue(Long id);
	Optional<Client> findById(Long id);

	long countByActiveTrue();
	long countByActiveFalse();

	@Query("SELECT dt.type as label, COUNT(c) as count FROM Client c LEFT JOIN c.documentType dt GROUP BY dt.type")
	List<Object[]> countByDocumentType();

	@Query("SELECT pt.type as label, COUNT(c) as count FROM Client c LEFT JOIN c.personType pt GROUP BY pt.type")
	List<Object[]> countByPersonType();
}
