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

package net.media.api.servlets;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.inject.Inject;
import net.media.config.Config;
import net.media.driver.OpenRtbConverter;
import net.media.enums.AdType;
import net.media.enums.OpenRtbVersion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.BidRequest2_X;
import net.media.openrtb3.OpenRTBWrapper3_X;
import net.media.utils.JacksonObjectMapperUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.util.TriConsumer;
import org.slf4j.Logger;

import javax.naming.ConfigurationException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static java.util.Objects.isNull;

public class ConverterServlet extends HttpServlet {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(ConverterServlet.class);
  private static final String REQUEST = "request";
  private static final String RESPONSE = "response";
  private static final String TWOXTOTHREEX = "2xTo3x";
  private static final String THREEXTOTHREEX = "3xTo2x";
  private static final String TYPE = "type";
  private static final String CONVERSIONTYPE = "conversiontype";
  private static final ObjectMapper mapper = JacksonObjectMapperUtils.getMapper();
  private static final JavaType javaTypeForAdTypeMapping = mapper.getTypeFactory()
    .constructMapType(Map.class, String.class, AdType.class);
  @Inject private OpenRtbConverter openRtbConverter;
  private Table<String, String, TriConsumer<HttpServletRequest, HttpServletResponse, Config>> queryActionMap;
  private TriConsumer<HttpServletRequest, HttpServletResponse, Config> get2xto3xRequest =
      (request, response, config) -> {
        try {
          mapper.writeValue(
            response.getWriter(),
            openRtbConverter
              .convert(
                config,
                IOUtils.toString(request.getInputStream(), "UTF-8"),
                BidRequest2_X.class,
                OpenRTBWrapper3_X.class));
        } catch (IOException | ConfigurationException | OpenRtbConverterException e) {
          log.error("Error while sending 2xto3x request ", e);
          response.setStatus(500);
        }
      };
  private TriConsumer<HttpServletRequest, HttpServletResponse, Config> get3xto2xRequest =
      (request, response, config) -> {
        try {
          mapper.writeValue(
            response.getWriter(),
            openRtbConverter
              .convert(
                config,
                IOUtils.toString(request.getInputStream(), "UTF-8"),
                OpenRTBWrapper3_X.class,
                BidRequest2_X.class));
        } catch (IOException | ConfigurationException | OpenRtbConverterException e) {
          log.error("Error while sending 2xto3x request ", e);
          response.setStatus(500);
        }
      };
  private TriConsumer<HttpServletRequest, HttpServletResponse, Config> get2xto3xResponse =
      (request, response, config) -> {
        try {
          mapper.writeValue(
            response.getWriter(),
            openRtbConverter
              .convert(
                config,
                IOUtils.toString(request.getInputStream(), "UTF-8"),
                BidRequest2_X.class,
                OpenRTBWrapper3_X.class));
        } catch (IOException | ConfigurationException | OpenRtbConverterException e) {
          log.error("Error while sending 2xto3x request ", e);
          response.setStatus(500);
        }
      };
  private TriConsumer<HttpServletRequest, HttpServletResponse, Config> get3xto2xResponse =
      (request, response, config) -> {
        try {
          mapper.writeValue(
            response.getWriter(),
            openRtbConverter
              .convert(
                config,
                IOUtils.toString(request.getInputStream(), "UTF-8"),
                BidRequest2_X.class,
                OpenRTBWrapper3_X.class));
        } catch (IOException | ConfigurationException | OpenRtbConverterException e) {
          log.error("Error while sending 2xto3x request ", e);
          response.setStatus(500);
        }
      };
  private TriConsumer<HttpServletRequest, HttpServletResponse, Config> illegalAction =
      (request, response, config) -> {
        try {
          response.getWriter().write("No such query exists");
        } catch (IOException e) {
          log.error("Error while sending cacheValues ", e);
        }
      };

  private static String getParamValue(String key, Map<String, String[]> queryParam) {
    String[] values = queryParam.get(key);
    if (isNull(values) || values.length == 0) {
      return null;
    }
    return values[0];
  }

  @Override
  public void init(ServletConfig sce) {
    Config config = new Config();
    openRtbConverter = new OpenRtbConverter(config);
    queryActionMap = HashBasedTable.create();
    queryActionMap.put(REQUEST, TWOXTOTHREEX, get2xto3xRequest);
    queryActionMap.put(REQUEST, THREEXTOTHREEX, get3xto2xRequest);
    queryActionMap.put(RESPONSE, TWOXTOTHREEX, get2xto3xResponse);
    queryActionMap.put(RESPONSE, THREEXTOTHREEX, get3xto2xResponse);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Map<String, String[]> queryMap = request.getParameterMap();
    Config config = null;
    TriConsumer<HttpServletRequest, HttpServletResponse, Config> queryConsumer = getQueryValues(queryMap);
    if(!queryConsumer.equals(illegalAction))
      config = getConfig.apply(request);
    queryConsumer.accept(request, response, config);
  }

  private Function<HttpServletRequest, Config> getConfig = request -> {
    Map<String, String[]> queryMap = request.getParameterMap();
    String nativeRequestAsString = getParamValue("nativeRequestAsString", queryMap);
    String nativeResponseAsString = getParamValue("nativeResponseAsString", queryMap);
    String adTypeMapping = getParamValue("adTypeMapping", queryMap);
    String disableCloning = getParamValue("disableCloning", queryMap);
    String validate = getParamValue("validate", queryMap);
    String openRtbVersion2_XVersion = getParamValue("openRtbVersion2_XVersion", queryMap);
    Config config = new Config();
    try {
      if(nativeRequestAsString != null)
        config.setNativeRequestAsString(Boolean.getBoolean(nativeRequestAsString));
      if(nativeResponseAsString != null)
        config.setNativeResponseAsString(Boolean.getBoolean(nativeResponseAsString));
      if(adTypeMapping != null)
        config.setAdTypeMapping(mapper.readValue(adTypeMapping, javaTypeForAdTypeMapping));
      if(disableCloning != null)
        config.setDisableCloning(Boolean.getBoolean(disableCloning));
      if(validate != null)
        config.setValidate(Boolean.getBoolean(validate));
      if(openRtbVersion2_XVersion != null)
        config.setOpenRtbVersion2_XVersion(OpenRtbVersion.valueOf(openRtbVersion2_XVersion));
    } catch (Exception e) {
      log.error("Could not convert config params ", e);
    }
    return config;
  };

  private TriConsumer<HttpServletRequest, HttpServletResponse, Config> getQueryValues(
      Map<String, String[]> query) {
    String type = getParamValue(TYPE, query);
    String conversionType = getParamValue(CONVERSIONTYPE, query);
    if (Objects.isNull(type)
        || Objects.isNull(conversionType)
        || !queryActionMap.contains(type, conversionType)) return illegalAction;
    return queryActionMap.get(type, conversionType);
  }
}
