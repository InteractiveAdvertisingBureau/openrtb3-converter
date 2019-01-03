package net.media.openrtb24.request;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class NativeVideo extends AbstractExtensible<NativeVideo.NativeVideoReqExt> {

  @NotEmpty
  private Set<String> mimes;

  @NotNull
  private Integer minduration;

  @NotNull
  private Integer maxduration;

  @NotEmpty
  private Set<Integer> protocols;

  private Map<String, Object> ext;

  public NativeVideo() {
    setReqExt(new NativeVideoReqExt());
  }

  public static class NativeVideoReqExt extends ReqExt {
  }
}
