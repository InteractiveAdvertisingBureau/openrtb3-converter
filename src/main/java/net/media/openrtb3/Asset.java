package net.media.openrtb3;


import net.media.utils.validator.CheckExactlyOneNotNull;

import javax.validation.Valid;

@CheckExactlyOneNotNull(fieldNames = {"title", "image", "video", "data", "link"})
public class Asset {

  private String id;
  private Integer req;
  @Valid
  private TitleAsset title;
  @Valid
  private Image image;
  @Valid
  private VideoAsset video;
  @Valid
  private Data data;
  @Valid
  private LinkAsset link;

}