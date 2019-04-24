package net.media;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class RemoveFiles {
  static final String basePaths[] = {"converter-core/src/test/resources/generated/request","converter-core/src/test/resources/generated/response","converter-core/target/test-classes/generated/request","converter-core/target/test-classes/generated/response"};
  public static void main(String[] args) {

    try {
      for (String basePath : basePaths) {
        File directory = new File(basePath);

        File[] files = directory.listFiles();
        for (File file : files) {
          System.out.println(file.getPath().toString());
          if (!file.delete()) {
            System.out.println("Failed to delete " + file);

          }
//        break;

        }
      }
    } catch (Exception e) {

    }
  }
}
