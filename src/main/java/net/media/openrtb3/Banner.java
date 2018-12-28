package net.media.openrtb3;


import lombok.Data;

import java.util.Map;

@Data
public class Banner {

  private String img;
  private LinkAsset link;
  private Map<String,Object> ext;


  public static Banner HashMapToBanner (Map<String,Object> map){
    Banner banner =  new Banner();
    banner.setImg((String)map.get("img"));
    banner.setLink(LinkAsset.HashMaptoLinkAsset((Map<String,Object>)map.get("link")));
    banner.setExt((Map<String, Object>) map.get("ext"));
    return banner;
  }

}