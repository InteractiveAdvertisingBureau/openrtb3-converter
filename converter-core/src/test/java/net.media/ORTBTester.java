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
import net.media.utils.JacksonObjectMapper;
import org.skyscreamer.jsonassert.JSONAssert;

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
      String inputFile) {

    String FAILURE = "FAILURE";
    try {
      U bidRequest = JacksonObjectMapper.getMapper().convertValue(source, sourceClass);
      V converted = openRtbConverter.convert(config, bidRequest, sourceClass, targetClass);

      JSONAssert.assertEquals(
          JacksonObjectMapper.getMapper().writeValueAsString(target),
          JacksonObjectMapper.getMapper().writeValueAsString(converted),
          true);

    } catch (Exception | AssertionError e) {
      OutputTestPojo outputTestPojo = new OutputTestPojo();
      outputTestPojo.setInputFile(inputFile);
      outputTestPojo.setStatus(FAILURE);
      outputTestPojo.setInputType(inputPojo.getInputType());
      outputTestPojo.setOutputType(inputPojo.getOutputType());
      outputTestPojo.setException(e.getMessage());

      if (!inputPojo.getOutputEdits().containsKey("status")
          || !inputPojo.getOutputEdits().get("status").equals("ERROR")) {
        testOutput.getFailedTestList().add(outputTestPojo);
      }
    }
  }
}
