package net.media.openrtb25.request;

import net.media.utils.JacksonObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.Data;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by vishnu on 6/5/16.
 */

@Data
public class Native {

  public static final String DEFAULT_NATIVE_VERSION = "1.1";

  @NotNull
  private Object request;

  private String ver = DEFAULT_NATIVE_VERSION;

  private List<Integer> api;

  private Set<Integer> battr;

  private Map<String, Object> ext;

  private transient NativeRequestBody nativeRequestBody;

  private transient String requestAsString;

  public NativeRequestBody getNativeRequestBody() {
    if (nonNull(nativeRequestBody)) {
      return nativeRequestBody;
    }
    if (isNull(request)) {
      return null;
    }
    NativeRequestBody nativeRequestBody = null;
    if (request instanceof String) {
      String nativeRequestString = (String) request;
      requestAsString = nativeRequestString;
      try {
        nativeRequestBody = JacksonObjectMapper.getMapper().readValue(nativeRequestString,
          NativeRequestBody.class);
      } catch (IOException e) {
        return null;
      }
    } else {
      nativeRequestBody = JacksonObjectMapper.getMapper().convertValue(request,
        NativeRequestBody.class);
    }
    return nativeRequestBody;
  }

  public String getRequestAsString() {
    if (nonNull(request) && (request instanceof String)) {
      requestAsString = (String) request;
    }
    return requestAsString;
  }

}
