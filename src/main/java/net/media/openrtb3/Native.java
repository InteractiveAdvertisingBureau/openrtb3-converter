package net.media.openrtb3;

import java.util.List;

import javax.validation.Valid;

public class Native {

  @Valid
  private LinkAsset link;
  @Valid
  private List<Asset> asset = null;
  private String ext;

}