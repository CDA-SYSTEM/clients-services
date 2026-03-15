package com.clients.clients.personType;

import com.clients.shared.entities.PersonType;
import java.util.List;

public interface CrudPersonTypeService {
    PersonType createPersonType(PersonType personType);
    PersonType getPersonTypeById(Long id);
    List<PersonType> getAllPersonTypes();
    PersonType updatePersonType(Long id, PersonType personType);
    void deletePersonType(Long id);
}
