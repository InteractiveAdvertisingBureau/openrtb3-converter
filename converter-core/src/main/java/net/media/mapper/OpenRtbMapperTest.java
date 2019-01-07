package net.media.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.media.OpenRtbConverter;
import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb3.OpenRTB;
import net.media.openrtb3.Request;
import net.media.openrtb3.Response;
import net.media.util.JacksonObjectMapper;

import javax.naming.ConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class OpenRtbMapperTest {

  public static void main(String[] args) {
    try {
      new OpenRtbMapperTest().test();
//      new OpenRtbMapperTest().test1();
    }catch (IOException e){
      System.out.println("Phatna  hihe"+e.getMessage());
    } catch (ConfigurationException e) {
      e.printStackTrace();
    } catch (OpenRtbConverterException e) {
      e.printStackTrace();
    }
  }

  private void test() throws IOException, OpenRtbConverterException, ConfigurationException {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("src/main/resources/25To30RequestTest.json").getPath());

    byte[] jsonData = Files.readAllBytes(file.toPath());
    ObjectMapper objectMapper = JacksonObjectMapper.getMapper();
    List<BidRequest> testList = objectMapper.readValue(jsonData, new
      TypeReference<ArrayList<BidRequest>>() {});
    for (BidRequest request : testList) {
      /*AdType adType = null;
      for (AdType adType1 : AdType.values()) {
        if (nonNull(response.getType()) && adType1.name().equals(response.getType())) {
          adType = adType1;
          break;
        }
      }
      if (isNull(adType)) {
        System.out.println("invalid ad type");
        continue;
      }*/

      Config config = new Config();
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());

      OpenRTB request30 = openRtbConverter.convert(request, BidRequest.class, OpenRTB.class);
      System.out.println(objectMapper.writeValueAsString(request30));
      //System.out.println(response.getResponse30());
      //System.out.println(response30.equals(response.getResponse30()));
    }
  }

  private void test1() throws IOException, OpenRtbConverterException, ConfigurationException {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("src/main/resources/30To25BannerRequestTest.json").getPath());

    byte[] jsonData = Files.readAllBytes(file.toPath());
    ObjectMapper objectMapper = JacksonObjectMapper.getMapper();
    List<OpenRTB> testList = objectMapper.readValue(jsonData, new
      TypeReference<ArrayList<OpenRTB>>() {});
    for (OpenRTB request : testList) {
      /*AdType adType = null;
      for (AdType adType1 : AdType.values()) {
        if (nonNull(response.getType()) && adType1.name().equals(response.getType())) {
          adType = adType1;
          break;
        }
      }
      if (isNull(adType)) {
        System.out.println("invalid ad type");
        continue;
      }*/
      Config config = new Config();
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());

      BidRequest bidRequest = openRtbConverter.convert(request, OpenRTB.class, BidRequest.class);
      System.out.println(objectMapper.writeValueAsString(bidRequest));
      //System.out.println(response.getResponse30());
      //System.out.println(response30.equals(response.getResponse30()));
    }
  }
}
