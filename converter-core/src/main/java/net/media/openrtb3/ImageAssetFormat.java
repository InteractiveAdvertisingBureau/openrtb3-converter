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
public class ImageAssetFormat {
  private Integer type;
  private List<String> mime;
  private Integer w;
  private Integer h;
  private Integer wmin;
  private Integer hmin;
  private Integer wratio;
  private Integer hratio;
  private Map<String, Object> ext;
}
