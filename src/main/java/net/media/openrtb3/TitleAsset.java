
package net.media.openrtb3;


import lombok.Data;

import java.util.Map;

@Data
public class TitleAsset {

  private String text;
  private Integer len;
  private Map<String,Object> ext;

}