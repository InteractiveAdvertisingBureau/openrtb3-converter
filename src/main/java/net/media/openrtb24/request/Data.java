package net.media.openrtb24.request;

import java.util.List;

/**
 * Created by vishnu on 6/5/16.
 */

@lombok.Data
public class Data {
  private String id;

  private String name;

  private List<Segment> segment;

  private Ext ext;
}
