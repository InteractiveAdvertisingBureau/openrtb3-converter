package net.media.openrtb3;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.*;

@lombok.Data
public class Media {

  @NotNull
  @Valid
  private Ad ad;

}