package net.media.openrtb24.request;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Created by vishnu on 6/5/16.
 */
@lombok.Data
public class Producer {

  @NotNull
  private String id;

  private String name;

  private List<String> cat;

  private String domain;

  private Map<String, Object> ext;
}
