package net.media.openrtb24.request;

import lombok.Data;

/**
 * Created by shiva.b on 18/10/16.
 */
@Data
public class MediaFile extends AbstractExtensible<MediaFile.MediaReqExt> {
  private String id;
  private String url;
  private String width;
  private String height;

  public MediaFile() {
    setReqExt(new MediaReqExt());
  }

  public static class MediaReqExt extends ReqExt {
  }
}
