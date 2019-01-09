package net.media.openrtb25.request;

import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class NativeTitle {

  @NotNull
  private Integer len;

  private Map<String, Object> ext;

}
