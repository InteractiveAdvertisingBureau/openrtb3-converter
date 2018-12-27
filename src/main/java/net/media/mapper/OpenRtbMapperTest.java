package net.media.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb3.Request;
import net.media.openrtb3.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class OpenRtbMapperTest {

  public static void main(String[] args) {
    try {
      new OpenRtbMapperTest().test();
    }catch (IOException e){
      System.out.println("Phatna  hihe"+e.getMessage());
    }
  }

  private void test() throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("25To30RequestTest.json").getPath());
    BidRequestMapperImpl impl = new BidRequestMapperImpl();

    byte[] jsonData = Files.readAllBytes(file.toPath());
    ObjectMapper objectMapper = new ObjectMapper();
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
      Request request30 = impl.mapRequestToBidRequest(request);
      System.out.println(request30);
      //System.out.println(response.getResponse30());
      //System.out.println(response30.equals(response.getResponse30()));
    }
  }
}
