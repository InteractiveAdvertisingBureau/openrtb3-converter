
package net.media.openrtb3;


import lombok.Data;

import java.util.Map;
import javax.validation.constraints.NotNull;
@Data
public class TitleAsset {

  @NotNull
  private String text;
  private Integer len;
  private Map<String,Object> ext;

}