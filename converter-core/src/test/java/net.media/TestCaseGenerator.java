/*
 * Copyright  2019 - present. MEDIA.NET ADVERTISING FZ-LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.media;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestCaseGenerator {

  static final ObjectMapper objectMapper = new ObjectMapper();
  static final String basePath = "converter-core/src/test/resources/";

  public static void main(String[] args) throws IOException {
    final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    /*mapper.registerModule(new ParameterNamesModule())
    .registerModule(new Jdk8Module())
    .registerModule(new JavaTimeModule());*/
    mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    for (Path rootPath : Files.list(Paths.get(basePath + "edits")).collect(Collectors.toList())) {
      for (Path path : Files.list(rootPath).collect(Collectors.toList())) {
        //        if(path.getFileName().toString().equals("testScript_OverridingConverter.txt") ==
        // false)
        //          continue;
        System.out.println(path.getFileName().toString());
        String json2 = new String(Files.readAllBytes(path));
        final Test test = mapper.readValue(json2, Test.class);

        for (Case aCase : test.getCases()) {
          try {
            generateJsons(aCase, rootPath.getFileName().toString());
            objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(
                    new File(
                        basePath
                            + "generated/"
                            + rootPath.getFileName().toString()
                            + "/"
                            + aCase.getPurpose()
                            + ".json"),
                    aCase);
            /*try (FileWriter writer = new FileWriter(new File(basePath + "request/" + aCase.getPurpose() + ".yaml"))) {
              new Yaml().dump(map, writer);
            }*/
          } catch (Exception e) {
            StringWriter writer = new StringWriter();
            PrintWriter writer1 = new PrintWriter(writer);
            e.printStackTrace(writer1);
            System.out.println(path.toAbsolutePath().toString());
            System.out.println(writer.toString());
            System.out.println("----------------------------------");
          }
        }
      }
    }
  }

  public static Case generateJsons(Case aCase, String rootDir) throws IOException {

    String inputJson =
        new String(
            Files.readAllBytes(
                Paths.get(basePath + "master/" + rootDir + "/" + aCase.getInputFile())));
    JsonNode inputJsonObject = objectMapper.readValue(inputJson, JsonNode.class);
    for (Map.Entry<String, String> entry : aCase.getInputEdits().entrySet()) {
      modify(inputJsonObject, getNode(entry.getValue()), entry.getKey().split("\\."), 0);
    }
    aCase.setInputJson(inputJsonObject);

    if (aCase.getOutputFile() != null
        && !aCase.getOutputFile().trim().equals("null")
        && !aCase.getOutputFile().trim().equals("")) {
      String outputJson =
          new String(
              Files.readAllBytes(
                  Paths.get(basePath + "master/" + rootDir + "/" + aCase.getOutputFile())));
      JsonNode outputJsonObject = objectMapper.readValue(outputJson, JsonNode.class);
      for (Map.Entry<String, String> entry : aCase.getOutputEdits().entrySet()) {
        modify(outputJsonObject, getNode(entry.getValue()), entry.getKey().split("\\."), 0);
      }
      aCase.setOutputJson(outputJsonObject);
    }

    return aCase;
  }

  public static JsonNode getNode(String text) {
    if (text == null || text.trim().equals("null")) {
      return NullNode.getInstance();
    }
    final String trimmedText = text.trim();
    if (!trimmedText.startsWith("\"") && !trimmedText.equals("ERROR")) {
      if (trimmedText.equals("true") || trimmedText.equals("false")) {
        return BooleanNode.valueOf(Boolean.parseBoolean(trimmedText));
      }
      if (trimmedText.contains(".")) {
        return DoubleNode.valueOf(Double.parseDouble(trimmedText));
      }
      return IntNode.valueOf(Integer.parseInt(trimmedText));
    }
    return objectMapper.valueToTree(trimmedText.substring(1, trimmedText.length() - 1));
  }

  public static void modify(JsonNode master, JsonNode nodeToSet, String[] path, int index) {
    try {
      String fieldName = path[index].trim();
      int newIndex = index + 1;
      if (!fieldName.contains("[")) {
        if (index == path.length - 1) {
          if (nodeToSet == NullNode.getInstance()) {
            ((ObjectNode) master).remove(fieldName);
            //            jsonNode = NullNode.getInstance();

            return;
          }
          /*if (request.get(fieldName).isContainerNode()) {
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
          if (textBetweenBs.contains(",")) {
            final String[] elements = textBetweenBs.split(",");
            Arrays.stream(elements)
                .forEach(
                    x ->
                        padArrayNode(((ArrayNode) arrayNode), Integer.parseInt(x.trim()))
                            .set(Integer.parseInt(x.trim()), getNode(nodeToSet.toString())));
          } else if (!textBetweenBs.equals("*")) {
            padArrayNode(((ArrayNode) arrayNode), Integer.parseInt(textBetweenBs))
                .set(Integer.parseInt(textBetweenBs), getNode(nodeToSet.toString()));
          } else {
            final int size = arrayNode.size();
            IntStream.range(0, size)
                .forEach(
                    x ->
                        padArrayNode(((ArrayNode) arrayNode), x)
                            .set(x, getNode(nodeToSet.toString())));
          }
        } else {
          if (textBetweenBs.contains(",")) {
            final String[] elements = textBetweenBs.split(",");
            Arrays.stream(elements)
                .forEach(
                    x ->
                        modify(
                            arrayNode.get(Integer.parseInt(x.trim())), nodeToSet, path, newIndex));
          } else if (!textBetweenBs.equals("*")) {
            modify(arrayNode.get(Integer.parseInt(textBetweenBs)), nodeToSet, path, newIndex);
          } else {
            final int size = master.size();
            IntStream.range(0, size)
                .forEach(x -> modify(arrayNode.get(x), nodeToSet, path, newIndex));
          }
        }
      }
    } catch (Exception e) {
      System.out.println(path[index]);
      throw new RuntimeException(e);
    }
  }

  public static ArrayNode padArrayNode(ArrayNode arrayNode, int index) {
    if (index < 0) {
      throw new RuntimeException("Index cannot be less than 0");
    }
    if (index + 1 <= arrayNode.size()) {
      return arrayNode;
    }
    for (int i = 0; i <= index - arrayNode.size(); i++) {
      arrayNode.add(NullNode.getInstance());
    }
    return arrayNode;
  }
}
