package net.media.openrtb3;

import net.media.utils.validator.CheckExactlyOneNotNull;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@CheckExactlyOneNotNull(fieldNames = {"request", "response"})
public class OpenRTB {

  private String ver = "3.0";
  private String domainspec = "adcom";
  @NotNull
  private String domainver = "1.0";
  @Valid
  private Request request;
  @Valid
  private Response response;

  public String getVer() {
    return this.ver;
  }

  public String getDomainspec() {
    return this.domainspec;
  }

  public @NotNull String getDomainver() {
    return this.domainver;
  }

  public @Valid Request getRequest() {
    return this.request;
  }

  public @Valid Response getResponse() {
    return this.response;
  }

  public void setVer(String ver) {
    this.ver = ver;
  }

  public void setDomainspec(String domainspec) {
    this.domainspec = domainspec;
  }

  public void setDomainver(@NotNull String domainver) {
    this.domainver = domainver;
  }

  public void setRequest(@Valid Request request) {
    this.request = request;
  }

  public void setResponse(@Valid Response response) {
    this.response = response;
  }
}
