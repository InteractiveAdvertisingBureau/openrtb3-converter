package net.media.openrtb24.request;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class NativeVideo {

  @NotEmpty
  private Set<String> mimes;

  @NotNull
  private Integer minduration;

  @NotNull
  private Integer maxduration;

  @NotEmpty
  private Set<Integer> protocols;

  private Map<String, Object> ext;

}
