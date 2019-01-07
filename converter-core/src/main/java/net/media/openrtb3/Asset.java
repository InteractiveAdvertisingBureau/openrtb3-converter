package net.media.openrtb3;


import net.media.utils.validator.CheckExactlyOneNotNull;

import javax.validation.Valid;

import net.media.utils.validator.CheckExactlyOneNotNull;

import javax.validation.Valid;
@lombok.Data
@CheckExactlyOneNotNull(fieldNames = {"title", "image", "video", "data", "link"})
public class Asset {

  private Integer id;
  private Integer req;
  @Valid
  private TitleAsset title;
  @Valid
  private ImageAsset image;
  @Valid
  private VideoAsset video;
  @Valid
  private DataAsset data;
  @Valid
  private LinkAsset link;

}