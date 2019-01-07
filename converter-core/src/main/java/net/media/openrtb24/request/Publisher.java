package net.media.openrtb24.request;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * Created by vishnu on 6/5/16.
 */
@Data
public class Publisher  {

  private static final long serialVersionUID = 1;

  @NotNull
  private String id;

  private String name;

  private List<String> cat;

  private String domain;

  private Map<String, Object> ext;

  public Publisher(String id) {
    this.id = id;
  }

  public Publisher() {
  }

}