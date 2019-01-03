package net.media.openrtb3;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Banner {

  @NotNull
  private String img;
  @Valid
  private LinkAsset link;
  private String ext;

}