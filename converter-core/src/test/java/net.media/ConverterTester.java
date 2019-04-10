package net.media;

import net.media.config.Config;
import net.media.driver.OpenRtbConverter;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.response.BidResponse;
import net.media.openrtb3.OpenRTB;
import net.media.utils.JacksonObjectMapper;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 09/01/19.
 */
public class ConverterTester {

  @Test
  public void run() throws Exception {
    OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
    ClassLoader classLoader = getClass().getClassLoader();
    ORTBTester ortbTester = new ORTBTester(openRtbConverter);
    TestOutput testOutput = new TestOutput();

    File folder = new File(classLoader.getResource("generated").getFile());
    File[] innerFolder = folder.listFiles();

    //files.
    File outputFile = new File(classLoader.getResource("generatedOutput").getPath()+"/Output.json");
    if (nonNull(innerFolder)) {
      Long totalFiles = 0l;
      for (File files : innerFolder) {
        totalFiles += files.listFiles().length;
        for (File file : files.listFiles()) {
//          System.out.println("Running test file = " + file.getName());

          byte[] jsonData = Files.readAllBytes(file.toPath());
          TestPojo testPojo = null;
          try {
            testPojo = JacksonObjectMapper.getMapper().readValue(jsonData, TestPojo.class);
          } catch (Exception e) {
          }
          if (isNull(testPojo) || isNull(testPojo.getInputType()) || isNull(testPojo.getOutputType
                  ())) {
            OutputTestPojo outputTestPojo = new OutputTestPojo();
            outputTestPojo.setInputFile(file.getName());
            outputTestPojo.setStatus("FAILURE");
            outputTestPojo.setException("Test file = " + file.getName() + " is incorrect");
            testOutput.getFailedTestList().add(outputTestPojo);
          } else if (testPojo.getInputType().equalsIgnoreCase("REQUEST25") && testPojo.getOutputType()
                  .equalsIgnoreCase("REQUEST30")) {
            ortbTester.test(testPojo.getInputJson(), BidRequest.class, testPojo.getOutputJson(),
                    OpenRTB.class, testPojo.getParams(), testPojo, testOutput, file.getName());
          } else if (testPojo.getInputType().equalsIgnoreCase("REQUEST30") && testPojo.getOutputType()
                  .equalsIgnoreCase("REQUEST25")) {
            ortbTester.test(testPojo.getInputJson(), BidRequest.class, testPojo.getOutputJson(),
                    OpenRTB.class, testPojo.getParams(), testPojo, testOutput, file.getName());
          } else if (testPojo.getInputType().equalsIgnoreCase("RESPONSE25") && testPojo
                  .getOutputType().equalsIgnoreCase("RESPONSE30")) {
            ortbTester.test(testPojo.getInputJson(), BidResponse.class, testPojo.getOutputJson(),
                    OpenRTB.class, testPojo.getParams(), testPojo, testOutput, file.getName());
          } else if (testPojo.getInputType().equalsIgnoreCase("RESPONSE30") && testPojo
                  .getOutputType().equalsIgnoreCase("RESPONSE25")) {
            ortbTester.test(testPojo.getInputJson(), OpenRTB.class, testPojo.getOutputJson(),
                    BidResponse.class, testPojo.getParams(), testPojo, testOutput, file.getName());
          } else {
            OutputTestPojo outputTestPojo = new OutputTestPojo();
            outputTestPojo.setInputFile(file.getName());
            outputTestPojo.setStatus("FAILURE");
            outputTestPojo.setInputType(testPojo.getInputType());
            outputTestPojo.setOutputType(testPojo.getOutputType());
            outputTestPojo.setException("Test file is incorrect");
            testOutput.getFailedTestList().add(outputTestPojo);
          }
//          System.out.println("Completed execution of test file = " + file.getName());
        }
    }
      testOutput.setTotalTestCases(totalFiles);
      testOutput.setFailedTestCases(testOutput.getFailedTestList().size());
//      System.out.println(JacksonObjectMapper.getMapper().writeValueAsString(testOutput));
      JacksonObjectMapper.getMapper().writerWithDefaultPrettyPrinter().writeValue(outputFile, testOutput);
      Assert.assertEquals("Failing test cases. For more info check generatedOutput/Output.json",0, testOutput.getFailedTestList().size());
    }
  }

}
