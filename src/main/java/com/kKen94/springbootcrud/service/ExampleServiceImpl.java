package com.kKen94.springbootcrud.service;

import com.kKen94.springbootcrud.dao.ExampleEntity;
import com.kKen94.springbootcrud.dao.ExampleRepository;
import com.kKen94.springbootcrud.mapping.ExampleMapper;
import com.kKen94.springbootcrud.model.Example;
import com.kKen94.springbootcrud.model.ExampleListResponse;
import com.kKen94.springbootcrud.model.ExampleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExampleServiceImpl implements ExampleService {

    private final ExampleRepository repository;
    private final ExampleMapper mapper;

    @Override
    public ExampleListResponse findAll() {
        List<ExampleEntity> entities = repository.findAll();
        return mapper.toExampleListResponse(entities);
    }

    @Override
    public ExampleResponse find(UUID id) {
        Optional<ExampleEntity> entity = repository.findById(id);
        return entity.map(mapper::toExampleResponse).orElse(null);
    }

    @Override
    public ExampleResponse add(Example example) {
        ExampleEntity entityToSave = mapper.toExampleEntity(example);
        ExampleEntity entityToReturn = repository.save(entityToSave);
        return mapper.toExampleResponse(entityToReturn);
    }

    @Override
    public void update(Example example, UUID id) throws EntityNotFoundException {
        boolean isPresent = repository.findById(id).isPresent();
        if (!isPresent) {
            throw new EntityNotFoundException();
        }
        ExampleEntity entity = mapper.toExampleEntity(example);
        repository.save(entity);
    }

    @Override
    public ExampleResponse delete(UUID id) throws EntityNotFoundException {
        Optional<ExampleEntity> entity = repository.findById(id);
        if (!entity.isPresent()) {
            throw new EntityNotFoundException();
        }
        repository.deleteById(id);
        return mapper.toExampleResponse(entity.get());
    }
}
