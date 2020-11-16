package com.kKen94.springbootcrud.controller;

import com.kKen94.springbootcrud.api.ExampleApi;
import com.kKen94.springbootcrud.model.Example;
import com.kKen94.springbootcrud.model.ExampleListResponse;
import com.kKen94.springbootcrud.model.ExampleResponse;
import com.kKen94.springbootcrud.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ExampleController implements ExampleApi {

    private final ExampleService service;

    @Override
    public ResponseEntity<ExampleListResponse> getExampleList() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ExampleResponse> getExample(UUID id) {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ExampleResponse> addExample(Example example) {
        return new ResponseEntity<>(service.add(example), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> updateExample(UUID id, Example example) throws EntityNotFoundException {
        service.update(example, id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<ExampleResponse> deleteExample(UUID id) throws EntityNotFoundException {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

}
