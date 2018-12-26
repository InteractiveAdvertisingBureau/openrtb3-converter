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

}