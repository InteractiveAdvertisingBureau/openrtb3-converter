package net.media.openrtb3;

import net.media.utils.validator.CheckExactlyOneNotNull;

@CheckExactlyOneNotNull(fieldNames = {"adm", "curl"})
public class VideoAsset {

  private String adm;
  private String curl;
  private String ext;

}