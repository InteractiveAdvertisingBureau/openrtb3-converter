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

import net.media.config.Config;
import net.media.driver.OpenRtbConverter;
import net.media.enums.OpenRtbVersion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.BidRequest2_X;
import net.media.openrtb25.response.BidResponse2_X;
import net.media.openrtb3.OpenRTBWrapper3_X;
import net.media.utils.JacksonObjectMapper;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.naming.ConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/** Created by rajat.go on 09/01/19. */
public class ConverterTest {

  @Test
  public void run() throws Exception {

    ClassLoader classLoader = getClass().getClassLoader();
    OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());

    ORTBTester ortbTester = new ORTBTester(openRtbConverter);
    TestOutput testOutput = new TestOutput();

    File folder = new File(classLoader.getResource("generated").getFile());
    File[] innerFolder = folder.listFiles();

    // files.
    File outputFile =
        new File(classLoader.getResource("generatedOutput").getPath() + "/Output.json");
    if (nonNull(innerFolder)) {
      Long totalFiles = 0L;
      for (File files : innerFolder) {
        totalFiles += files.listFiles().length;
        for (File file : files.listFiles()) {
//          if(file.getName().equals("nativeResponseStringInitConfigTrue.json")==false)
//            continue;
          //System.out.println("file: " + file );
          Exception exception = new Exception();
          byte[] jsonData = Files.readAllBytes(file.toPath());
          TestPojo testPojo = null;
          try {
            testPojo = JacksonObjectMapper.getMapper().readValue(jsonData, TestPojo.class);
          } catch (Exception e) {
            exception = e;
          }
          if (isNull(testPojo)
              || isNull(testPojo.getInputType())
              || isNull(testPojo.getOutputType())) {
            /*OutputTestPojo outputTestPojo = new OutputTestPojo();
            outputTestPojo.setInputFile(file.getName());
            outputTestPojo.setStatus("FAILURE");
            outputTestPojo.setException("Test file = " + file.getName() + " is incorrect");
            testOutput.getFailedTestList().add(outputTestPojo);*/
            OutputTestPojo outputTestPojo = new OutputTestPojo();
            Map<String, Object> inputPojo = JacksonObjectMapper.getMapper().readValue(jsonData, Map.class);
            outputTestPojo.setInputFile(null);
            outputTestPojo.setStatus("FAILURE");
            outputTestPojo.setInputType((String) inputPojo.get("inputType"));
            outputTestPojo.setOutputType((String) inputPojo.get("outputType"));
            outputTestPojo.setException(exception.getMessage());

            if (!((Map)inputPojo.get("outputEdits")).containsKey("status")
              || !((Map)inputPojo.get("outputEdits")).get("status").equals("ERROR")) {
              testOutput.getFailedTestList().add(outputTestPojo);
            }
          } else if (testPojo.getInputType().equalsIgnoreCase("REQUEST25")
              && testPojo.getOutputType().equalsIgnoreCase("REQUEST30")) {
            ortbTester.test(
                testPojo.getInputJson(),
                BidRequest2_X.class,
                testPojo.getOutputJson(),
                OpenRTBWrapper3_X.class,
                testPojo.getParams(),
                testPojo,
                testOutput,
                file.getName(),
                testPojo.getConfig());
          } else if (testPojo.getInputType().equalsIgnoreCase("REQUEST30")
              && testPojo.getOutputType().equalsIgnoreCase("REQUEST25")) {
            ortbTester.test(
                testPojo.getInputJson(),
                OpenRTBWrapper3_X.class,
                testPojo.getOutputJson(),
                BidRequest2_X.class,
                testPojo.getParams(),
                testPojo,
                testOutput,
                file.getName(),
                testPojo.getConfig());
          } else if (testPojo.getInputType().equalsIgnoreCase("RESPONSE25")
              && testPojo.getOutputType().equalsIgnoreCase("RESPONSE30")) {
            ortbTester.test(
                testPojo.getInputJson(),
                BidResponse2_X.class,
                testPojo.getOutputJson(),
                OpenRTBWrapper3_X.class,
                testPojo.getParams(),
                testPojo,
                testOutput,
                file.getName(),
                testPojo.getConfig());
          } else if (testPojo.getInputType().equalsIgnoreCase("RESPONSE30")
              && testPojo.getOutputType().equalsIgnoreCase("RESPONSE25")) {
            ortbTester.test(
                testPojo.getInputJson(),
                OpenRTBWrapper3_X.class,
                testPojo.getOutputJson(),
                BidResponse2_X.class,
                testPojo.getParams(),
                testPojo,
                testOutput,
                file.getName(),
                testPojo.getConfig());
          } else {
            OutputTestPojo outputTestPojo = new OutputTestPojo();
            outputTestPojo.setInputFile(file.getName());
            outputTestPojo.setStatus("FAILURE");
            outputTestPojo.setInputType(testPojo.getInputType());
            outputTestPojo.setOutputType(testPojo.getOutputType());
            outputTestPojo.setException("Test file is incorrect");
            testOutput.getFailedTestList().add(outputTestPojo);
          }
        }
      }
      System.out.println("total cases : "+totalFiles);
      System.out.println("failed cases : "+testOutput.getFailedTestList().size());
      testOutput.setTotalTestCases(totalFiles);
      testOutput.setFailedTestCases(testOutput.getFailedTestList().size());
      JacksonObjectMapper.getMapper()
          .writerWithDefaultPrettyPrinter()
          .writeValue(outputFile, testOutput);
      Assert.assertEquals(
          "Failing test cases. For more info check generatedOutput/Output.json",
          0,
          testOutput.getFailedTestList().size());
    }
  }

  @Test
  public void configOrtbversion24() throws IOException, OpenRtbConverterException, ConfigurationException, JSONException {
    Boolean success = true;
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_30.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config config = new Config();
      config.setOpenRtbVersion2_XVersion(OpenRtbVersion.TWO_DOT_FOUR);
      BidRequest2_X converted = openRtbConverter.convert(config, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_24.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target).replaceAll("\\s+", ""),
        JacksonObjectMapper.getMapper().writeValueAsString(converted).replaceAll("\\s+", ""),
        true);
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void configOrtbversion23() {
    Boolean success = true;
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      Config config = new Config();
      //config.setOpenRtbVersion2_XVersion(OpenRtbVersion.TWO_DOT_FIVE);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_30.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      config.setOpenRtbVersion2_XVersion(OpenRtbVersion.TWO_DOT_THREE);
      BidRequest2_X converted = openRtbConverter.convert(config, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_23.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target).replaceAll("\\s+", ""),
        JacksonObjectMapper.getMapper().writeValueAsString(converted).replaceAll("\\s+", ""),
        true);
    } catch (Exception | AssertionError e) {
      System.out.println(e);
      success = false;
    }
    Assert.assertTrue(success);
  }
}
