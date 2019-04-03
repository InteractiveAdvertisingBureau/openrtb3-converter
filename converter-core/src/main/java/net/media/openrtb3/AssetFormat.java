package net.media.openrtb3;

import net.media.utils.validator.CheckExactlyOneNotNull;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by shiva.b on 14/12/18.
 */

@CheckExactlyOneNotNull(fieldNames = {"title", "img", "video", "data"})
public class AssetFormat {

  private static final Integer DEFAULT_REQUIRED = 0;

  @NotNull
  private Integer id;
  private Integer req = DEFAULT_REQUIRED;
  @Valid
  private TitleAssetFormat title;
  private ImageAssetFormat img;
  @Valid
  private VideoPlacement video;
  @Valid
  private DataAssetFormat data;
  private Map<String, Object> ext;

  public @NotNull Integer getId() {
    return this.id;
  }

  public Integer getReq() {
    return this.req;
  }

  public @Valid TitleAssetFormat getTitle() {
    return this.title;
  }

  public ImageAssetFormat getImg() {
    return this.img;
  }

  public @Valid VideoPlacement getVideo() {
    return this.video;
  }

  public @Valid DataAssetFormat getData() {
    return this.data;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setId(@NotNull Integer id) {
    this.id = id;
  }

  public void setReq(Integer req) {
    this.req = req;
  }

  public void setTitle(@Valid TitleAssetFormat title) {
    this.title = title;
  }

  public void setImg(ImageAssetFormat img) {
    this.img = img;
  }

  public void setVideo(@Valid VideoPlacement video) {
    this.video = video;
  }

  public void setData(@Valid DataAssetFormat data) {
    this.data = data;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
