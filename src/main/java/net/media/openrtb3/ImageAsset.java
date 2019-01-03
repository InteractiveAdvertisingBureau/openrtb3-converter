package net.media.openrtb3;

import lombok.Data;

import java.util.Map;

import javax.validation.constraints.NotNull;

/**
 * Created by shiva.b on 17/12/18.
 */
@Data
public class ImageAsset {

  @NotNull
  private String url;
  private Integer w;
  private Integer h;
  private Integer type;
  private Map<String,Object> ext;
}
