package net.media.openrtb25.request;


import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;

/**
 * Created by shashankt on 17/5/17.
 */
public abstract class AbstractExtensible<E extends ReqExt> {

  transient private E reqExt;

  transient private HashMap<String, Object> unknowProperty = new HashMap<>();

  public AbstractExtensible() {
  }

  @JsonAnySetter
  public void setExtProperty(String key, Object value) {
    unknowProperty.put(key, value);
  }

  public E getReqExt() {
    return this.reqExt;
  }

  public HashMap<String, Object> getUnknowProperty() {
    return this.unknowProperty;
  }

  public void setReqExt(E reqExt) {
    this.reqExt = reqExt;
  }

  public void setUnknowProperty(HashMap<String, Object> unknowProperty) {
    this.unknowProperty = unknowProperty;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof AbstractExtensible)) return false;
    final AbstractExtensible other = (AbstractExtensible) o;
    return other.canEqual((Object) this);
  }

  public int hashCode() {
    int result = 1;
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof AbstractExtensible;
  }

  public String toString() {
    return "com.medianet.rtb.mowgli.commons.dto.request.AbstractExtensible(reqExt=" + this.getReqExt() + ", unknowProperty=" + this.getUnknowProperty() + ")";
  }
}
