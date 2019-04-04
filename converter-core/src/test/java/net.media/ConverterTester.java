package net.media;

import net.media.config.Config;
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
    File folder = new File(classLoader.getResource("generated/request").getFile());
    File[] files = folder.listFiles();
    if (nonNull(files)) {
      for (File file : files) {
        System.out.println("Running test file = " + file.getName());
        byte[] jsonData = Files.readAllBytes(file.toPath());
        TestPojo testPojo =  JacksonObjectMapper.getMapper().readValue(jsonData, TestPojo.class);
        if (isNull(testPojo) || isNull(testPojo.getInputType()) || isNull(testPojo.getOutputType
          ())) {
          Assert.assertFalse("Test file = " + file.getName() + " is incorrect", true);
        }
        if (testPojo.getInputType().equalsIgnoreCase("REQUEST25") && testPojo.getOutputType()
          .equalsIgnoreCase("REQUEST30")) {
          ortbTester.test(testPojo.getInputJson(), BidRequest.class, testPojo.getOutputJson(),
            OpenRTB.class, testPojo.getConfig());
        } else if (testPojo.getInputType().equalsIgnoreCase("REQUEST30") && testPojo.getOutputType()
          .equalsIgnoreCase("REQUEST25")) {
          ortbTester.test(testPojo.getInputJson(), BidRequest.class, testPojo.getOutputJson(),
            OpenRTB.class, testPojo.getConfig());
        } else if (testPojo.getInputType().equalsIgnoreCase("RESPONSE25") && testPojo
          .getOutputType().equalsIgnoreCase("RESPONSE30")) {
          ortbTester.test(testPojo.getInputJson(), BidResponse.class, testPojo.getOutputJson(),
            OpenRTB.class, testPojo.getConfig());
        } else if (testPojo.getInputType().equalsIgnoreCase("RESPONSE30") && testPojo
          .getOutputType().equalsIgnoreCase("RESPONSE25")) {
          ortbTester.test(testPojo.getInputJson(), OpenRTB.class, testPojo.getOutputJson(),
            BidResponse.class, testPojo.getConfig());
        } else {
          Assert.assertFalse("Test file is incorrect", true);
        }
        System.out.println("Completed execution of test file = " + file.getName());
      }
    }
  }

}
