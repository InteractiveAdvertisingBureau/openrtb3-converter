package net.media.openrtb24.request;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class NativeImage {

  private Integer type;

  private Integer w;

  private Integer wmin;

  private Integer h;

  private Integer hmin;

  private List<String> mimes;

  private Map<String, Object> ext;

}
