package net.media.openrtb3;


import lombok.Data;

import java.util.Map;

@Data
public class Banner {

  private String img;
  private LinkAsset link;
  private Map<String,Object> ext;

}