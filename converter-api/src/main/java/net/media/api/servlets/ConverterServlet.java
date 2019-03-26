package net.media.api.servlets;


import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.media.OpenRtbConverter;
import net.media.OpenRtbConverterException;
import net.media.api.models.Request2xPayload;
import net.media.api.models.RequestResponse3xPayload;
import net.media.api.models.Response2xPayload;
import net.media.config.Config;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.response.BidResponse;
import net.media.openrtb3.OpenRTB;
import net.media.utils.JacksonObjectMapper;

import javax.naming.ConfigurationException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

import static java.util.Objects.isNull;

@Slf4j
public class ConverterServlet extends HttpServlet {

  @Inject
  private OpenRtbConverter openRtbConverter;
  private Table<String, String, BiConsumer<HttpServletRequest, HttpServletResponse>> queryActionMap;

  private static final String REQUEST = "request";
  private static final String RESPONSE = "response";
  private static final String TWOXTOTHREEX = "2xTo3x";
  private static final String THREEXTOTHREEX = "3xTo2x";
  private static final String TYPE = "type";
  private static final String CONVERSIONTYPE = "conversiontype";

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

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
    ServletException, IOException {
    Map<String, String[]> queryMap = request.getParameterMap();
    BiConsumer<HttpServletRequest, HttpServletResponse> queryConsumer = getQueryValues(queryMap);
    queryConsumer.accept(request, response);
  }

  private static String getParamValue(String key, Map<String, String[]> queryParam) {
    String[] values = queryParam.get(key);
    if (isNull(values) || values.length == 0) {
      return null;
    }
    return values[0];
  }

  private BiConsumer<HttpServletRequest, HttpServletResponse> getQueryValues(Map<String, String[]> query) {
    String type = getParamValue(TYPE, query);
    String conversionType = getParamValue(CONVERSIONTYPE, query);
    if(Objects.isNull(type) || Objects.isNull(conversionType) || !queryActionMap.contains(type, conversionType))
      return illegalAction;
    return queryActionMap.get(type, conversionType);
  }

  private BiConsumer<HttpServletRequest, HttpServletResponse> get2xto3xRequest = (request, response) -> {
    try {
      Request2xPayload request2xPayload = JacksonObjectMapper.getMapper().readValue(request.getInputStream(), Request2xPayload.class);
      JacksonObjectMapper.getMapper().writerWithDefaultPrettyPrinter().writeValue(response.getWriter(), openRtbConverter.convert(request2xPayload.getConfig(), request2xPayload.getBidRequest(), BidRequest.class, OpenRTB.class));
    } catch (IOException | ConfigurationException | OpenRtbConverterException e) {
      log.error("Error while sending 2xto3x request ", e);
      response.setStatus(500);
    }
  };

  private BiConsumer<HttpServletRequest, HttpServletResponse> get3xto2xRequest = (request, response) -> {
    try {
      RequestResponse3xPayload requestResponse3xPayload = JacksonObjectMapper.getMapper().readValue(request.getInputStream(), RequestResponse3xPayload.class);
      JacksonObjectMapper.getMapper().writerWithDefaultPrettyPrinter().writeValue(response.getWriter(), openRtbConverter.convert(requestResponse3xPayload.getConfig(), requestResponse3xPayload.getOpenRTB(), OpenRTB.class, BidRequest.class));
    } catch (IOException | ConfigurationException | OpenRtbConverterException e) {
      log.error("Error while sending 2xto3x request ", e);
      response.setStatus(500);
    }
  };

  private BiConsumer<HttpServletRequest, HttpServletResponse> get2xto3xResponse = (request, response) -> {
    try {
      Response2xPayload response2xPayload = JacksonObjectMapper.getMapper().readValue(request.getInputStream(), Response2xPayload.class);
      JacksonObjectMapper.getMapper().writerWithDefaultPrettyPrinter().writeValue(response.getWriter(), openRtbConverter.convert(response2xPayload.getConfig(), response2xPayload.getResponse(), BidResponse.class, OpenRTB.class));
    } catch (IOException | ConfigurationException | OpenRtbConverterException e) {
      log.error("Error while sending 2xto3x request ", e);
      response.setStatus(500);
    }
  };

  private BiConsumer<HttpServletRequest, HttpServletResponse> get3xto2xResponse = (request, response) -> {
    try {
      RequestResponse3xPayload requestResponse3xPayload = JacksonObjectMapper.getMapper().readValue(request.getInputStream(), RequestResponse3xPayload.class);
      JacksonObjectMapper.getMapper().writerWithDefaultPrettyPrinter().writeValue(response.getWriter(), openRtbConverter.convert(requestResponse3xPayload.getConfig(), requestResponse3xPayload.getOpenRTB(), OpenRTB.class, BidResponse.class));
    } catch (IOException | ConfigurationException | OpenRtbConverterException e) {
      log.error("Error while sending 2xto3x request ", e);
      response.setStatus(500);
    }
  };

  private BiConsumer<HttpServletRequest, HttpServletResponse> illegalAction = (request, response) -> {
    try {
      response.getWriter().write( "No such query exists");
    } catch (IOException e) {
      log.error("Error while sending cacheValues ", e);
    }
  };
}
