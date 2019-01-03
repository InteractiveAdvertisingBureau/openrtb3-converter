package net.media.openrtb3;

import net.media.utils.validator.CheckExactlyOneNotNull;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by shiva.b on 14/12/18.
 */

@Getter
@Setter
@CheckExactlyOneNotNull(fieldNames = {"title", "img", "video", "data"})
public class AssetFormat {
  @NotNull
  private Integer id;
  private Integer req;
  @Valid
  private TitleAssetFormat title;
  private ImageAssetFormat img;
  @Valid
  private VideoPlacement video;
  @Valid
  private DataAssetFormat data;
  private Map<String, Object> ext;
}
