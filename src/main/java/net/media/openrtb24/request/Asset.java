package net.media.openrtb24.request;

import net.media.utils.validator.CheckAtLeastOneNotNull;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@CheckAtLeastOneNotNull(fieldNames = {"title", "img", "video", "data"})
public class Asset extends AbstractExtensible<Asset.AssetReqExt> {

  @NotNull
  private Integer id;

  private Integer required;

  @Valid
  private NativeTitle title;

  @Valid
  private NativeImage img;

  @Valid
  private NativeVideo video;

  @Valid
  private NativeData data;

  private Map<String, Object> ext;

  public Asset() {
    setReqExt(new AssetReqExt());
  }

  public static class AssetReqExt extends ReqExt {
  }
}
