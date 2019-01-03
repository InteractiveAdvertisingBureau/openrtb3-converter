package net.media.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb3.Request;
import net.media.openrtb3.Response;
import net.media.util.JacksonObjectMapper;

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
    }
  }

  private void test() throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("25To30RequestTest.json").getPath());
    BidRequestMapperImpl impl = new BidRequestMapperImpl();

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
      Request request30 = new RequestConverter().mapRequestToBidRequest(request);
      System.out.println(objectMapper.writeValueAsString(request30));
      //System.out.println(response.getResponse30());
      //System.out.println(response30.equals(response.getResponse30()));
    }
  }

  private void test1() throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("30To25BannerRequestTest.json").getPath());

    byte[] jsonData = Files.readAllBytes(file.toPath());
    ObjectMapper objectMapper = JacksonObjectMapper.getMapper();
    List<Request> testList = objectMapper.readValue(jsonData, new
      TypeReference<ArrayList<Request>>() {});
    for (Request request : testList) {
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
      BidRequest bidRequest = new RequestConverter().mapRtb3RequestToRtb24BidRequest(request);
      System.out.println(objectMapper.writeValueAsString(bidRequest));
      //System.out.println(response.getResponse30());
      //System.out.println(response30.equals(response.getResponse30()));
    }
  }
}
