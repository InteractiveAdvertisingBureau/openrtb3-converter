package net.media.utils;

import com.fasterxml.jackson.databind.JavaType;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.exceptions.OpenRtbConverterException;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ExtUtils {

  public static void removeFromExt(Map<String, Object> ext, List<String> extraFields) {
    if(nonNull(ext)) {
      for (String key: extraFields) {
        ext.remove(key);
      }
      for(Map.Entry<String, Object> entry: ext.entrySet()) {
        if(entry.getValue() == null)
          ext.remove(entry.getKey());
      }
    }
  }

  public static <X> void fetchFromExt(Consumer<X> consumer, Map<String, Object> ext, String field, String message) throws OpenRtbConverterException {
    if(nonNull(ext)) {
      if (ext.containsKey(field)) {
        try {
          consumer.accept((X) ext.get(field));
        } catch (Exception e) {
          throw new OpenRtbConverterException(message, e);
        }
      }
    }
  }

  public static <X> void fetchElementFromListInExt(Consumer<X> consumer, Map<String, Object> ext, String field, String message) throws OpenRtbConverterException {
    if(nonNull(ext)) {
      if (ext.containsKey(field)) {
        try {
          consumer.accept(((List<X>) ext.get(field)).get(0));
        } catch (Exception e) {
          throw new OpenRtbConverterException(message, e);
        }
      }
    }
  }

  public static <X> void fetchFromExt(Consumer<X> consumer, Map<String, Object> ext, String field, String message, JavaType javaType) throws OpenRtbConverterException {
    if(nonNull(ext)) {
      if (ext.containsKey(field)) {
        try {
          consumer.accept(JacksonObjectMapperUtils.getMapper().convertValue(ext.get(field),javaType));
        } catch (Exception e) {
          throw new OpenRtbConverterException(message, e);
        }
      }
    }
  }

  public static <X> void fetchFromExt(Consumer<X> consumer, Map<String, Object> ext, String field, String message, Class<X> toValueType) throws OpenRtbConverterException {
    if(nonNull(ext)) {
      if (ext.containsKey(field)) {
        try {
          consumer.accept(JacksonObjectMapperUtils.getMapper().convertValue(ext.get(field),toValueType));
        } catch (Exception e) {
          throw new OpenRtbConverterException(message, e);
        }
      }
    }
  }

  public static <X> void fetchFromExt(Consumer<X> consumer, Map<String, Object> ext, String field, String message, Converter converter, Config config, Provider provider) throws OpenRtbConverterException {
    if(nonNull(ext)) {
      if (ext.containsKey(field)) {
        try {
          consumer.accept((X) converter.map(ext.get(field), config, provider));
        } catch (Exception e) {
          throw new OpenRtbConverterException(message, e);
        }
      }
    }
  }

  public static <X> void fetchCollectionFromExt(Consumer<Collection<X>> consumer, Map<String, Object> ext, String field, String message, Config config) throws OpenRtbConverterException {
    if(nonNull(ext)) {
      if (ext.containsKey(field)) {
        try {
          consumer.accept(CollectionUtils.copyCollection((Collection<X>) ext.get(field), config));
        } catch (Exception e) {
          throw new OpenRtbConverterException(message, e);
        }
      }
    }
  }

  public static <X> void fetchCollectionFromExt(Consumer<X> consumer, Map<String, Object> ext, String field, String message, Converter converter, Config config, Provider provider, JavaType javaType) throws OpenRtbConverterException {
    if(nonNull(ext)) {
      if (ext.containsKey(field)) {
        try {
          consumer.accept((X) CollectionToCollectionConverter.convert(JacksonObjectMapperUtils.getMapper().convertValue(ext.get(field),javaType), converter, config, provider));
        } catch (Exception e) {
          throw new OpenRtbConverterException(message, e);
        }
      }
    }
  }

  public static <X> Map<String, Object> putToExt(Supplier<X> supplier, Map<String, Object> ext, String field) {
    if (nonNull(supplier.get())) {
      if(isNull(ext))
        ext = new HashMap<>();
      ext.put(field, supplier.get());
    }
    return ext;
  }

  public static <X> Map<String, Object> putToExt(X value, Map<String, Object> ext, String field) {
    if (nonNull(value)) {
      if(isNull(ext))
        ext = new HashMap<>();
      ext.put(field, value);
    }
    return ext;
  }

  public static <X> Map<String, Object> putListFromSingleObjectToExt(Supplier<X> supplier, Map<String, Object> ext, String field) {
    if (nonNull(supplier.get())) {
      if(isNull(ext))
        ext = new HashMap<>();
      ext.put(field, Collections.singletonList(supplier.get()));
    }
    return ext;
  }

  public static <X> Map<String, Object> fetchExtFromFieldInExt(Map<String, Object> ext, String field, String message) throws OpenRtbConverterException {
    if(nonNull(ext)) {
      if (ext.containsKey(field)) {
        try {
          Map<String, Object> value = (Map<String, Object>) ext.get(field);
          if (value.containsKey("ext")) {
            return (Map<String, Object>) value.get("ext");
          } else
            return null;
        } catch (Exception e) {
          throw new OpenRtbConverterException(message, e);
        }
      }
    }
    return null;
  }
}
