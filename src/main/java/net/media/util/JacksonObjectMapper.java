//package net.media.util;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
//
///**
// * Created by rajat.go on 27/12/18.
// */
//public class JacksonObjectMapper {
//  private static final ObjectMapper mapper = new ObjectMapper();
//
//  static {
//    setJacksonMapperConfig(mapper);
//  }
//
//  public static ObjectMapper setJacksonMapperConfig(ObjectMapper mapper) {
//    mapper
//      .registerModule(new ParameterNamesModule())
//      .registerModule(new Jdk8Module())
//      .registerModule(new JavaTimeModule())
//      .setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS)
//      .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
//      .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
//      .enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)
//      .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
//      .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
//      .setVisibility(mapper
//        .getSerializationConfig()
//        .getDefaultVisibilityChecker()
//        .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
//        .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
//        .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
//        .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
//        .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE));
//    return mapper;
//  }
//
//  public static ObjectMapper getMapper() {
//    return mapper;
//  }
//}
