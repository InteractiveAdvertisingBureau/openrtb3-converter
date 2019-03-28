package net.media.openrtb25.request;

/**
 * Created by shiva.b on 18/10/16.
 */
public class MediaFile {
  private String id;
  private String url;
  private String width;
  private String height;

  public MediaFile() {
  }

  public String getId() {
    return this.id;
  }

  public String getUrl() {
    return this.url;
  }

  public String getWidth() {
    return this.width;
  }

  public String getHeight() {
    return this.height;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof MediaFile)) return false;
    final MediaFile other = (MediaFile) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$id = this.getId();
    final Object other$id = other.getId();
    if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
    final Object this$url = this.getUrl();
    final Object other$url = other.getUrl();
    if (this$url == null ? other$url != null : !this$url.equals(other$url)) return false;
    final Object this$width = this.getWidth();
    final Object other$width = other.getWidth();
    if (this$width == null ? other$width != null : !this$width.equals(other$width)) return false;
    final Object this$height = this.getHeight();
    final Object other$height = other.getHeight();
    if (this$height == null ? other$height != null : !this$height.equals(other$height))
      return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $id = this.getId();
    result = result * PRIME + ($id == null ? 43 : $id.hashCode());
    final Object $url = this.getUrl();
    result = result * PRIME + ($url == null ? 43 : $url.hashCode());
    final Object $width = this.getWidth();
    result = result * PRIME + ($width == null ? 43 : $width.hashCode());
    final Object $height = this.getHeight();
    result = result * PRIME + ($height == null ? 43 : $height.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof MediaFile;
  }

  public String toString() {
    return "net.media.openrtb25.request.MediaFile(id=" + this.getId() + ", url=" + this.getUrl() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ")";
  }
}
