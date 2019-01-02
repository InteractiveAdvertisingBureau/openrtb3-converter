package net.media.openrtb24.request;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by rajat.go on 30/12/18.
 */

@Getter
@Setter
public class Metric {

  private String type;

  private Double value;

  private String vendor;

  private Map<String, Object> ext;
}
