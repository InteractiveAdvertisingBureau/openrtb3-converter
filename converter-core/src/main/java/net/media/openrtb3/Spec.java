package net.media.openrtb3;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by rajat.go on 14/12/18.
 */

@Getter
@Setter
public class Spec {

  @NotNull
  @Valid
  private Placement placement;
}
