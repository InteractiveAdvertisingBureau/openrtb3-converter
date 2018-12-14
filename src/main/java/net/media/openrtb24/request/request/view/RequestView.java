package net.media.openrtb24.request.request.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.media.openrtb24.request.Imp;

/**
 * Created by shashankt on 16/5/17.
 */
public abstract class RequestView {

  @JsonIgnore
  public abstract void setExt(Object ext);

  @JsonProperty("ext")
  public abstract void setReqExt(Imp.ImpReqExt req);

  @JsonIgnore
  public abstract Object getExt();

  @JsonProperty("ext")
  public abstract Imp.ImpReqExt getReqExt();


}
