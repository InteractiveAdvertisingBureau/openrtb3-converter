package net.media.openrtb3;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by shiva.b on 14/12/18.
 */

@Getter
@Setter
public class NativeFormat {
  @NotEmpty
  @Valid
  public List<AssetFormat> asset;
  public Map<String, Object> ext;
}
