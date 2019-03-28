package net.media.openrtb3;

import java.util.List;
import java.util.Map;

/**
 * Created by shiva.b on 14/12/18.
 */

public class ImageAssetFormat {
  private Integer type;
  private List<String> mime;
  private Integer w;
  private Integer h;
  private Integer wmin;
  private Integer hmin;
  private Integer wratio;
  private Integer hratio;
  private Map<String, Object> ext;

  public Integer getType() {
    return this.type;
  }

  public List<String> getMime() {
    return this.mime;
  }

  public Integer getW() {
    return this.w;
  }

  public Integer getH() {
    return this.h;
  }

  public Integer getWmin() {
    return this.wmin;
  }

  public Integer getHmin() {
    return this.hmin;
  }

  public Integer getWratio() {
    return this.wratio;
  }

  public Integer getHratio() {
    return this.hratio;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public void setMime(List<String> mime) {
    this.mime = mime;
  }

  public void setW(Integer w) {
    this.w = w;
  }

  public void setH(Integer h) {
    this.h = h;
  }

  public void setWmin(Integer wmin) {
    this.wmin = wmin;
  }

  public void setHmin(Integer hmin) {
    this.hmin = hmin;
  }

  public void setWratio(Integer wratio) {
    this.wratio = wratio;
  }

  public void setHratio(Integer hratio) {
    this.hratio = hratio;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
