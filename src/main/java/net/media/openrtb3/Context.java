package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Context {
  private Site site;
  private App app;
  private User user;
  private Device device;
  private Regs regs;
  private Restrictions restrictions;
}
