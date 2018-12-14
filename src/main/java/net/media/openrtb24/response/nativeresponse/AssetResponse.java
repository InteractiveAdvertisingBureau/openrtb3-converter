package net.media.openrtb24.response.nativeresponse;

import java.util.Map;

import lombok.Data;

@Data
public class AssetResponse {

  private static final Integer DEFAULT_REQUIRED = 0;

  private Integer id;

  private Integer required = DEFAULT_REQUIRED;

  private NativeTitle title;

  private NativeImage img;

  private NativeVideo video;

  private NativeData data;

  private Link link;

  private Map<String, Object> ext;
}
