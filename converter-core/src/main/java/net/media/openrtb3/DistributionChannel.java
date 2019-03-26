package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public abstract class DistributionChannel {
  @NotNull
  private String id;
  private String name;
  private Publisher pub;
  private Content content;
}
