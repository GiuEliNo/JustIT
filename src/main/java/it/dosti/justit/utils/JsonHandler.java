package it.dosti.justit.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;

public class JsonHandler {

    private static final String DIRPATH = "data/json/";
    private static final String JSON = ".json";
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private JsonHandler(){
    }

    public static <T> void writeJsonFile(T object, String filename) throws Exception{
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(DIRPATH+filename+JSON), object);
    }



    public static <T> T readCollectionOnJsonFile(String filename, TypeReference<T> typeReference) throws Exception{
        return mapper.readValue(new File(DIRPATH+filename+JSON), typeReference);
    }







}