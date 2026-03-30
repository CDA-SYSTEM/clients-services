package com.clients.clients.personType;

import com.clients.shared.entities.PersonType;
import org.springframework.stereotype.Service;
import java.util.List;
import com.clients.clients.personType.repository.PersonTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CrudPersonTypeServiceImpl implements CrudPersonTypeService {
    private final PersonTypeRepository personTypeRepository;

    @Autowired
    public CrudPersonTypeServiceImpl(PersonTypeRepository personTypeRepository) {
        this.personTypeRepository = personTypeRepository;
    }

    @Override
    public PersonType createPersonType(PersonType personType) {
        return personTypeRepository.save(personType);
    }

    @Override
    public PersonType getPersonTypeById(Long id) {
        return personTypeRepository.findById(id).orElse(null);
    }

    @Override
    public List<PersonType> getAllPersonTypes() {
        return personTypeRepository.findAll();
    }

    @Override
    public PersonType updatePersonType(Long id, PersonType personType) {
        if (personTypeRepository.existsById(id)) {
            personType.setId(id);
            return personTypeRepository.save(personType);
        }
        return null;
    }

    @Override
    public void deletePersonType(Long id) {
        personTypeRepository.deleteById(id);
    }
}
