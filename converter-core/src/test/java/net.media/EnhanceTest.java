package net.media;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.media.config.Config;
import net.media.driver.OpenRtbConverter;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb3.Publisher;
import org.junit.Test;

import javax.naming.ConfigurationException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EnhanceTest {

  OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());


  public Publisher getPublisher30() {

    Publisher publisher = new Publisher();
    publisher.setId("pub1");
    publisher.setCattax(1);
    publisher.setCat(Collections.singletonList("1"));
    publisher.setDomain("abc.com");
    publisher.setName("randomPublisher");
    Map<String,Object> tempMap = new HashMap<>();
    tempMap.put("extra11","extraValue11");
    Map<String, Object> map = new HashMap<>();
    map.put("Extra1","extraValue1");
    map.put("extra2",tempMap);
    publisher.setExt(map);
    return publisher;

  }

  public net.media.openrtb25.request.Publisher getPublisher25() {

    net.media.openrtb25.request.Publisher publisher = new net.media.openrtb25.request.Publisher();
    publisher.setId("pub1");
    publisher.setDomain("abc.com");
    publisher.setCat(Collections.singletonList("1"));
    publisher.setName("randomPublisher");
    Map<String,Object> tempMap = new HashMap<>();
    tempMap.put("extra11","extraValue11");
    Map<String, Object> map = new HashMap<>();
    map.put("Extra1","extraValue1");
    map.put("extra2",tempMap);
    publisher.setExt(map);
    return publisher;

  }



  @Test
  public void publisherConverter2to3() {
    Boolean success = true;

    try {
      Publisher testPublisher = new Publisher();
      openRtbConverter.enhance(getPublisher25(), testPublisher, net.media.openrtb25.request.Publisher.class, Publisher.class);
      assertEquals(getPublisher30(), testPublisher);


      testPublisher = new Publisher();
      net.media.openrtb25.request.Publisher publisher = getPublisher25();
      publisher.setExt(null);
      Publisher finalPublisher = getPublisher30();
      finalPublisher.setExt(null);
      finalPublisher.setCattax(1);
      openRtbConverter.enhance(publisher, testPublisher, net.media.openrtb25.request.Publisher.class, Publisher.class);
      assertEquals(finalPublisher, testPublisher);

    }
    catch(Exception e) {
      success = false;
    }
    assertTrue(success);
  }



}
