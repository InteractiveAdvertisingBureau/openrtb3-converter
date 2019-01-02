package net.media.config;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * @author shiva.b
 */
public class ConfigSupport {

  private ObjectMapper jsonMapper = createMapper(null, null);
  private ObjectMapper yamlMapper = createMapper(new YAMLFactory(), null);

  @JsonFilter("classFilter")
  public static class ClassMixIn {

  }

  public static class ConfigMixIn {

    @JsonProperty
    String bannerTemplate;
  }

  public <T> T fromJSON(String content, Class<T> configType) throws IOException {
    return jsonMapper.readValue(content, configType);
  }

  public <T> T fromJSON(File file, Class<T> configType) throws IOException {
    return fromJSON(file, configType, null);
  }

  public <T> T fromJSON(File file, Class<T> configType, java.lang.ClassLoader classLoader) throws IOException {
    jsonMapper = createMapper(null, classLoader);
    return jsonMapper.readValue(file, configType);
  }

  public <T> T fromJSON(URL url, Class<T> configType) throws IOException {
    return jsonMapper.readValue(url, configType);
  }

  public <T> T fromJSON(Reader reader, Class<T> configType) throws IOException {
    return jsonMapper.readValue(reader, configType);
  }

  public <T> T fromJSON(InputStream inputStream, Class<T> configType) throws IOException {
    return jsonMapper.readValue(inputStream, configType);
  }

  public String toJSON(Config config) throws IOException {
    return jsonMapper.writeValueAsString(config);
  }

  public <T> T fromYAML(String content, Class<T> configType) throws IOException {
    return yamlMapper.readValue(content, configType);
  }

  public <T> T fromYAML(File file, Class<T> configType) throws IOException {
    return yamlMapper.readValue(file, configType);
  }

  public <T> T fromYAML(File file, Class<T> configType, ClassLoader classLoader) throws IOException {
    yamlMapper = createMapper(new YAMLFactory(), classLoader);
    return yamlMapper.readValue(file, configType);
  }

  public <T> T fromYAML(URL url, Class<T> configType) throws IOException {
    return yamlMapper.readValue(url, configType);
  }

  public <T> T fromYAML(Reader reader, Class<T> configType) throws IOException {
    return yamlMapper.readValue(reader, configType);
  }

  public <T> T fromYAML(InputStream inputStream, Class<T> configType) throws IOException {
    return yamlMapper.readValue(inputStream, configType);
  }

  public String toYAML(Config config) throws IOException {
    return yamlMapper.writeValueAsString(config);
  }

  private ObjectMapper createMapper(JsonFactory mapping, ClassLoader classLoader) {
    ObjectMapper mapper = new ObjectMapper(mapping);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.addMixIn(Config.class, ConfigMixIn.class);
    FilterProvider filterProvider = new SimpleFilterProvider()
      .addFilter("classFilter", SimpleBeanPropertyFilter.filterOutAllExcept());
    mapper.setFilterProvider(filterProvider);
    if (classLoader != null) {
      TypeFactory tf = TypeFactory.defaultInstance()
        .withClassLoader(classLoader);
      mapper.setTypeFactory(tf);
    }

    return mapper;
  }
}
