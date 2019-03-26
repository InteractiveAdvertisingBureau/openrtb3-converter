package net.media.openrtb25.request;

import java.util.List;
import java.util.Map;

/**
 * Created by vishnu on 6/5/16.
 */

@lombok.Data
public class Data {
  private String id;

  private String name;

  private List<Segment> segment;

  private Map<String, Object> ext;
}
