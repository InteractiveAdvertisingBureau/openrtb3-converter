
package net.media.openrtb3;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TitleAsset {

  private String text;
  private Integer len;
  private Map<String, Object> ext;

}