
package net.media.openrtb3;

import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TitleAsset {

  @NotNull
  private String text;
  private Integer len;
  private Map<String, Object> ext;

}