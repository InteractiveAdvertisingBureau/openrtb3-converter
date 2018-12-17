package net.media.openrtb3;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisplayPlacement {
  private Integer pos;
  private Integer instl;
  private Integer topframe;
  private List<String> ifrbust;
  private Integer clktype;
  private Integer ampren;
  private Integer ptype;
  private String mime;
  private Integer[] api;
  private Integer[] ctype;
  private Integer w;
  private Integer h;
  private Integer unit;
  private Integer priv;
  private DisplayFormat displayfmt;
  private NativeFormat nativefmt;
  private EventSpec event;
  private Object ext;
}