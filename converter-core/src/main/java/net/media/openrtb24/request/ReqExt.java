package net.media.openrtb24.request;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;

/**
 * Created by shashankt on 17/5/17.
 */
public abstract class ReqExt {

  transient private HashMap<String, Object> unknowProperty = new HashMap<>();

  @JsonAnySetter
  public void setExt(String key, Object value) {
    unknowProperty.put(key, value);
  }

  public Object getExt(String key){
    return unknowProperty.get(key);
  }


}
