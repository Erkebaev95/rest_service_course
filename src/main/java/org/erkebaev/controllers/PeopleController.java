package org.erkebaev.controllers;

import org.erkebaev.dto.PersonDTO;
import org.erkebaev.models.Person;
import org.erkebaev.services.PeopleService;
import org.erkebaev.util.PersonErrorResponse;
import org.erkebaev.util.PersonNotCreatedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final ModelMapper modelMapper;
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(ModelMapper modelMapper, PeopleService peopleService) {
        this.modelMapper = modelMapper;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public List<Person> getPeople() {
        // Jackson конвертирует эти объекты в JSON
        return peopleService.findAll();
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id) {
        // Jackson конвертирует в JSON
        return peopleService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errorMsg.toString());
        }
        peopleService.save(convertToPerson(personDTO));

        // отправляем http ответ с пустым телом и со статусом 200
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // приходят от клиента
    private Person convertToPerson(PersonDTO personDTO) {
        // Маппет все поля из дто в объект модели
        return modelMapper.map(personDTO, Person.class);
    }

    // ловим исключение
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );

        // В HTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        // NOT_FOUND - 404
        // BAD_REQUEST - 400
    }
}
