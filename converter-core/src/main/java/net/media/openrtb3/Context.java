package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;

@Getter
@Setter
public class Context {
  private Site site;
  private App app;
  @Valid
  private User user;
  private Dooh dooh;
  private Device device;
  private Regs regs;
  private Restrictions restrictions;
}
