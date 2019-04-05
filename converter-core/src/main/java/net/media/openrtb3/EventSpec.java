package net.media.openrtb3;

import javax.validation.constraints.NotNull;

/**
 * Created by shiva.b on 14/12/18.
 */
public class EventSpec {
  @NotNull
  private Integer type;
  private Integer[] method;
  private Integer[] api;
  private String[] jstrk;
  private Integer wjs;
  private String[] pxtrk;
  private Integer wpx;
  private Object ext;
}
