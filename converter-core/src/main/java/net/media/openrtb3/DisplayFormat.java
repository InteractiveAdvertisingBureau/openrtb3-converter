package net.media.openrtb3;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by shiva.b on 14/12/18.
 */

@Getter
@Setter
public class DisplayFormat {
  private Integer w;
  private Integer h;
  private Integer wratio;
  private Integer hratio;
  private List<Integer> expdir;
  private Map<String, Object> ext;
}
