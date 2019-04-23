package net.media;

import java.io.IOException;

public class TestRunner {
  public static void main(String[] args) {

    for (int i = 0; i < 2; i++) {
      try {
        TestCaseGenerator.main(args);
        ConverterTest converterTest = new ConverterTest();
        converterTest.run();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
