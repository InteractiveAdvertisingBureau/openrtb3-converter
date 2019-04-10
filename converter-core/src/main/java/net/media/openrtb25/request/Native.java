package net.media.openrtb25.request;

import net.media.utils.JacksonObjectMapper;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by vishnu on 6/5/16.
 */

public class Native {

  public static final String DEFAULT_NATIVE_VERSION = "1.1";

  @NotNull
  private Object request;

  private String ver = DEFAULT_NATIVE_VERSION;

  private Collection<Integer> api;

  private Collection<Integer> battr;

  private Map<String, Object> ext;

  private transient NativeRequestBody nativeRequestBody;

  private transient String requestAsString;

  public Native() {
  }

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
      NativeRequest nativeRequest = JacksonObjectMapper.getMapper().convertValue(request,NativeRequest.class);
      nativeRequestBody = JacksonObjectMapper.getMapper().convertValue(nativeRequest.getNativeRequestBody(),
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

  public @NotNull Object getRequest() {
    return this.request;
  }

  public String getVer() {
    return this.ver;
  }

  public Collection<Integer> getApi() {
    return this.api;
  }

  public Collection<Integer> getBattr() {
    return this.battr;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setRequest(@NotNull Object request) {
    this.request = request;
  }

  public void setVer(String ver) {
    this.ver = ver;
  }

  public void setApi(Collection<Integer> api) {
    this.api = api;
  }

  public void setBattr(Collection<Integer> battr) {
    this.battr = battr;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public void setNativeRequestBody(NativeRequestBody nativeRequestBody) {
    this.nativeRequestBody = nativeRequestBody;
  }

  public void setRequestAsString(String requestAsString) {
    this.requestAsString = requestAsString;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Native)) return false;
    final Native other = (Native) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$request = this.getRequest();
    final Object other$request = other.getRequest();
    if (this$request == null ? other$request != null : !this$request.equals(other$request))
      return false;
    final Object this$ver = this.getVer();
    final Object other$ver = other.getVer();
    if (this$ver == null ? other$ver != null : !this$ver.equals(other$ver)) return false;
    final Object this$api = this.getApi();
    final Object other$api = other.getApi();
    if (this$api == null ? other$api != null : !this$api.equals(other$api)) return false;
    final Object this$battr = this.getBattr();
    final Object other$battr = other.getBattr();
    if (this$battr == null ? other$battr != null : !this$battr.equals(other$battr)) return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $request = this.getRequest();
    result = result * PRIME + ($request == null ? 43 : $request.hashCode());
    final Object $ver = this.getVer();
    result = result * PRIME + ($ver == null ? 43 : $ver.hashCode());
    final Object $api = this.getApi();
    result = result * PRIME + ($api == null ? 43 : $api.hashCode());
    final Object $battr = this.getBattr();
    result = result * PRIME + ($battr == null ? 43 : $battr.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Native;
  }

  public String toString() {
    return "net.media.openrtb25.request.Native(request=" + this.getRequest() + ", ver=" + this.getVer() + ", api=" + this.getApi() + ", battr=" + this.getBattr() + ", ext=" + this.getExt() + ", nativeRequestBody=" + this.getNativeRequestBody() + ", requestAsString=" + this.getRequestAsString() + ")";
  }
}
