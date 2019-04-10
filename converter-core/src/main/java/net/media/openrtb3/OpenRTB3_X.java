package net.media.openrtb3;

import net.media.utils.validator.CheckExactlyOneNotNull;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@CheckExactlyOneNotNull(fieldNames = {"request", "response"})
public class OpenRTB3_X {

  private String ver = "3.0";
  private String domainSpec = "adcom";
  @NotNull
  private String domainVer = "1.0";
  @Valid
  private Request request;
  @Valid
  private Response response;

  public String getVer() {
    return this.ver;
  }

  public String getDomainSpec() {
    return this.domainSpec;
  }

  public @NotNull String getDomainVer() {
    return this.domainVer;
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

  public void setDomainSpec(String domainSpec) {
    this.domainSpec = domainSpec;
  }

  public void setDomainVer(@NotNull String domainVer) {
    this.domainVer = domainVer;
  }

  public void setRequest(@Valid Request request) {
    this.request = request;
  }

  public void setResponse(@Valid Response response) {
    this.response = response;
  }
}
