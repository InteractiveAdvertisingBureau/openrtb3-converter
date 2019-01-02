
package net.media.openrtb3;


import javax.validation.constraints.NotNull;

public class TitleAsset {

  @NotNull
  private String text;
  private Integer len;
  private String ext;

}