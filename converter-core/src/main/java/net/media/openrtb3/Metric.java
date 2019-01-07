package net.media.openrtb3;

import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by shiva.b on 14/12/18.
 */

@Getter
@Setter
public class Metric {
  private String type;
  @NotNull
  private Double value;
  private String vendor;
  private Map<String, Object> ext;
}
