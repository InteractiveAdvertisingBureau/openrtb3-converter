package net.media.openrtb3;

import java.util.List;
import java.util.Map;

/**
 * Created by shiva.b on 14/12/18.
 */

public class DisplayFormat {
  private Integer w;
  private Integer h;
  private Integer wratio;
  private Integer hratio;
  private List<Integer> expdir;
  private Map<String, Object> ext;

  public Integer getW() {
    return this.w;
  }

  public Integer getH() {
    return this.h;
  }

  public Integer getWratio() {
    return this.wratio;
  }

  public Integer getHratio() {
    return this.hratio;
  }

  public List<Integer> getExpdir() {
    return this.expdir;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setW(Integer w) {
    this.w = w;
  }

  public void setH(Integer h) {
    this.h = h;
  }

  public void setWratio(Integer wratio) {
    this.wratio = wratio;
  }

  public void setHratio(Integer hratio) {
    this.hratio = hratio;
  }

  public void setExpdir(List<Integer> expdir) {
    this.expdir = expdir;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
