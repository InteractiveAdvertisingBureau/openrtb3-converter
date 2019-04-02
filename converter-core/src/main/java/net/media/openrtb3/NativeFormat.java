package net.media.openrtb3;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * Created by shiva.b on 14/12/18.
 */

public class NativeFormat {
  @NotEmpty
  @Valid
  public Collection<AssetFormat> asset;
  public Map<String, Object> ext;

  public @NotEmpty @Valid Collection<AssetFormat> getAsset() {
    return this.asset;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setAsset(@NotEmpty @Valid Collection<AssetFormat> asset) {
    this.asset = asset;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
