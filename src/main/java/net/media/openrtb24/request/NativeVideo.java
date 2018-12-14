package net.media.openrtb24.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class NativeVideo extends AbstractExtensible<NativeVideo.NativeVideoReqExt> {

  @NotNull
  private List<String> mimes;

  @NotNull
  private Integer minduration;

  @NotNull
  private Integer maxduration;

  @NotNull
  private List<Integer> protocols;

  private Ext ext;

  public NativeVideo() {
    setReqExt(new NativeVideoReqExt());
  }

  public static class NativeVideoReqExt extends ReqExt {
  }
}
