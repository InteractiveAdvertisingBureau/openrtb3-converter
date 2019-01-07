package net.media.openrtb24.request;

import java.util.Map;

/**
 * Created by vishnu on 6/5/16.
 */

@lombok.Data
public class Segment {

  private String id;

  private String name;

  private String value;

  private Map<String, Object> ext;
}
