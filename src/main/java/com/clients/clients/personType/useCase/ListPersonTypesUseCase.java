package com.clients.clients.personType.useCase;

import com.clients.shared.entities.PersonType;
import com.clients.clients.personType.CrudPersonTypeService;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ListPersonTypesUseCase {
    private final CrudPersonTypeService personTypeService;

    public ListPersonTypesUseCase(CrudPersonTypeService personTypeService) {
        this.personTypeService = personTypeService;
    }

    public List<PersonType> execute() {
        return personTypeService.getAllPersonTypes();
    }
}
