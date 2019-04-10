package net.media;

public class TestRunner {
  public static void main(String[] args) {
    try {
      TestCaseGenerator.main(args);
      ConverterTester converterTester = new ConverterTester();
      converterTester.run();
    }
    catch(Exception e ) {
      e.printStackTrace();
    }

  }
}
