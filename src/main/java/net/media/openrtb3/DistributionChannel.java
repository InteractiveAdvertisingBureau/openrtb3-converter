package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class DistributionChannel {

  private String id;
  private String name;
  private Publisher pub;
  private Content content;
}
