package net.media.openrtb3;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by shiva.b on 14/12/18.
 */

@Getter
@Setter
public class AssetFormat {
  private Integer id;
  private Integer req;
  private TitleAssetFormat title;
  private ImageAssetFormat img;
  private VideoPlacement video;
  private DataAssetFormat data;
  private Map<String, Object> ext;
}
