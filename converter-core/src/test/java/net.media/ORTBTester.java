package net.media;

import net.media.config.Config;
import net.media.driver.OpenRtbConverter;
import net.media.utils.JacksonObjectMapper;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * Created by rajat.go on 09/01/19.
 */

public class ORTBTester<U, V> {

  private OpenRtbConverter openRtbConverter;

  @java.beans.ConstructorProperties({"openRtbConverter"})
  public ORTBTester(OpenRtbConverter openRtbConverter) {
    this.openRtbConverter = openRtbConverter;
  }

  public <U, V> void test(Object source, Class<U> sourceClass, Object target, Class<V> targetClass,
                          Config config, TestPojo inputPojo, TestOutput testOutput, String inputFile) throws Exception {

    String FAILURE = "FAILURE";
    try {
      U bidRequest = JacksonObjectMapper.getMapper().convertValue(source, sourceClass);
      V converted = openRtbConverter.convert(bidRequest, sourceClass, targetClass);

      JSONAssert.assertEquals(JacksonObjectMapper.getMapper().writeValueAsString(target),
                JacksonObjectMapper.getMapper().writeValueAsString(converted), true);

    } catch(Exception | AssertionError e) {
      OutputTestPojo outputTestPojo = new OutputTestPojo();
      outputTestPojo.setInputFile(inputFile);
      outputTestPojo.setStatus(FAILURE);
      outputTestPojo.setInputType(inputPojo.getInputType());
      outputTestPojo.setOutputType(inputPojo.getOutputType());
      outputTestPojo.setException(e.getMessage());

      System.out.println(e.getMessage());
      testOutput.getFailedTestList().add(outputTestPojo);
    }
  }
}
