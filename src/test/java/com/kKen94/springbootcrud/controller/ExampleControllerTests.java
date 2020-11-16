package com.kKen94.springbootcrud.controller;

import com.kKen94.springbootcrud.dao.ExampleEntity;
import com.kKen94.springbootcrud.dao.ExampleRepository;
import com.kKen94.springbootcrud.mapping.ExampleMapper;
import com.kKen94.springbootcrud.model.Example;
import com.kKen94.springbootcrud.model.ExampleListResponse;
import com.kKen94.springbootcrud.model.ExampleResponse;
import com.kKen94.springbootcrud.service.ExampleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ExampleControllerTests {

    @Mock
    private ExampleRepository exampleRepository;
    private static final ExampleMapper mapper = Mappers.getMapper(ExampleMapper.class);

    ExampleServiceImpl service;
    ExampleController controller;

    private final static UUID ID1 = UUID.randomUUID();
    private final static String DESCRIPTION1 = "Description 1";
    private final static UUID ID2 = UUID.randomUUID();
    private final static String DESCRIPTION2 = "Description 2";

    @BeforeEach
    void init() {
        service = new ExampleServiceImpl(exampleRepository, mapper);
        controller = new ExampleController(service);
    }

    @Test
    public void getListaTestoTest() {
        Mockito.when(exampleRepository.findAll()).thenReturn(createListResp());

        ResponseEntity<ExampleListResponse> response = controller.getExampleList();

        Assertions.assertEquals(2, response.getBody().getData().size());
        Assertions.assertEquals(response.getBody().getData().get(0).getId(), JsonNullable.of(ID1));
        Assertions.assertEquals(response.getBody().getData().get(1).getId(), JsonNullable.of(ID2));
        Assertions.assertEquals(response.getBody().getData().get(0).getDescription(), JsonNullable.of(DESCRIPTION1));
        Assertions.assertEquals(response.getBody().getData().get(1).getDescription(), JsonNullable.of(DESCRIPTION2));
    }

    @Test
    public void getTestoByIdTest() {
        Mockito.when(exampleRepository.findById(ID1)).thenReturn(createEntity(ID1, DESCRIPTION1));
        
        Assertions.assertAll(() -> controller.getExample(ID1).getBody().getData());
    }

    @Test
    public void addTestoTest() {
        Mockito.when(exampleRepository.save(Mockito.any(ExampleEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        ResponseEntity<ExampleResponse> response = controller.addExample(createExample(ID1, DESCRIPTION1));

        Assertions.assertEquals(JsonNullable.of(ID1), response.getBody().getData().getId());
        Assertions.assertEquals(JsonNullable.of(DESCRIPTION1), response.getBody().getData().getDescription());
    }

    @Test
    public void updateTestoTest() throws EntityNotFoundException {
        Optional<ExampleEntity> entity = createEntity(ID1, DESCRIPTION1);
        Example example = createExample(ID1, DESCRIPTION1);

        Mockito.when(exampleRepository.findById(ID1)).thenReturn(entity);
        Mockito.when(exampleRepository.save(Mockito.any(ExampleEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        controller.updateExample(ID1, example);

        Mockito.verify(exampleRepository, Mockito.times(1)).save(Mockito.any(ExampleEntity.class));
    }

    @Test
    public void updateTestoExceptionTest() {
        Optional<ExampleEntity> testoEntity = Optional.empty();
        Example example = createExample(ID1, DESCRIPTION1);
        Mockito.when(exampleRepository.findById(ID1)).thenReturn(testoEntity);

        Assertions.assertThrows(EntityNotFoundException.class, () -> controller.updateExample(ID1, example), "The requested resource was not found");

        Mockito.verify(exampleRepository, Mockito.times(1)).findById(ID1);
    }

    @Test
    public void deleteTestoTest() throws EntityNotFoundException {
        Optional<ExampleEntity> entity = createEntity(ID1, DESCRIPTION1);

        Mockito.when(exampleRepository.findById(ID1)).thenReturn(entity);
        Mockito.doNothing().when(exampleRepository).deleteById(ID1);

        ResponseEntity<ExampleResponse> response = controller.deleteExample(ID1);

        Assertions.assertEquals(JsonNullable.of(ID1), response.getBody().getData().getId());
        Assertions.assertEquals(JsonNullable.of(DESCRIPTION1), response.getBody().getData().getDescription());

        Mockito.verify(exampleRepository, Mockito.times(1)).deleteById(ID1);
    }

    @Test
    public void deleteTestoExceptionTest() {
        Optional<ExampleEntity> exampleEntity = Optional.empty();
        Mockito.when(exampleRepository.findById(ID1)).thenReturn(exampleEntity);

        Assertions.assertThrows(EntityNotFoundException.class, () -> controller.deleteExample(ID1), "The requested resource was not found");

        Mockito.verify(exampleRepository, Mockito.times(1)).findById(ID1);
    }

    private List<ExampleEntity> createListResp(){
        List<ExampleEntity> risp = new ArrayList<>();
        risp.add(createEntity(ID1, DESCRIPTION1).get());
        risp.add(createEntity(ID2, DESCRIPTION2).get());
        return risp;
    }
    
    private Optional<ExampleEntity> createEntity(UUID id, String description) {
        ExampleEntity exampleEntity = new ExampleEntity();
        exampleEntity.setId(id);
        exampleEntity.setDescription(description);
        return Optional.of(exampleEntity);
    }

    private Example createExample(UUID id, String description) {
        Example example = new Example();
        example.setId(JsonNullable.of(id));
        example.setDescription(JsonNullable.of(description));
        return example;
    }

}
