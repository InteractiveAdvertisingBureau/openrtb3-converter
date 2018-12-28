package net.media.openrtb3;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class LinkAsset {

  private String url;
  private String urlfb;
  private List<String> trkr = null;
  private Map<String,Object> ext;

  public static LinkAsset HashMaptoLinkAsset(Map<String,Object> map){
    LinkAsset linkAsset = new LinkAsset();
    linkAsset.setUrl((String)map.get("url"));
    linkAsset.setUrlfb((String)map.get("urlfb"));
    linkAsset.setTrkr((List<String>)map.get("trkr"));
    linkAsset.setExt((Map<String,Object>)map.get("ext"));
    return linkAsset;
  }

}