package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.PersonDto;
import com.empaques.desa.domain.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<PersonDto> getAll(){
        return this.personService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getById(@PathVariable Integer id){
        return personService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PersonDto> create(@RequestBody PersonDto dto){
        return ResponseEntity.ok(personService.seva(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> update(
            @PathVariable Integer id,
            @RequestBody PersonDto dto) {
        return personService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        this.personService.delete(id);
        return ResponseEntity.ok().build();
    }

}
