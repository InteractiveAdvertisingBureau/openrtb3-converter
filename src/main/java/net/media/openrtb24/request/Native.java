package net.media.openrtb24.request;

import java.util.List;
import java.util.Set;

import lombok.Data;

/**
 * Created by vishnu on 6/5/16.
 */

@Data
public class Native extends AbstractExtensible<Native.NativeReqExt> {

  public static final String DEFAULT_NATIVE_VERSION = "1.1";

  private Object request;

  private String ver = DEFAULT_NATIVE_VERSION;

  private List<Integer> api;

  private Set<Integer> battr;

  private Ext ext;

  public Native() {
    setReqExt(new NativeReqExt());
  }

  public static class NativeReqExt extends ReqExt {
  }
}
