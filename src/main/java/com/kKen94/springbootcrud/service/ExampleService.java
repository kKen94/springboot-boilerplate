package com.kKen94.springbootcrud.service;

import com.kKen94.springbootcrud.model.Example;
import com.kKen94.springbootcrud.model.ExampleListResponse;
import com.kKen94.springbootcrud.model.ExampleResponse;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

public interface ExampleService {

    ExampleListResponse findAll();

    ExampleResponse find(UUID id);

    ExampleResponse add(Example example);

    void update(Example example, UUID id) throws EntityNotFoundException;

    ExampleResponse delete(UUID id) throws EntityNotFoundException;
}
