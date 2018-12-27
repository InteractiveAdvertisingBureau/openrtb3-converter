package net.media.openrtb3;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by shiva.b on 14/12/18.
 */

@Getter
@Setter
public class NativeFormat {
  public List<AssetFormat> asset;
  public Map<String, Object> ext;
}
