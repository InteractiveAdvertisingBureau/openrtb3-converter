package net.media.openrtb3;


import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Macro {

  @NotNull
  private String key;
  private String value;

}