package net.media.openrtb25.request;

import java.util.Map;

import lombok.Data;

/**
 * Created by rajat.go on 13/10/16.
 */

@Data
public class Format {

  private Integer w;

  private Integer h;

  private Integer wratio;

  private Integer hratio;

  private Integer wmin;

  private Map<String, Object> ext;
}
