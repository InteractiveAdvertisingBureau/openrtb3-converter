package net.media.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.media.enums.AdType;
import net.media.openrtb3.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class OpenRtb24To3Mapper {

  public static void main(String[] args) {
    try {
      new OpenRtb24To3Mapper().test();
    }catch (IOException e){
      System.out.println("Phatna  hihe"+e.getMessage());
    }
  }

  private void test() throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("25To30Test.json").getFile());
    OpenRtb24To3MapperImpl impl = new OpenRtb24To3MapperImpl();

    byte[] jsonData = Files.readAllBytes(file.toPath());
    ObjectMapper objectMapper = new ObjectMapper();
    List<ResponseTestPojo> testList = objectMapper.readValue(jsonData, new
      TypeReference<ArrayList<ResponseTestPojo>>() {});
    for (ResponseTestPojo response : testList) {
      AdType adType = null;
      for (AdType adType1 : AdType.values()) {
        if (nonNull(response.getType()) && adType1.name().equals(response.getType())) {
          adType = adType1;
          break;
        }
      }
      if (isNull(adType)) {
        System.out.println("invalid ad type");
        continue;
      }
      Response response30 = impl.map(response.getResponse25(), adType);
      System.out.println(response30);
      System.out.println(response.getResponse30());
      System.out.println(response30.equals(response.getResponse30()));
    }
  }

}
