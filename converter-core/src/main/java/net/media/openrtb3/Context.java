package net.media.openrtb3;

import javax.validation.Valid;

public class Context {
  private Site site;
  private App app;
  @Valid
  private User user;
  private Dooh dooh;
  private Device device;
  private Regs regs;
  private Restrictions restrictions;

  public Site getSite() {
    return this.site;
  }

  public App getApp() {
    return this.app;
  }

  public @Valid User getUser() {
    return this.user;
  }

  public Dooh getDooh() {
    return this.dooh;
  }

  public Device getDevice() {
    return this.device;
  }

  public Regs getRegs() {
    return this.regs;
  }

  public Restrictions getRestrictions() {
    return this.restrictions;
  }

  public void setSite(Site site) {
    this.site = site;
  }

  public void setApp(App app) {
    this.app = app;
  }

  public void setUser(@Valid User user) {
    this.user = user;
  }

  public void setDooh(Dooh dooh) {
    this.dooh = dooh;
  }

  public void setDevice(Device device) {
    this.device = device;
  }

  public void setRegs(Regs regs) {
    this.regs = regs;
  }

  public void setRestrictions(Restrictions restrictions) {
    this.restrictions = restrictions;
  }
}
