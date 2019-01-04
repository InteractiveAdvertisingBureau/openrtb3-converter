package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Producer {
  @NotNull
  private String id;
  private String name;
  private String domain;
  private List<String> cat;
  private Integer cattax;
  private Map<String, Object> ext;

}
