package net.media.openrtb3;

import lombok.Data;

import java.util.List;

@Data
public class LinkAsset {

  private String url;
  private String urlfb;
  private List<String> trkr = null;
  private String ext;

}