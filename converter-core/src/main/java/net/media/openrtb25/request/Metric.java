package net.media.openrtb25.request;

import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by rajat.go on 30/12/18.
 */

@Getter
@Setter
public class Metric {

  @NotBlank
  private String type;

  @NotNull
  private Double value;

  private String vendor;

  private Map<String, Object> ext;
}
