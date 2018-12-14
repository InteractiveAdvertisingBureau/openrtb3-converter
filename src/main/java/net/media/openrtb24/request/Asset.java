package net.media.openrtb24.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Asset extends AbstractExtensible<Asset.AssetReqExt> {

  @NotNull
  private Integer id;

  private Integer required;

  private NativeTitle title;

  @Valid
  private NativeImage img;

  @Valid
  private NativeVideo video;

  @Valid
  private NativeData data;

  private Ext ext;

  public Asset() {
    setReqExt(new AssetReqExt());
  }

  public static class AssetReqExt extends ReqExt {
  }
}
