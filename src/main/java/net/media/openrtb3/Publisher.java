package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
public class Publisher {

  @NotNull
  private String id;
  private String name;
  private String domain;
  private String[] cat;
  private Integer cattax;
  private Map<String, Object> ext;

}
