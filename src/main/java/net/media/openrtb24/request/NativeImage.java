package net.media.openrtb24.request;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class NativeImage extends AbstractExtensible<NativeImage.NativeImageReqExt> {

  private Integer type;

  private Integer w;

  private Integer wmin;

  private Integer h;

  private Integer hmin;

  private List<String> mimes;

  private Map<String, Object> ext;

  public NativeImage() {
    setReqExt(new NativeImageReqExt());
  }

  public static class NativeImageReqExt extends ReqExt {
  }
}
