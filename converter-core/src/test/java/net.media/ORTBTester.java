/*
 * Copyright  2019 - present. IAB Tech Lab
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
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.driver.OpenRtbConverter;
import net.media.utils.JacksonObjectMapperUtils;

import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/** Created by rajat.go on 09/01/19. */
public class ORTBTester<U, V> {

  private OpenRtbConverter openRtbConverter;

  @java.beans.ConstructorProperties({"openRtbConverter"})
  public ORTBTester(OpenRtbConverter openRtbConverter) {
    this.openRtbConverter = openRtbConverter;
  }

  public static void main(String[] args) {
    String exception =
        "\\nExpected: domainSpec\\n     but none found\\n ; \\nExpected: domainVer\\n     but none found\\n ; \\nExpected: request\\n     but none found\\n ; \\nExpected: ver\\n     but none found\\n ; \\nUnexpected: allimps\\n ; \\nUnexpected: at\\n ; \\nUnexpected: badv\\n ; \\nUnexpected: bapp\\n ; \\nUnexpected: bcat\\n ; \\nUnexpected: bseat\\n ; \\nUnexpected: cur\\n ; \\nUnexpected: device\\n ; \\nUnexpected: ext\\n ; \\nUnexpected: id\\n ; \\nUnexpected: imp\\n ; \\nUnexpected: regs\\n ; \\nUnexpected: site\\n ; \\nUnexpected: source\\n ; \\nUnexpected: test\\n ; \\nUnexpected: tmax\\n ; \\nUnexpected: user\\n ; \\nUnexpected: wlang\\n ; \\nUnexpected: wseat\\n";
    exception.replaceAll("\\n", "\\\n");
    System.out.println(exception);
  }

  public <U, V> void test(
      Object source,
      Class<U> sourceClass,
      Object target,
      Class<V> targetClass,
      Config config,
      TestPojo inputPojo,
      TestOutput testOutput,
      String inputFile,
      Map<Conversion, Converter> overRider,
      Config initconfig)
      throws Exception {

    String FAILURE = "FAILURE";
    U bidRequest;
    V converted = null;
    try {
      OpenRtbConverter tempOpenRtbConverter = openRtbConverter;
      if(initconfig != null) {
          tempOpenRtbConverter = new OpenRtbConverter(initconfig);
      }
       if(inputPojo.getInputAsString() == null) {
         bidRequest = JacksonObjectMapperUtils.getMapper().convertValue(source, sourceClass);
         if(inputPojo.getTestEnhance()==null) {
           converted = tempOpenRtbConverter.convert(config, bidRequest, sourceClass, targetClass, overRider);
         }
         else {
            converted = targetClass.newInstance();
            tempOpenRtbConverter.enhance(config,bidRequest,converted,sourceClass,targetClass,overRider);

         }
      } else {
         String stringInput = source.toString();
         String stringOutput = tempOpenRtbConverter.convert(config, stringInput, sourceClass, targetClass, overRider);
         converted = JacksonObjectMapperUtils.getMapper().readValue(stringOutput, targetClass);
      }
        JSONAssert.assertEquals(
        JacksonObjectMapperUtils.getMapper().writeValueAsString(target),
        JacksonObjectMapperUtils.getMapper().writeValueAsString(converted),
        true);

    } catch (Exception | AssertionError e) {

      OutputTestPojo outputTestPojo = new OutputTestPojo();
      outputTestPojo.setInputFile(inputFile);
      outputTestPojo.setStatus(FAILURE);
      outputTestPojo.setInputType(inputPojo.getInputType());
      outputTestPojo.setOutputType(inputPojo.getOutputType());
      outputTestPojo.setException(e.getMessage());

      if (e instanceof AssertionError || !inputPojo.getOutputEdits().containsKey("status")
          || !inputPojo.getOutputEdits().get("status").equals("ERROR")) {
        testOutput.getFailedTestList().add(outputTestPojo);
      }
    }
  }
}
