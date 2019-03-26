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
public class DataAssetFormat {
  @NotNull
  private Integer type;
  private Integer len;
  private Map<String, Object> ext;
}
