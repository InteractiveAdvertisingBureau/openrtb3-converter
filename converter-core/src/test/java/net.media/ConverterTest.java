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
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.driver.OpenRtbConverter;
import net.media.enums.AdType;
import net.media.enums.OpenRtbVersion;
import net.media.openrtb25.request.App;
import net.media.openrtb25.request.BidRequest2_X;
import net.media.openrtb25.request.Imp;
import net.media.openrtb25.response.BidResponse2_X;
import net.media.openrtb25.response.SeatBid;
import net.media.openrtb25.response.nativeresponse.AssetResponse;
import net.media.openrtb25.response.nativeresponse.NativeResponse;
import net.media.openrtb3.*;
import net.media.utils.JacksonObjectMapper;
import org.json.JSONException;
import net.media.utils.JacksonObjectMapperUtils;

import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/** Created by rajat.go on 09/01/19. */
public class ConverterTest {

  @Test
  public void run() throws Exception {
    OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
    ClassLoader classLoader = getClass().getClassLoader();
    ORTBTester ortbTester = new ORTBTester(openRtbConverter);
    TestOutput testOutput = new TestOutput();

    File folder = new File(classLoader.getResource("generated").getFile());
    File[] innerFolder = folder.listFiles();

    // files.
    File outputFile =
        new File(classLoader.getResource("generatedOutput").getPath() + "/Output.json");
    if (nonNull(innerFolder)) {
      Long totalFiles = 0L;
      for (File files : innerFolder) {
        totalFiles += files.listFiles().length;
        for (File file : files.listFiles()) {
          Exception exception = new Exception();
          byte[] jsonData = Files.readAllBytes(file.toPath());
          TestPojo testPojo = null;
          try {
            testPojo = JacksonObjectMapperUtils.getMapper().readValue(jsonData, TestPojo.class);
          } catch (Exception e) {
            exception = e;
          }
          if (isNull(testPojo)
              || isNull(testPojo.getInputType())
              || isNull(testPojo.getOutputType())) {
            OutputTestPojo outputTestPojo = new OutputTestPojo();
            Map<String, Object> inputPojo =
              JacksonObjectMapperUtils.getMapper().readValue(jsonData, Map.class);
            outputTestPojo.setInputFile(null);
            outputTestPojo.setStatus("FAILURE");
            outputTestPojo.setInputType((String) inputPojo.get("inputType"));
            outputTestPojo.setOutputType((String) inputPojo.get("outputType"));
            outputTestPojo.setException(exception.getMessage());

            if (!((Map) inputPojo.get("outputEdits")).containsKey("status")
              || !((Map) inputPojo.get("outputEdits")).get("status").equals("ERROR")) {
              testOutput.getFailedTestList().add(outputTestPojo);
            }
            continue;
          }

          Map<Conversion, Converter> overRider = null;

          if (testPojo.getOverRidingMap() != null) {
            try {
              overRider = new HashMap<>();
              Map<String, Object> overRidingMap = testPojo.getOverRidingMap();
              for (Object over : testPojo.getOverRidingMap().values()) {
                Map<String, String> tempMap = (Map<String, String>) over;
                Class<?> src = Class.forName(tempMap.get("sourceClass"));
                Class<?> target = Class.forName(tempMap.get("targetClass"));
                Class<?> customConverter = Class.forName(tempMap.get("converterClass"));
                Converter<App, App> converter1 =
                  (Converter<App, App>) customConverter.newInstance();
                overRider.put(new Conversion<>(src, target), converter1);
              }
            } catch (Exception e) {
              System.out.println("Wrong Converter name or class provided");
            }
          }

          if (testPojo.getInputType().equalsIgnoreCase("REQUEST25")
              && testPojo.getOutputType().equalsIgnoreCase("REQUEST30")) {
            ortbTester.test(
                testPojo.getInputJson(),
                BidRequest2_X.class,
                testPojo.getOutputJson(),
                OpenRTBWrapper3_X.class,
                testPojo.getParams(),
                testPojo,
                testOutput,
              file.getName(),
              overRider,
                testPojo.getConfig());
          } else if (testPojo.getInputType().equalsIgnoreCase("REQUEST30")
              && testPojo.getOutputType().equalsIgnoreCase("REQUEST25")) {
            ortbTester.test(
                testPojo.getInputJson(),
                OpenRTBWrapper3_X.class,
                testPojo.getOutputJson(),
                BidRequest2_X.class,
                testPojo.getParams(),
                testPojo,
                testOutput,
              file.getName(),
              overRider,
                testPojo.getConfig());
          } else if (testPojo.getInputType().equalsIgnoreCase("RESPONSE25")
              && testPojo.getOutputType().equalsIgnoreCase("RESPONSE30")) {
            ortbTester.test(
                testPojo.getInputJson(),
                BidResponse2_X.class,
                testPojo.getOutputJson(),
                OpenRTBWrapper3_X.class,
                testPojo.getParams(),
                testPojo,
                testOutput,
              file.getName(),
              overRider,
                testPojo.getConfig());
          } else if (testPojo.getInputType().equalsIgnoreCase("RESPONSE30")
              && testPojo.getOutputType().equalsIgnoreCase("RESPONSE25")) {
            ortbTester.test(
                testPojo.getInputJson(),
                OpenRTBWrapper3_X.class,
                testPojo.getOutputJson(),
                BidResponse2_X.class,
                testPojo.getParams(),
                testPojo,
                testOutput,
              file.getName(),
              overRider,
                testPojo.getConfig());
          }
          else {
            Class<?> src = Class.forName(testPojo.getInputType());
            Class<?> target = Class.forName(testPojo.getOutputType());
            ortbTester.test(testPojo.getInputJson(),src,testPojo.getOutputJson(),target,testPojo.getParams(),testPojo,testOutput,file.getName(),overRider,testPojo.getConfig());
          }
        }
      }
      System.out.println("total cases : " + totalFiles);
      System.out.println("failed cases : " + testOutput.getFailedTestList().size());
      testOutput.setTotalTestCases(totalFiles);
      testOutput.setFailedTestCases(testOutput.getFailedTestList().size());
      JacksonObjectMapperUtils.getMapper()
          .writerWithDefaultPrettyPrinter()
          .writeValue(outputFile, testOutput);
      Assert.assertEquals(
          "Failing test cases. For more info check generatedOutput/Output.json",
          0,
          testOutput.getFailedTestList().size());
    }
  }

  @Test
  public void testCloningEnabledRequestBannerSite3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_30.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidRequest2_X converted = openRtbConverter.convert(config, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()!=converted.getCur())
        success = false;
      if(request.getSeat()!=converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() != ((Imp) converted.getImp().toArray()[i]).getIframebuster())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getMime() != ((Imp) converted.getImp().toArray()[i]).getBanner().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getApi() != ((Imp) converted.getImp().toArray()[i]).getBanner().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getCtype() != ((Imp) converted.getImp().toArray()[i]).getBanner().getExt().get("ctype"))
          success = false;

      }
      if(request.getContext().getSite().getPub().getCat() != converted.getSite().getPublisher().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getCat() != converted.getSite().getContent().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getProducer().getCat() != converted.getSite().getContent().getProducer().getCat())
        success = false;
      if(request.getContext().getSite().getCat() != converted.getSite().getCat())
        success = false;
      if(request.getContext().getSite().getSectcat() != converted.getSite().getSectioncat())
        success = false;
      if(request.getContext().getSite().getPagecat() != converted.getSite().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningEnabledRequestNativeApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidRequest2_X converted = openRtbConverter.convert(config, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()!=converted.getCur())
        success = false;
      if(request.getSeat()!=converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() != ((Imp) converted.getImp().toArray()[i]).getIframebuster())
          success = false;
        for(int j=0;j<((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().size();j++) {
          ImageAssetFormat img = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getImg();
          if(nonNull(img)) {
            if (img.getMime() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getImg().getMimes())
              success = false;
          }
          VideoPlacement video = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getVideo();
          if(nonNull(video)) {
            if (video.getMime() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getMimes())
              success = false;
            if (video.getApi() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("api"))
              success = false;
            if (video.getCtype() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getProtocols())
              success = false;
            if (video.getDelivery() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("delivery"))
              success = false;
            if (video.getComptype() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("companiontype"))
              success = false;
          }
        }
      }
      if(request.getContext().getApp().getCat() != converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningEnabledRequestVideoApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidRequest2_X converted = openRtbConverter.convert(config, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()!=converted.getCur())
        success = false;
      if(request.getSeat()!=converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getMime()!=  ((Imp) converted.getImp().toArray()[i]).getVideo().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getApi()!=  ((Imp) converted.getImp().toArray()[i]).getVideo().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getCtype()!=  ((Imp) converted.getImp().toArray()[i]).getVideo().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getDelivery()!=  ((Imp) converted.getImp().toArray()[i]).getVideo().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() != converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningEnabledRequestAudioApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidRequest2_X converted = openRtbConverter.convert(config, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()!=converted.getCur())
        success = false;
      if(request.getSeat()!=converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getMime()!=  ((Imp) converted.getImp().toArray()[i]).getAudio().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getApi()!=  ((Imp) converted.getImp().toArray()[i]).getAudio().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getCtype()!=  ((Imp) converted.getImp().toArray()[i]).getAudio().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getDelivery()!=  ((Imp) converted.getImp().toArray()[i]).getAudio().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() != converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningDisabledRequestBannerSite3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_30.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidRequest2_X converted = openRtbConverter.convert(config, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()==converted.getCur())
        success = false;
      if(request.getSeat()==converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() == ((Imp) converted.getImp().toArray()[i]).getIframebuster())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getMime() == ((Imp) converted.getImp().toArray()[i]).getBanner().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getApi() == ((Imp) converted.getImp().toArray()[i]).getBanner().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getCtype() == ((Imp) converted.getImp().toArray()[i]).getBanner().getExt().get("ctype"))
          success = false;
      }
      if(request.getContext().getSite().getPub().getCat() == converted.getSite().getPublisher().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getCat() == converted.getSite().getContent().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getProducer().getCat() == converted.getSite().getContent().getProducer().getCat())
        success = false;
      if(request.getContext().getSite().getCat() == converted.getSite().getCat())
        success = false;
      if(request.getContext().getSite().getSectcat() == converted.getSite().getSectioncat())
        success = false;
      if(request.getContext().getSite().getPagecat() == converted.getSite().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningDisabledRequestNativeApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidRequest2_X converted = openRtbConverter.convert(config, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()==converted.getCur())
        success = false;
      if(request.getSeat()==converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() == ((Imp) converted.getImp().toArray()[i]).getIframebuster())
          success = false;
        for(int j=0;j<((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().size();j++) {
          ImageAssetFormat img = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getImg();
          if(nonNull(img)) {
            if (img.getMime() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getImg().getMimes())
              success = false;
          }
          VideoPlacement video = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getVideo();
          if(nonNull(video)) {
            if (video.getMime() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getMimes())
              success = false;
            if (video.getApi() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("api"))
              success = false;
            if (video.getCtype() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getProtocols())
              success = false;
            if (video.getDelivery() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("delivery"))
              success = false;
            if (video.getComptype() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("companiontype"))
              success = false;
          }
        }
      }
      if(request.getContext().getApp().getCat() == converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningDisabledRequestVideoApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidRequest2_X converted = openRtbConverter.convert(config, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()==converted.getCur())
        success = false;
      if(request.getSeat()==converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getMime()==  ((Imp) converted.getImp().toArray()[i]).getVideo().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getApi()==  ((Imp) converted.getImp().toArray()[i]).getVideo().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getCtype()==  ((Imp) converted.getImp().toArray()[i]).getVideo().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getDelivery()==  ((Imp) converted.getImp().toArray()[i]).getVideo().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() == converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningDisabledRequestAudioApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidRequest2_X converted = openRtbConverter.convert(config, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()==converted.getCur())
        success = false;
      if(request.getSeat()==converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getMime()==  ((Imp) converted.getImp().toArray()[i]).getAudio().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getApi()==  ((Imp) converted.getImp().toArray()[i]).getAudio().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getCtype()==  ((Imp) converted.getImp().toArray()[i]).getAudio().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getDelivery()==  ((Imp) converted.getImp().toArray()[i]).getAudio().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() == converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningInitEnabledRequestBannerSite3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_30.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidRequest2_X converted = openRtbConverter.convert(new Config(), ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()!=converted.getCur())
        success = false;
      if(request.getSeat()!=converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() != ((Imp) converted.getImp().toArray()[i]).getIframebuster())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getMime() != ((Imp) converted.getImp().toArray()[i]).getBanner().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getApi() != ((Imp) converted.getImp().toArray()[i]).getBanner().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getCtype() != ((Imp) converted.getImp().toArray()[i]).getBanner().getExt().get("ctype"))
          success = false;
      }
      if(request.getContext().getSite().getPub().getCat() != converted.getSite().getPublisher().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getCat() != converted.getSite().getContent().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getProducer().getCat() != converted.getSite().getContent().getProducer().getCat())
        success = false;
      if(request.getContext().getSite().getCat() != converted.getSite().getCat())
        success = false;
      if(request.getContext().getSite().getSectcat() != converted.getSite().getSectioncat())
        success = false;
      if(request.getContext().getSite().getPagecat() != converted.getSite().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningInitEnabledRequestNativeApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidRequest2_X converted = openRtbConverter.convert(new Config(), ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()!=converted.getCur())
        success = false;
      if(request.getSeat()!=converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() != ((Imp) converted.getImp().toArray()[i]).getIframebuster())
          success = false;
        for(int j=0;j<((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().size();j++) {
          ImageAssetFormat img = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getImg();
          if(nonNull(img)) {
            if (img.getMime() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getImg().getMimes())
              success = false;
          }
          VideoPlacement video = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getVideo();
          if(nonNull(video)) {
            if (video.getMime() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getMimes())
              success = false;
            if (video.getApi() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("api"))
              success = false;
            if (video.getCtype() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getProtocols())
              success = false;
            if (video.getDelivery() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("delivery"))
              success = false;
            if (video.getComptype() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("companiontype"))
              success = false;
          }
        }
      }
      if(request.getContext().getApp().getCat() != converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningInitEnabledRequestVideoApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidRequest2_X converted = openRtbConverter.convert(new Config(), ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()!=converted.getCur())
        success = false;
      if(request.getSeat()!=converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getMime()!=  ((Imp) converted.getImp().toArray()[i]).getVideo().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getApi()!=  ((Imp) converted.getImp().toArray()[i]).getVideo().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getCtype()!=  ((Imp) converted.getImp().toArray()[i]).getVideo().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getDelivery()!=  ((Imp) converted.getImp().toArray()[i]).getVideo().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() != converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningInitEnabledRequestAudioApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidRequest2_X converted = openRtbConverter.convert(new Config(), ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()!=converted.getCur())
        success = false;
      if(request.getSeat()!=converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getMime()!=  ((Imp) converted.getImp().toArray()[i]).getAudio().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getApi()!=  ((Imp) converted.getImp().toArray()[i]).getAudio().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getCtype()!=  ((Imp) converted.getImp().toArray()[i]).getAudio().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getDelivery()!=  ((Imp) converted.getImp().toArray()[i]).getAudio().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() != converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningInitDisabledRequestBannerSite3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_30.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidRequest2_X converted = openRtbConverter.convert(new Config(), ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()==converted.getCur())
        success = false;
      if(request.getSeat()==converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() == ((Imp) converted.getImp().toArray()[i]).getIframebuster())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getMime() == ((Imp) converted.getImp().toArray()[i]).getBanner().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getApi() == ((Imp) converted.getImp().toArray()[i]).getBanner().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getCtype() == ((Imp) converted.getImp().toArray()[i]).getBanner().getExt().get("ctype"))
          success = false;
      }
      if(request.getContext().getSite().getPub().getCat() == converted.getSite().getPublisher().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getCat() == converted.getSite().getContent().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getProducer().getCat() == converted.getSite().getContent().getProducer().getCat())
        success = false;
      if(request.getContext().getSite().getCat() == converted.getSite().getCat())
        success = false;
      if(request.getContext().getSite().getSectcat() == converted.getSite().getSectioncat())
        success = false;
      if(request.getContext().getSite().getPagecat() == converted.getSite().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningInitDisabledRequestNativeApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidRequest2_X converted = openRtbConverter.convert(new Config(), ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()==converted.getCur())
        success = false;
      if(request.getSeat()==converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() == ((Imp) converted.getImp().toArray()[i]).getIframebuster())
          success = false;
        for(int j=0;j<((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().size();j++) {
          ImageAssetFormat img = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getImg();
          if(nonNull(img)) {
            if (img.getMime() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getImg().getMimes())
              success = false;
          }
          VideoPlacement video = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getVideo();
          if(nonNull(video)) {
            if (video.getMime() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getMimes())
              success = false;
            if (video.getApi() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("api"))
              success = false;
            if (video.getCtype() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getProtocols())
              success = false;
            if (video.getDelivery() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("delivery"))
              success = false;
            if (video.getComptype() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("companiontype"))
              success = false;
          }
        }
      }
      if(request.getContext().getApp().getCat() == converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningInitDisabledRequestVideoApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidRequest2_X converted = openRtbConverter.convert(new Config(), ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()==converted.getCur())
        success = false;
      if(request.getSeat()==converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getMime()==  ((Imp) converted.getImp().toArray()[i]).getVideo().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getApi()==  ((Imp) converted.getImp().toArray()[i]).getVideo().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getCtype()==  ((Imp) converted.getImp().toArray()[i]).getVideo().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getDelivery()==  ((Imp) converted.getImp().toArray()[i]).getVideo().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() == converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningInitDisabledRequestAudioApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidRequest2_X converted = openRtbConverter.convert(new Config(), ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()==converted.getCur())
        success = false;
      if(request.getSeat()==converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getMime()==  ((Imp) converted.getImp().toArray()[i]).getAudio().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getApi()==  ((Imp) converted.getImp().toArray()[i]).getAudio().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getCtype()==  ((Imp) converted.getImp().toArray()[i]).getAudio().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getDelivery()==  ((Imp) converted.getImp().toArray()[i]).getAudio().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() == converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningOverrideEnabledRequestBannerSite3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_30.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setDisableCloning(true);
      BidRequest2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()!=converted.getCur())
        success = false;
      if(request.getSeat()!=converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() != ((Imp) converted.getImp().toArray()[i]).getIframebuster())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getMime() != ((Imp) converted.getImp().toArray()[i]).getBanner().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getApi() != ((Imp) converted.getImp().toArray()[i]).getBanner().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getCtype() != ((Imp) converted.getImp().toArray()[i]).getBanner().getExt().get("ctype"))
          success = false;
      }
      if(request.getContext().getSite().getPub().getCat() != converted.getSite().getPublisher().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getCat() != converted.getSite().getContent().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getProducer().getCat() != converted.getSite().getContent().getProducer().getCat())
        success = false;
      if(request.getContext().getSite().getCat() != converted.getSite().getCat())
        success = false;
      if(request.getContext().getSite().getSectcat() != converted.getSite().getSectioncat())
        success = false;
      if(request.getContext().getSite().getPagecat() != converted.getSite().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningOverrideEnabledRequestNativeApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setDisableCloning(true);
      BidRequest2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()!=converted.getCur())
        success = false;
      if(request.getSeat()!=converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() != ((Imp) converted.getImp().toArray()[i]).getIframebuster())
          success = false;
        for(int j=0;j<((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().size();j++) {
          ImageAssetFormat img = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getImg();
          if(nonNull(img)) {
            if (img.getMime() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getImg().getMimes())
              success = false;
          }
          VideoPlacement video = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getVideo();
          if(nonNull(video)) {
            if (video.getMime() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getMimes())
              success = false;
            if (video.getApi() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("api"))
              success = false;
            if (video.getCtype() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getProtocols())
              success = false;
            if (video.getDelivery() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("delivery"))
              success = false;
            if (video.getComptype() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("companiontype"))
              success = false;
          }
        }
      }
      if(request.getContext().getApp().getCat() != converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningOverrideEnabledRequestVideoApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setDisableCloning(true);
      BidRequest2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()!=converted.getCur())
        success = false;
      if(request.getSeat()!=converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getMime()!=  ((Imp) converted.getImp().toArray()[i]).getVideo().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getApi()!=  ((Imp) converted.getImp().toArray()[i]).getVideo().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getCtype()!=  ((Imp) converted.getImp().toArray()[i]).getVideo().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getDelivery()!=  ((Imp) converted.getImp().toArray()[i]).getVideo().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() != converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningOverrideEnabledRequestAudioApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setDisableCloning(true);
      BidRequest2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()!=converted.getCur())
        success = false;
      if(request.getSeat()!=converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getMime()!=  ((Imp) converted.getImp().toArray()[i]).getAudio().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getApi()!=  ((Imp) converted.getImp().toArray()[i]).getAudio().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getCtype()!=  ((Imp) converted.getImp().toArray()[i]).getAudio().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getDelivery()!=  ((Imp) converted.getImp().toArray()[i]).getAudio().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() != converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningOverrideDisabledRequestBannerSite3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_30.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setDisableCloning(false);
      BidRequest2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()==converted.getCur())
        success = false;
      if(request.getSeat()==converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() == ((Imp) converted.getImp().toArray()[i]).getIframebuster())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getMime() == ((Imp) converted.getImp().toArray()[i]).getBanner().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getApi() == ((Imp) converted.getImp().toArray()[i]).getBanner().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getCtype() == ((Imp) converted.getImp().toArray()[i]).getBanner().getExt().get("ctype"))
          success = false;
      }
      if(request.getContext().getSite().getPub().getCat() == converted.getSite().getPublisher().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getCat() == converted.getSite().getContent().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getProducer().getCat() == converted.getSite().getContent().getProducer().getCat())
        success = false;
      if(request.getContext().getSite().getCat() == converted.getSite().getCat())
        success = false;
      if(request.getContext().getSite().getSectcat() == converted.getSite().getSectioncat())
        success = false;
      if(request.getContext().getSite().getPagecat() == converted.getSite().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningOverrideDisabledRequestNativeApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setDisableCloning(false);
      BidRequest2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()==converted.getCur())
        success = false;
      if(request.getSeat()==converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() == ((Imp) converted.getImp().toArray()[i]).getIframebuster())
          success = false;
        for(int j=0;j<((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().size();j++) {
          ImageAssetFormat img = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getImg();
          if(nonNull(img)) {
            if (img.getMime() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getImg().getMimes())
              success = false;
          }
          VideoPlacement video = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getVideo();
          if(nonNull(video)) {
            if (video.getMime() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getMimes())
              success = false;
            if (video.getApi() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("api"))
              success = false;
            if (video.getCtype() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getProtocols())
              success = false;
            if (video.getDelivery() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("delivery"))
              success = false;
            if (video.getComptype() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("companiontype"))
              success = false;
          }
        }
      }
      if(request.getContext().getApp().getCat() == converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningOverrideDisabledRequestVideoApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setDisableCloning(false);
      BidRequest2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()==converted.getCur())
        success = false;
      if(request.getSeat()==converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getMime()==  ((Imp) converted.getImp().toArray()[i]).getVideo().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getApi()==  ((Imp) converted.getImp().toArray()[i]).getVideo().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getCtype()==  ((Imp) converted.getImp().toArray()[i]).getVideo().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getDelivery()==  ((Imp) converted.getImp().toArray()[i]).getVideo().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() == converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningOverrideDisabledRequestAudioApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setDisableCloning(false);
      BidRequest2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()==converted.getCur())
        success = false;
      if(request.getSeat()==converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getMime()==  ((Imp) converted.getImp().toArray()[i]).getAudio().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getApi()==  ((Imp) converted.getImp().toArray()[i]).getAudio().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getCtype()==  ((Imp) converted.getImp().toArray()[i]).getAudio().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getDelivery()==  ((Imp) converted.getImp().toArray()[i]).getAudio().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() == converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupEnabledRequestBannerSite3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_30.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      BidRequest2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()!=converted.getCur())
        success = false;
      if(request.getSeat()!=converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() != ((Imp) converted.getImp().toArray()[i]).getIframebuster())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getMime() != ((Imp) converted.getImp().toArray()[i]).getBanner().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getApi() != ((Imp) converted.getImp().toArray()[i]).getBanner().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getCtype() != ((Imp) converted.getImp().toArray()[i]).getBanner().getExt().get("ctype"))
          success = false;
      }
      if(request.getContext().getSite().getPub().getCat() != converted.getSite().getPublisher().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getCat() != converted.getSite().getContent().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getProducer().getCat() != converted.getSite().getContent().getProducer().getCat())
        success = false;
      if(request.getContext().getSite().getCat() != converted.getSite().getCat())
        success = false;
      if(request.getContext().getSite().getSectcat() != converted.getSite().getSectioncat())
        success = false;
      if(request.getContext().getSite().getPagecat() != converted.getSite().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupEnabledRequestNativeApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      BidRequest2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()!=converted.getCur())
        success = false;
      if(request.getSeat()!=converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() != ((Imp) converted.getImp().toArray()[i]).getIframebuster())
          success = false;
        for(int j=0;j<((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().size();j++) {
          ImageAssetFormat img = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getImg();
          if(nonNull(img)) {
            if (img.getMime() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getImg().getMimes())
              success = false;
          }
          VideoPlacement video = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getVideo();
          if(nonNull(video)) {
            if (video.getMime() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getMimes())
              success = false;
            if (video.getApi() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("api"))
              success = false;
            if (video.getCtype() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getProtocols())
              success = false;
            if (video.getDelivery() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("delivery"))
              success = false;
            if (video.getComptype() != ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("companiontype"))
              success = false;
          }
        }
      }
      if(request.getContext().getApp().getCat() != converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupEnabledRequestVideoApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      BidRequest2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()!=converted.getCur())
        success = false;
      if(request.getSeat()!=converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getMime()!=  ((Imp) converted.getImp().toArray()[i]).getVideo().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getApi()!=  ((Imp) converted.getImp().toArray()[i]).getVideo().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getCtype()!=  ((Imp) converted.getImp().toArray()[i]).getVideo().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getDelivery()!=  ((Imp) converted.getImp().toArray()[i]).getVideo().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() != converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupEnabledRequestAudioApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      BidRequest2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()!=converted.getCur())
        success = false;
      if(request.getSeat()!=converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getMime()!=  ((Imp) converted.getImp().toArray()[i]).getAudio().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getApi()!=  ((Imp) converted.getImp().toArray()[i]).getAudio().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getCtype()!=  ((Imp) converted.getImp().toArray()[i]).getAudio().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getDelivery()!=  ((Imp) converted.getImp().toArray()[i]).getAudio().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() != converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupDisabledRequestBannerSite3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_30.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      BidRequest2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()==converted.getCur())
        success = false;
      if(request.getSeat()==converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() == ((Imp) converted.getImp().toArray()[i]).getIframebuster())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getMime() == ((Imp) converted.getImp().toArray()[i]).getBanner().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getApi() == ((Imp) converted.getImp().toArray()[i]).getBanner().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getCtype() == ((Imp) converted.getImp().toArray()[i]).getBanner().getExt().get("ctype"))
          success = false;
      }
      if(request.getContext().getSite().getPub().getCat() == converted.getSite().getPublisher().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getCat() == converted.getSite().getContent().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getProducer().getCat() == converted.getSite().getContent().getProducer().getCat())
        success = false;
      if(request.getContext().getSite().getCat() == converted.getSite().getCat())
        success = false;
      if(request.getContext().getSite().getSectcat() == converted.getSite().getSectioncat())
        success = false;
      if(request.getContext().getSite().getPagecat() == converted.getSite().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupDisabledRequestNativeApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      BidRequest2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()==converted.getCur())
        success = false;
      if(request.getSeat()==converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() == ((Imp) converted.getImp().toArray()[i]).getIframebuster())
          success = false;
        for(int j=0;j<((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().size();j++) {
          ImageAssetFormat img = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getImg();
          if(nonNull(img)) {
            if (img.getMime() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getImg().getMimes())
              success = false;
          }
          VideoPlacement video = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getVideo();
          if(nonNull(video)) {
            if (video.getMime() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getMimes())
              success = false;
            if (video.getApi() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("api"))
              success = false;
            if (video.getCtype() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getProtocols())
              success = false;
            if (video.getDelivery() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("delivery"))
              success = false;
            if (video.getComptype() == ((net.media.openrtb25.request.Asset) ((Imp) converted.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("companiontype"))
              success = false;
          }
        }
      }
      if(request.getContext().getApp().getCat() == converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupDisabledRequestVideoApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      BidRequest2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()==converted.getCur())
        success = false;
      if(request.getSeat()==converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getMime()==  ((Imp) converted.getImp().toArray()[i]).getVideo().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getApi()==  ((Imp) converted.getImp().toArray()[i]).getVideo().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getCtype()==  ((Imp) converted.getImp().toArray()[i]).getVideo().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getDelivery()==  ((Imp) converted.getImp().toArray()[i]).getVideo().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() == converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupDisabledRequestAudioApp3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      BidRequest2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidRequest2_X.class);
      BidRequest2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_25.json").toPath()), BidRequest2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = ortb3_x.getOpenrtb().getRequest();
      if(request.getCur()==converted.getCur())
        success = false;
      if(request.getSeat()==converted.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) converted.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getMime()==  ((Imp) converted.getImp().toArray()[i]).getAudio().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getApi()==  ((Imp) converted.getImp().toArray()[i]).getAudio().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getCtype()==  ((Imp) converted.getImp().toArray()[i]).getAudio().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getDelivery()==  ((Imp) converted.getImp().toArray()[i]).getAudio().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() == converted.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == converted.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == converted.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == converted.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == converted.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == converted.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }





  @Test
  public void testCloningBackupEnabledResponseBanner3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      BidResponse2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() != ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupEnabledResponseNative3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.NATIVE);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      BidResponse2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() != ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getLink().getTrkr() != ((NativeResponse)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getLink().getClicktrackers())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().size();k++) {
            if(nonNull(((Asset)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink())) {
              if (((Asset) ((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink().getTrkr() != ((AssetResponse) ((NativeResponse) ((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getAsset().toArray()[k]).getLink().getClicktrackers())
                success = false;
            }
          }
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupEnabledResponseAudio3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.AUDIO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      BidResponse2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudio().getMime() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupEnabledResponseVideo3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.VIDEO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      BidResponse2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getVideo().getMime() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupDisabledResponseBanner3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      BidResponse2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() == ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          //if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
          //  success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupDisabledResponseNative3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.NATIVE);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      BidResponse2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() == ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getLink().getTrkr() == ((NativeResponse)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getLink().getClicktrackers())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().size();k++) {
            if (((Asset)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink().getTrkr() == ((AssetResponse)((NativeResponse) ((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getAsset().toArray()[k]).getLink().getClicktrackers())
              success = false;
          }
          //if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
          // success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupDisabledResponseAudio3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.AUDIO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      BidResponse2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudio().getMime() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupDisabledResponseVideo3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.VIDEO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      BidResponse2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getVideo().getMime() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }



  @Test
  public void testCloningEnabledResponseBanner3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      BidResponse2_X converted = openRtbConverter.convert(config, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() != ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningEnabledResponseNative3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.NATIVE);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      BidResponse2_X converted = openRtbConverter.convert(config, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() != ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getLink().getTrkr() != ((NativeResponse)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getLink().getClicktrackers())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().size();k++) {
            if(nonNull(((Asset)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink())) {
              if (((Asset) ((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink().getTrkr() != ((AssetResponse) ((NativeResponse) ((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getAsset().toArray()[k]).getLink().getClicktrackers())
                success = false;
            }
          }
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningEnabledResponseAudio3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.AUDIO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      BidResponse2_X converted = openRtbConverter.convert(config, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudio().getMime() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningEnabledResponseVideo3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.VIDEO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      BidResponse2_X converted = openRtbConverter.convert(config, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getVideo().getMime() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningDisabledResponseBanner3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidResponse2_X converted = openRtbConverter.convert(config, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() == ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          //if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
          //  success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningDisabledResponseNative3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.NATIVE);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      BidResponse2_X converted = openRtbConverter.convert(config, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() == ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getLink().getTrkr() == ((NativeResponse)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getLink().getClicktrackers())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().size();k++) {
            if(nonNull(((Asset)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink())) {
              if (((Asset) ((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink().getTrkr() == ((AssetResponse) ((NativeResponse) ((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getAsset().toArray()[k]).getLink().getClicktrackers())
                success = false;
            }
          }
          //if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
          // success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningDisabledResponseAudio3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.AUDIO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      BidResponse2_X converted = openRtbConverter.convert(config, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudio().getMime() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningDisabledResponseVideo3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.VIDEO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidResponse2_X converted = openRtbConverter.convert(config, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getVideo().getMime() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testInitCloningEnabledResponseBanner3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      BidResponse2_X converted = openRtbConverter.convert(new Config(), ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() != ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testInitCloningEnabledResponseNative3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.NATIVE);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      BidResponse2_X converted = openRtbConverter.convert(new Config(), ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() != ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getLink().getTrkr() != ((NativeResponse)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getLink().getClicktrackers())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().size();k++) {
            if(nonNull(((Asset)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink())) {
              if (((Asset) ((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink().getTrkr() != ((AssetResponse) ((NativeResponse) ((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getAsset().toArray()[k]).getLink().getClicktrackers())
                success = false;
            }
          }
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testInitCloningEnabledResponseAudio3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.AUDIO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      BidResponse2_X converted = openRtbConverter.convert(new Config(), ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudio().getMime() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testInitCloningEnabledResponseVideo3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.VIDEO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      BidResponse2_X converted = openRtbConverter.convert(new Config(), ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getVideo().getMime() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testInitCloningDisabledResponseBanner3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidResponse2_X converted = openRtbConverter.convert(new Config(), ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() == ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          //if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
          //  success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testInitCloningDisabledResponseNative3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.NATIVE);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      BidResponse2_X converted = openRtbConverter.convert(new Config(), ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() == ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getLink().getTrkr() == ((NativeResponse)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getLink().getClicktrackers())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().size();k++) {
            if (((Asset)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink().getTrkr() == ((AssetResponse)((NativeResponse) ((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getAsset().toArray()[k]).getLink().getClicktrackers())
              success = false;
          }
          //if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
          // success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testInitCloningDisabledResponseAudio3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.AUDIO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      BidResponse2_X converted = openRtbConverter.convert(new Config(), ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudio().getMime() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testInitCloningDisabledResponseVideo3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.VIDEO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);

      BidResponse2_X converted = openRtbConverter.convert(new Config(), ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getVideo().getMime() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testOverrideCloningEnabledResponseBanner3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      config.setDisableCloning(true);
      BidResponse2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() != ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testOverrideCloningEnabledResponseNative3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.NATIVE);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      config.setDisableCloning(true);
      BidResponse2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() != ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getLink().getTrkr() != ((NativeResponse)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getLink().getClicktrackers())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().size();k++) {
            if(nonNull(((Asset)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink())) {
              if (((Asset) ((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink().getTrkr() != ((AssetResponse) ((NativeResponse) ((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getAsset().toArray()[k]).getLink().getClicktrackers())
                success = false;
            }
          }
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testOverrideCloningEnabledResponseAudio3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.AUDIO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      config.setDisableCloning(true);
      BidResponse2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudio().getMime() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testOverrideCloningEnabledResponseVideo3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.VIDEO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      config.setDisableCloning(true);
      BidResponse2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getVideo().getMime() !=((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testOverrideCloningDisabledResponseBanner3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      config.setDisableCloning(false);
      BidResponse2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() == ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          //if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
          //  success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testOverrideCloningDisabledResponseNative3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.NATIVE);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      config.setDisableCloning(false);
      BidResponse2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() == ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getLink().getTrkr() == ((NativeResponse)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getLink().getClicktrackers())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().size();k++) {
            if(nonNull(((Asset)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink())) {
              if (((Asset) ((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink().getTrkr() == ((AssetResponse) ((NativeResponse) ((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getAsset().toArray()[k]).getLink().getClicktrackers())
                success = false;
            }
          }
          //if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
          // success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testOverrideCloningDisabledResponseAudio3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.AUDIO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      config.setDisableCloning(false);
      BidResponse2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudio().getMime() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testOverrideCloningDisabledResponseVideo3to2() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.VIDEO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_30_3to2.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      OpenRTBWrapper3_X ortb3_x = JacksonObjectMapper.getMapper().readValue(jsonData, OpenRTBWrapper3_X.class);
      Config overrideConfig = new Config();
      config.setDisableCloning(false);
      BidResponse2_X converted = openRtbConverter.convert(overrideConfig, ortb3_x, OpenRTBWrapper3_X.class, BidResponse2_X.class);
      BidResponse2_X target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_25_3to2.json").toPath()), BidResponse2_X.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = ortb3_x.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getVideo().getMime() ==((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }









  @Test
  public void testCloningEnabledRequestBannerSite2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(config, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_30.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()!=bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()!=bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getIframebuster())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getMime() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getApi() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getCtype() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getExt().get("ctype"))
          success = false;

      }
      if(request.getContext().getSite().getPub().getCat() != bidRequest2_x.getSite().getPublisher().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getCat() != bidRequest2_x.getSite().getContent().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getProducer().getCat() != bidRequest2_x.getSite().getContent().getProducer().getCat())
        success = false;
      if(request.getContext().getSite().getCat() != bidRequest2_x.getSite().getCat())
        success = false;
      if(request.getContext().getSite().getSectcat() != bidRequest2_x.getSite().getSectioncat())
        success = false;
      if(request.getContext().getSite().getPagecat() != bidRequest2_x.getSite().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningEnabledRequestNativeApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(config, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()!=bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()!=bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getIframebuster())
          success = false;
      }
      if(request.getContext().getApp().getCat() != bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningEnabledRequestVideoApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(config, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()!=bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()!=bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getMime()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getApi()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getCtype()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getDelivery()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() != bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningEnabledRequestAudioApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(config, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()!=bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()!=bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getMime()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getApi()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getCtype()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getDelivery()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() != bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningDisabledRequestBannerSite2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(config, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_30.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()==bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()==bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getIframebuster())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getMime() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getApi() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getCtype() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getExt().get("ctype"))
          success = false;
      }
      if(request.getContext().getSite().getPub().getCat() == bidRequest2_x.getSite().getPublisher().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getCat() == bidRequest2_x.getSite().getContent().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getProducer().getCat() == bidRequest2_x.getSite().getContent().getProducer().getCat())
        success = false;
      if(request.getContext().getSite().getCat() == bidRequest2_x.getSite().getCat())
        success = false;
      if(request.getContext().getSite().getSectcat() == bidRequest2_x.getSite().getSectioncat())
        success = false;
      if(request.getContext().getSite().getPagecat() == bidRequest2_x.getSite().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningDisabledRequestNativeApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(config, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()==bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()==bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getIframebuster())
          success = false;
        for(int j=0;j<((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().size();j++) {
          ImageAssetFormat img = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getImg();
          if(nonNull(img)) {
            if (img.getMime() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getImg().getMimes())
              success = false;
          }
          VideoPlacement video = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getVideo();
          if(nonNull(video)) {
            if (video.getMime() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getMimes())
              success = false;
            if (video.getApi() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("api"))
              success = false;
            if (video.getCtype() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getProtocols())
              success = false;
            if (video.getDelivery() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("delivery"))
              success = false;
            if (video.getComptype() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("companiontype"))
              success = false;
          }
        }
      }
      if(request.getContext().getApp().getCat() == bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningDisabledRequestVideoApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(config, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()==bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()==bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getMime()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getApi()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getCtype()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getDelivery()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() == bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningDisabledRequestAudioApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(config, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()==bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()==bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getMime()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getApi()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getCtype()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getDelivery()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() == bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningInitEnabledRequestBannerSite2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(new Config(), bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_30.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()!=bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()!=bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getIframebuster())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getMime() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getApi() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getCtype() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getExt().get("ctype"))
          success = false;
      }
      if(request.getContext().getSite().getPub().getCat() != bidRequest2_x.getSite().getPublisher().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getCat() != bidRequest2_x.getSite().getContent().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getProducer().getCat() != bidRequest2_x.getSite().getContent().getProducer().getCat())
        success = false;
      if(request.getContext().getSite().getCat() != bidRequest2_x.getSite().getCat())
        success = false;
      if(request.getContext().getSite().getSectcat() != bidRequest2_x.getSite().getSectioncat())
        success = false;
      if(request.getContext().getSite().getPagecat() != bidRequest2_x.getSite().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningInitEnabledRequestNativeApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(new Config(), bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()!=bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()!=bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getIframebuster())
          success = false;
      }
      if(request.getContext().getApp().getCat() != bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningInitEnabledRequestVideoApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(new Config(), bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()!=bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()!=bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getMime()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getApi()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getCtype()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getDelivery()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() != bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningInitEnabledRequestAudioApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(new Config(), bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()!=bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()!=bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getMime()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getApi()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getCtype()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getDelivery()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() != bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningInitDisabledRequestBannerSite2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(new Config(), bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_30.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()==bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()==bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getIframebuster())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getMime() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getApi() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getCtype() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getExt().get("ctype"))
          success = false;
      }
      if(request.getContext().getSite().getPub().getCat() == bidRequest2_x.getSite().getPublisher().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getCat() == bidRequest2_x.getSite().getContent().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getProducer().getCat() == bidRequest2_x.getSite().getContent().getProducer().getCat())
        success = false;
      if(request.getContext().getSite().getCat() == bidRequest2_x.getSite().getCat())
        success = false;
      if(request.getContext().getSite().getSectcat() == bidRequest2_x.getSite().getSectioncat())
        success = false;
      if(request.getContext().getSite().getPagecat() == bidRequest2_x.getSite().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningInitDisabledRequestNativeApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(new Config(), bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()==bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()==bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getIframebuster())
          success = false;
        for(int j=0;j<((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().size();j++) {
          ImageAssetFormat img = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getImg();
          if(nonNull(img)) {
            if (img.getMime() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getImg().getMimes())
              success = false;
          }
          VideoPlacement video = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getVideo();
          if(nonNull(video)) {
            if (video.getMime() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getMimes())
              success = false;
            if (video.getApi() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("api"))
              success = false;
            if (video.getCtype() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getProtocols())
              success = false;
            if (video.getDelivery() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("delivery"))
              success = false;
            if (video.getComptype() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("companiontype"))
              success = false;
          }
        }
      }
      if(request.getContext().getApp().getCat() == bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningInitDisabledRequestVideoApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(new Config(), bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()==bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()==bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getMime()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getApi()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getCtype()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getDelivery()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() == bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningInitDisabledRequestAudioApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(new Config(), bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()==bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()==bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getMime()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getApi()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getCtype()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getDelivery()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() == bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningOverrideEnabledRequestBannerSite2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setDisableCloning(true);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_30.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()!=bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()!=bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getIframebuster())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getMime() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getApi() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getCtype() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getExt().get("ctype"))
          success = false;
      }
      if(request.getContext().getSite().getPub().getCat() != bidRequest2_x.getSite().getPublisher().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getCat() != bidRequest2_x.getSite().getContent().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getProducer().getCat() != bidRequest2_x.getSite().getContent().getProducer().getCat())
        success = false;
      if(request.getContext().getSite().getCat() != bidRequest2_x.getSite().getCat())
        success = false;
      if(request.getContext().getSite().getSectcat() != bidRequest2_x.getSite().getSectioncat())
        success = false;
      if(request.getContext().getSite().getPagecat() != bidRequest2_x.getSite().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningOverrideEnabledRequestNativeApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setDisableCloning(true);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()!=bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()!=bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getIframebuster())
          success = false;
      }
      if(request.getContext().getApp().getCat() != bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningOverrideEnabledRequestVideoApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setDisableCloning(true);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()!=bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()!=bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getMime()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getApi()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getCtype()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getDelivery()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() != bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningOverrideEnabledRequestAudioApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setDisableCloning(true);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()!=bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()!=bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getMime()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getApi()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getCtype()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getDelivery()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() != bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningOverrideDisabledRequestBannerSite2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setDisableCloning(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_30.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()==bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()==bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getIframebuster())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getMime() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getApi() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getCtype() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getExt().get("ctype"))
          success = false;
      }
      if(request.getContext().getSite().getPub().getCat() == bidRequest2_x.getSite().getPublisher().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getCat() == bidRequest2_x.getSite().getContent().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getProducer().getCat() == bidRequest2_x.getSite().getContent().getProducer().getCat())
        success = false;
      if(request.getContext().getSite().getCat() == bidRequest2_x.getSite().getCat())
        success = false;
      if(request.getContext().getSite().getSectcat() == bidRequest2_x.getSite().getSectioncat())
        success = false;
      if(request.getContext().getSite().getPagecat() == bidRequest2_x.getSite().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningOverrideDisabledRequestNativeApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setDisableCloning(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()==bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()==bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getIframebuster())
          success = false;
        for(int j=0;j<((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().size();j++) {
          ImageAssetFormat img = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getImg();
          if(nonNull(img)) {
            if (img.getMime() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getImg().getMimes())
              success = false;
          }
          VideoPlacement video = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getVideo();
          if(nonNull(video)) {
            if (video.getMime() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getMimes())
              success = false;
            if (video.getApi() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("api"))
              success = false;
            if (video.getCtype() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getProtocols())
              success = false;
            if (video.getDelivery() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("delivery"))
              success = false;
            if (video.getComptype() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("companiontype"))
              success = false;
          }
        }
      }
      if(request.getContext().getApp().getCat() == bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningOverrideDisabledRequestVideoApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setDisableCloning(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()==bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()==bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getMime()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getApi()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getCtype()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getDelivery()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() == bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningOverrideDisabledRequestAudioApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setDisableCloning(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()==bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()==bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getMime()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getApi()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getCtype()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getDelivery()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() == bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupEnabledRequestBannerSite2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_30.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()!=bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()!=bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getIframebuster())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getMime() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getApi() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getCtype() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getExt().get("ctype"))
          success = false;
      }
      if(request.getContext().getSite().getPub().getCat() != bidRequest2_x.getSite().getPublisher().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getCat() != bidRequest2_x.getSite().getContent().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getProducer().getCat() != bidRequest2_x.getSite().getContent().getProducer().getCat())
        success = false;
      if(request.getContext().getSite().getCat() != bidRequest2_x.getSite().getCat())
        success = false;
      if(request.getContext().getSite().getSectcat() != bidRequest2_x.getSite().getSectioncat())
        success = false;
      if(request.getContext().getSite().getPagecat() != bidRequest2_x.getSite().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupEnabledRequestNativeApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()!=bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()!=bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() != ((Imp) bidRequest2_x.getImp().toArray()[i]).getIframebuster())
          success = false;
      }
      if(request.getContext().getApp().getCat() != bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupEnabledRequestVideoApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()!=bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()!=bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getMime()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getApi()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getCtype()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getDelivery()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() != bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupEnabledRequestAudioApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()!=bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()!=bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() != ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getMime()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getApi()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getCtype()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getDelivery()!=  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() != bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() != bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() != bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() != bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() != bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() != bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupDisabledRequestBannerSite2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/BANNER_SITE_30.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()==bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()==bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getIframebuster())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getMime() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getApi() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getCtype() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getBanner().getExt().get("ctype"))
          success = false;
      }
      if(request.getContext().getSite().getPub().getCat() == bidRequest2_x.getSite().getPublisher().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getCat() == bidRequest2_x.getSite().getContent().getCat())
        success = false;
      if(request.getContext().getSite().getContent().getProducer().getCat() == bidRequest2_x.getSite().getContent().getProducer().getCat())
        success = false;
      if(request.getContext().getSite().getCat() == bidRequest2_x.getSite().getCat())
        success = false;
      if(request.getContext().getSite().getSectcat() == bidRequest2_x.getSite().getSectioncat())
        success = false;
      if(request.getContext().getSite().getPagecat() == bidRequest2_x.getSite().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupDisabledRequestNativeApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/NATIVE_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()==bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()==bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getIfrbust() == ((Imp) bidRequest2_x.getImp().toArray()[i]).getIframebuster())
          success = false;
        for(int j=0;j<((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().size();j++) {
          ImageAssetFormat img = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getImg();
          if(nonNull(img)) {
            if (img.getMime() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getImg().getMimes())
              success = false;
          }
          VideoPlacement video = ((AssetFormat) ((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getDisplay().getNativefmt().getAsset().toArray()[j]).getVideo();
          if(nonNull(video)) {
            if (video.getMime() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getMimes())
              success = false;
            if (video.getApi() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("api"))
              success = false;
            if (video.getCtype() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getProtocols())
              success = false;
            if (video.getDelivery() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("delivery"))
              success = false;
            if (video.getComptype() == ((net.media.openrtb25.request.Asset) ((Imp) bidRequest2_x.getImp().toArray()[i]).getNat().getNativeRequestBody().getAssets().toArray()[j]).getVideo().getExt().get("companiontype"))
              success = false;
          }
        }
      }
      if(request.getContext().getApp().getCat() == bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupDisabledRequestVideoApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/VAST_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()==bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()==bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getMime()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getApi()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getCtype()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getVideo().getDelivery()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getVideo().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() == bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupDisabledRequestAudioApp2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeRequestAsString(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_25.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidRequest2_X bidRequest2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidRequest2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidRequest2_x, BidRequest2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/request/AUDIO_APP_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Request request = converted.getOpenrtb().getRequest();
      if(request.getCur()==bidRequest2_x.getCur())
        success = false;
      if(request.getSeat()==bidRequest2_x.getWseat())
        success = false;
      for(int i=0;i<request.getItem().size();i++) {
        for(int j=0;j<((Item)request.getItem().toArray()[i]).getDeal().size();j++) {
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWseat() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWseat())
            success = false;
          if (((Deal) ((Item) request.getItem().toArray()[i]).getDeal().toArray()[j]).getWadomain() == ((net.media.openrtb25.request.Deal) ((Imp) bidRequest2_x.getImp().toArray()[i]).getPmp().getDeals().toArray()[j]).getWadomain())
            success = false;
        }
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getMime()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getMimes())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getApi()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getApi())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getCtype()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getProtocols())
          success = false;
        if(((Item) request.getItem().toArray()[i]).getSpec().getPlacement().getAudio().getDelivery()==  ((Imp) bidRequest2_x.getImp().toArray()[i]).getAudio().getDelivery())
          success = false;
      }
      if(request.getContext().getApp().getCat() == bidRequest2_x.getApp().getCat())
        success = false;
      if(request.getContext().getApp().getSectcat() == bidRequest2_x.getApp().getSectioncat())
        success = false;
      if(request.getContext().getApp().getPagecat() == bidRequest2_x.getApp().getPagecat())
        success = false;
      if(request.getContext().getRestrictions().getBcat() == bidRequest2_x.getBcat())
        success = false;
      if(request.getContext().getRestrictions().getBadv() == bidRequest2_x.getBadv())
        success = false;
      if(request.getContext().getRestrictions().getBapp() == bidRequest2_x.getBapp())
        success = false;
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }





  @Test
  public void testCloningBackupEnabledResponseBanner2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() != ((List<LinkedHashMap>)((net.media.openrtb25.response.Bid) ((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).get(k).get("api"))
              success = false;
          }*/
          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupEnabledResponseNative2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.NATIVE);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() != ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getLink().getTrkr() != ((NativeResponse)((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getLink().getClicktrackers())
            success = false;*/
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().size();k++) {
            if (((Asset)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink().getTrkr() != ((AssetResponse)((NativeResponse) ((net.media.openrtb25.response.Bid) ((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getAsset().toArray()[k]).getLink().getClicktrackers())
              success = false;
          }*/
          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupEnabledResponseAudio2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.AUDIO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudio().getMime() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupEnabledResponseVideo2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.VIDEO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getVideo().getMime() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupDisabledResponseBanner2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() == ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          //if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
          //  success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupDisabledResponseNative2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.NATIVE);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() == ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getLink().getTrkr() == ((NativeResponse)((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getLink().getClicktrackers())
            success = false;*/
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().size();k++) {
            if (((Asset)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink().getTrkr() == ((AssetResponse)((NativeResponse) ((net.media.openrtb25.response.Bid) ((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getAsset().toArray()[k]).getLink().getClicktrackers())
              success = false;
          }*/
          //if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
          // success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupDisabledResponseAudio2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.AUDIO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudio().getMime() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningBackupDisabledResponseVideo2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.VIDEO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      Config overrideConfig = new Config();
      overrideConfig.setValidate(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getVideo().getMime() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }



  @Test
  public void testCloningEnabledResponseBanner2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(config, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() != ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningEnabledResponseNative2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.NATIVE);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(config, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() != ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getLink().getTrkr() != ((NativeResponse)((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getLink().getClicktrackers())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().size();k++) {
            if (((Asset)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink().getTrkr() != ((AssetResponse)((NativeResponse) ((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getAsset().toArray()[k]).getLink().getClicktrackers())
              success = false;
          }*/
          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningEnabledResponseAudio2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.AUDIO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(config, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudio().getMime() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningEnabledResponseVideo2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.VIDEO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(config, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getVideo().getMime() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningDisabledResponseBanner2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(config, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() == ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          //if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
          //  success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningDisabledResponseNative2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.NATIVE);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(config, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() == ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getLink().getTrkr() == ((NativeResponse)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getLink().getClicktrackers())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().size();k++) {
            if (((Asset)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink().getTrkr() == ((AssetResponse)((NativeResponse) ((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getAsset().toArray()[k]).getLink().getClicktrackers())
              success = false;
          }*/
          //if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
          // success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningDisabledResponseAudio2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.AUDIO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(config, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudio().getMime() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testCloningDisabledResponseVideo2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.VIDEO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(new Config());
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(config, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getVideo().getMime() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testInitCloningEnabledResponseBanner2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(new Config(), bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() != ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testInitCloningEnabledResponseNative2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.NATIVE);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(new Config(), bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() != ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getLink().getTrkr() != ((NativeResponse)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getLink().getClicktrackers())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().size();k++) {
            if (((Asset)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink().getTrkr() != ((AssetResponse)((NativeResponse) ((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getAsset().toArray()[k]).getLink().getClicktrackers())
              success = false;
          }
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testInitCloningEnabledResponseAudio2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.AUDIO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(new Config(), bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudio().getMime() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testInitCloningEnabledResponseVideo2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.VIDEO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(new Config(), bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getVideo().getMime() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;
/*
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testInitCloningDisabledResponseBanner2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(new Config(), bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() == ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          //if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
          //  success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testInitCloningDisabledResponseNative2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.NATIVE);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(new Config(), bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() == ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getLink().getTrkr() == ((NativeResponse)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getLink().getClicktrackers())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().size();k++) {
            if (((Asset)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink().getTrkr() == ((AssetResponse)((NativeResponse) ((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getAsset().toArray()[k]).getLink().getClicktrackers())
              success = false;
          }*/
          //if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
          // success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testInitCloningDisabledResponseAudio2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.AUDIO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(new Config(), bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudio().getMime() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testInitCloningDisabledResponseVideo2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.VIDEO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);

      OpenRTBWrapper3_X converted = openRtbConverter.convert(new Config(), bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getVideo().getMime() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testOverrideCloningEnabledResponseBanner2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      Config overrideConfig = new Config();
      config.setDisableCloning(true);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() != ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testOverrideCloningEnabledResponseNative2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.NATIVE);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      Config overrideConfig = new Config();
      config.setDisableCloning(true);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() != ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getLink().getTrkr() != ((NativeResponse)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getLink().getClicktrackers())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().size();k++) {
            if (((Asset)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink().getTrkr() != ((AssetResponse)((NativeResponse) ((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getAsset().toArray()[k]).getLink().getClicktrackers())
              success = false;
          }
          if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testOverrideCloningEnabledResponseAudio2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.AUDIO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      Config overrideConfig = new Config();
      config.setDisableCloning(true);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudio().getMime() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testOverrideCloningEnabledResponseVideo2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(false);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.VIDEO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      Config overrideConfig = new Config();
      config.setDisableCloning(true);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getVideo().getMime() !=((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()!=((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testOverrideCloningDisabledResponseBanner2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      Config overrideConfig = new Config();
      config.setDisableCloning(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_BANNER_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() == ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          //if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
          //  success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testOverrideCloningDisabledResponseNative2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.NATIVE);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      Config overrideConfig = new Config();
      config.setDisableCloning(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_NATIVE_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;
          /*for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().size();k++) {
            if (((Event)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().getEvent().toArray()[k]).getApi() == ((Event)((Collection<Event>)((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("event")).toArray()[k]).getApi())
              success = false;
          }*/
          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getLink().getTrkr() == ((NativeResponse)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getLink().getClicktrackers())
            success = false;
          for(int k=0;k<((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().size();k++) {
            if (((Asset)((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getDisplay().get_native().getAsset().toArray()[k]).getLink().getTrkr() == ((AssetResponse)((NativeResponse) ((net.media.openrtb25.response.Bid) ((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdm()).getNativeResponseBody().getAsset().toArray()[k]).getLink().getClicktrackers())
              success = false;
          }*/
          //if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
          // success = false;
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testOverrideCloningDisabledResponseAudio2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.AUDIO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      Config overrideConfig = new Config();
      config.setDisableCloning(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_AUDIO_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudio().getMime() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }

  @Test
  public void testOverrideCloningDisabledResponseVideo2to3() {
    Boolean success = true;
    try {
      Config config = new Config();
      config.setDisableCloning(true);
      config.setNativeResponseAsString(false);
      Map<String, AdType> adTypeMap = new HashMap<>();
      adTypeMap.put("1", AdType.VIDEO);
      config.setAdTypeMapping(adTypeMap);
      OpenRtbConverter openRtbConverter = new OpenRtbConverter(config);
      ClassLoader classLoader = getClass().getClassLoader();
      File file =
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_25_2to3.json");
      byte[] jsonData = Files.readAllBytes(file.toPath());
      BidResponse2_X bidResponse2_x = JacksonObjectMapper.getMapper().readValue(jsonData, BidResponse2_X.class);
      Config overrideConfig = new Config();
      config.setDisableCloning(false);
      OpenRTBWrapper3_X converted = openRtbConverter.convert(overrideConfig, bidResponse2_x, BidResponse2_X.class, OpenRTBWrapper3_X.class);
      Object target = JacksonObjectMapper.getMapper().readValue(Files.readAllBytes(
        new File(classLoader.getResource("master").getPath() + "/response/RESPONSE_VAST_SITE_30_2to3.json").toPath()), Object.class);
      JSONAssert.assertEquals(
        JacksonObjectMapper.getMapper().writeValueAsString(target),
        JacksonObjectMapper.getMapper().writeValueAsString(converted),
        true);
      Response response = converted.getOpenrtb().getResponse();

      for(int i=0;i<response.getSeatbid().size();i++) {
        for(int j=0;j<((Seatbid)response.getSeatbid().toArray()[i]).getBid().size();j++) {
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAdomain() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAdomain())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getCat() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getCat())
            success = false;
          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAttr() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getAttr())
            success = false;

          if (((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getVideo().getMime() ==((net.media.openrtb25.response.Bid)((SeatBid) bidResponse2_x.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("mime"))
            success = false;

          /*if(((Bid) ((Seatbid) response.getSeatbid().toArray()[i]).getBid().toArray()[j]).getMedia().getAd().getAudit().getFeedback()==((Audit)((net.media.openrtb25.response.Bid)((SeatBid) converted.getSeatbid().toArray()[i]).getBid().toArray()[j]).getExt().get("audit")).getFeedback())
            success = false;*/
        }
      }
    } catch (Exception | AssertionError e) {
      success = false;
    }
    Assert.assertTrue(success);
  }
}
