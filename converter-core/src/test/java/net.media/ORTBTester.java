package net.media;

import net.media.config.Config;
import net.media.utils.JacksonObjectMapper;

import org.skyscreamer.jsonassert.JSONAssert;

import lombok.AllArgsConstructor;

/**
 * Created by rajat.go on 09/01/19.
 */

@AllArgsConstructor
public class ORTBTester<U, V> {

  private OpenRtbConverter openRtbConverter;

  public <U, V> void test(Object source, Class<U> sourceClass, Object target, Class<V> targetClass,
                          Config config) throws Exception {
    U bidRequest = JacksonObjectMapper.getMapper().convertValue(source, sourceClass);
    V converted = openRtbConverter.convert(bidRequest, sourceClass, targetClass);
    JSONAssert.assertEquals(JacksonObjectMapper.getMapper().writeValueAsString(target),
      JacksonObjectMapper.getMapper().writeValueAsString(converted), true);
  }
}
