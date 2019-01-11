package net.media.openrtb25.request;

import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class NativeData {

  @NotNull
  private Integer type;

  private Integer len;

  private Map<String, Object> ext;
}
