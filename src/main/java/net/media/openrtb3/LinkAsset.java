package net.media.openrtb3;

import lombok.Data;

import java.util.List;

import javax.validation.constraints.NotNull;

@Data
public class LinkAsset {

  @NotNull
  private String url;
  private String urlfb;
  private List<String> trkr = null;
  private String ext;

}