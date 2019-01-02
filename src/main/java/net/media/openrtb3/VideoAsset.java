package net.media.openrtb3;


import lombok.Data;

import java.util.Map;

@Data
public class VideoAsset {

  private String adm;
  private String curl;
  private Map<String,Object> ext;

}