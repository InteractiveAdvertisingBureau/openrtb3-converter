package net.media.openrtb3;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by rajat.go on 14/12/18.
 */

public class Spec {

  @NotNull
  @Valid
  private Placement placement;

  public @NotNull @Valid Placement getPlacement() {
    return this.placement;
  }

  public void setPlacement(@NotNull @Valid Placement placement) {
    this.placement = placement;
  }
}
