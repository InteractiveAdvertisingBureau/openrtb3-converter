package net.media.openrtb24.request;

import java.util.List;

/**
 * Created by vishnu on 6/5/16.
 */
@lombok.Data
public class Producer extends AbstractExtensible<Producer.ProducerReqExt> {

  private String id;

  private String name;

  private List<String> cat;

  private String domain;

  private Ext ext;

  public Producer() {
    setReqExt(new ProducerReqExt());
  }

  public static class ProducerReqExt extends ReqExt {
  }
}
