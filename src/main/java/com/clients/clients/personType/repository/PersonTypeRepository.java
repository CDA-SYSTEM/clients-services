package com.clients.clients.personType.repository;

import com.clients.shared.entities.PersonType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonTypeRepository extends JpaRepository<PersonType, Long> {
}
