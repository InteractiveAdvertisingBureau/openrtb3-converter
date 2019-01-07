package net.media.openrtb3;

import net.media.utils.validator.CheckExactlyOneNotNull;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@CheckExactlyOneNotNull(fieldNames = {"request", "response"})
public class OpenRTB {

  private String ver;
  private String domainSpec;
  @NotNull
  private String domainVer;
  @Valid
  private Request request;
  @Valid
  private Response response;

}
