package com.lgorev.telegramclient.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.Optional;

@UtilityClass
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public <T> Optional<T> readSafe(JsonNode node, Class<T> clazz) {
        try {
            return Optional.ofNullable(createMapper().treeToValue(node, clazz));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    public <T> JsonNode writeJsonNode(T object) {
        Objects.requireNonNull(object, "object required but was null");
        return createMapper().valueToTree(object);
    }

    public ObjectMapper createMapper() {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }
}
