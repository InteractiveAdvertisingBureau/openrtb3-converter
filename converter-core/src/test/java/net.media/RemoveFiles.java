package net.media;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class RemoveFiles {
  static final String basePath = "converter-core/src/test/resources/genrated/request";
  public static void main(String[] args) {

    try {
      File directory = new File(basePath);

      File[] files = directory.listFiles();
      for (File file : files) {

        if (!file.delete()) {
          System.out.println("Failed to delete "+file);

        }

      }
    } catch (Exception e) {

    }
  }
}
