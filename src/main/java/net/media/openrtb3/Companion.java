package net.media.openrtb3;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by shiva.b on 14/12/18.
 */

@Getter
@Setter
public class Companion {
  private String id;
  private Integer vcm;
  private DisplayPlacement display;
  private Map<String, Object> ext;
}
