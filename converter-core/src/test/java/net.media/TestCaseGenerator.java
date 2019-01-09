package net.media;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.databind.node.*;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestCaseGenerator {

  static final ObjectMapper objectMapper = new ObjectMapper();
  static final String basePath = "src/test/resources/";

  public static void main(String[] args) throws IOException {
    final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    /*mapper.registerModule(new ParameterNamesModule())
      .registerModule(new Jdk8Module())
      .registerModule(new JavaTimeModule());*/
    mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    for(Path path : Files.list(Paths.get(basePath + "edits")).collect(Collectors.toList())) {
      String json2 = new String(Files.readAllBytes(path));
      final Test test = mapper.readValue(json2, Test.class);

      for(Case aCase: test.getCases()) {
        generateJsons(aCase);
        objectMapper.writerWithDefaultPrettyPrinter()
          .writeValue(new File(basePath + "generated/" + aCase.getPurpose() + ".json"), aCase);
      /*try (FileWriter writer = new FileWriter(new File(basePath + "generated/" + aCase.getPurpose() + ".yaml"))) {
        new Yaml().dump(map, writer);
      }*/
      }
    }
  }

  public static Case generateJsons(Case aCase) throws IOException{

    String inputJson = new String(Files.readAllBytes(Paths.get(basePath + "/master/" + aCase.getInputFile())));
    JsonNode inputJsonObject = objectMapper.readValue(inputJson, JsonNode.class);
    for(Map.Entry<String, String> entry: aCase.getInputEdits().entrySet()) {
      modify(inputJsonObject, getNode(entry.getValue()), entry.getKey().split("\\."), 0);
    }
    aCase.setInputJson(inputJsonObject);

    if(aCase.getOutputFile() != null && !aCase.getOutputFile().trim().equals("null")) {
      String outputJson = new String(Files.readAllBytes(Paths.get(basePath + "/master/" + aCase.getOutputFile())));
      JsonNode outputJsonObject = objectMapper.readValue(outputJson, JsonNode.class);
      for(Map.Entry<String, String> entry: aCase.getOutputEdits().entrySet()) {
        modify(outputJsonObject, getNode(entry.getValue()), entry.getKey().split("\\."), 0);
      }
      aCase.setOutputJson(outputJsonObject);
    }

    return aCase;
  }

  public static JsonNode getNode(String text) {
    if(text == null || text.trim().equals("null")) {
      return NullNode.getInstance();
    }
    final String trimmedText = text.trim();
    if (!trimmedText.startsWith("\"")) {
      if(trimmedText.equals("true") || trimmedText.equals("false")) {
        return BooleanNode.valueOf(Boolean.parseBoolean(trimmedText));
      }
      if(trimmedText.contains(".")) {
        return DoubleNode.valueOf(Double.parseDouble(trimmedText));
      }
      return IntNode.valueOf(Integer.parseInt(trimmedText));
    }
    return objectMapper.valueToTree(trimmedText.substring(1, trimmedText.length() - 1));
  }

  public static void modify(JsonNode master, JsonNode nodeToSet, String[] path, int index) {
    String fieldName = path[index].trim();
    int newIndex = index + 1;
    if(!fieldName.contains("[")) {
      if (index == path.length - 1) {
        /*if (master.get(fieldName).isContainerNode()) {
          throw new RuntimeException("Unexpected Container Node found.");
        }*/
        ((ObjectNode) master).replace(fieldName, nodeToSet);
      } else {
        modify(master.get(fieldName), nodeToSet, path, newIndex);
      }

    } else {
      int indexOfLeftSB = fieldName.indexOf('[');
      int indexOfRightSB = fieldName.indexOf(']');
      String textBetweenBs = fieldName.substring(indexOfLeftSB + 1, indexOfRightSB).trim();
      String newFieldName = fieldName.substring(0, indexOfLeftSB).trim();

      final JsonNode arrayNode = master.get(newFieldName);
      if (index == path.length - 1) {
        if(textBetweenBs.contains(",")) {
          final String[] elements = textBetweenBs.split(",");
          Arrays.stream(elements).forEach(x -> ((ArrayNode) arrayNode).insert(Integer.parseInt(x.trim()), nodeToSet));
        } else if (!textBetweenBs.equals("*")) {
          ((ArrayNode) arrayNode).insert(Integer.parseInt(textBetweenBs), nodeToSet);
        } else {
          final int size = master.size();
          IntStream.range(0, size).forEach(x -> ((ArrayNode) arrayNode).insert(x, nodeToSet));
        }
      } else {
        if(textBetweenBs.contains(",")) {
          final String[] elements = textBetweenBs.split(",");
          Arrays.stream(elements).forEach(x -> modify(arrayNode.get(Integer.parseInt(x.trim())), nodeToSet, path, newIndex));
        } else if (!textBetweenBs.equals("*")) {
          modify(arrayNode.get(Integer.parseInt(textBetweenBs)), nodeToSet, path, newIndex);
        } else {
          final int size = master.size();
          IntStream.range(0, size).forEach(x -> modify(arrayNode.get(x), nodeToSet, path, newIndex));
        }
      }
    }
  }
}