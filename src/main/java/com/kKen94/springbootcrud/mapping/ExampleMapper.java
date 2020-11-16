package com.kKen94.springbootcrud.mapping;

import com.kKen94.springbootcrud.dao.ExampleEntity;
import com.kKen94.springbootcrud.model.Example;
import com.kKen94.springbootcrud.model.ExampleListResponse;
import com.kKen94.springbootcrud.model.ExampleResponse;
import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ExampleMapper {

    public ExampleEntity toExampleEntity(Example src) {
        ExampleEntity dest = new ExampleEntity();
        dest.setId(src.getId().get());
        dest.setDescription(src.getDescription().get());
        return dest;
    }

    public abstract List<ExampleEntity> toExampleEntity(Collection<Example> src);

    public ExampleResponse toExampleResponse(ExampleEntity src) {
        ExampleResponse dest = new ExampleResponse();
        dest.setData(toExample(src));
        return dest;
    }

    public abstract List<ExampleResponse> toExampleResponse(Collection<ExampleEntity> src);

    public ExampleListResponse toExampleListResponse(List<ExampleEntity> src) {
        ExampleListResponse response = new ExampleListResponse();
        response.setData(toExample(src));
        return response;
    }

    public Example toExample(ExampleEntity src) {
        Example dest = new Example();
        dest.setId(JsonNullable.of(src.getId()));
        dest.setDescription(JsonNullable.of(src.getDescription()));
        return dest;
    }

    public abstract List<Example> toExample(Collection<ExampleEntity> src);
}
