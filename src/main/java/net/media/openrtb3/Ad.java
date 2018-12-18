package net.media.openrtb3;

import java.util.List;

/**
 * Created by shiva.b on 17/12/18.
 */
public class Ad {

  private String id;//bid.crid
  private List<String> adomain = null;//bid
  private List<String> bundle = null;//bid(String to  List)
  private String iurl;//bid
  private List<String> cat = null;//bid
  private Integer cattax;//bid.ext
  private String lang;//bid.language
  private List<Integer> attr = null;//bid
  private Integer secure;//bid.ext
  private Integer mrating;//bid.ext
  private Integer init;//bid.ext
  private Integer lastmod;//bid.ext
  private Display display;
  private Video video;
  private Audio audio;
  private Audit audit;
  private Object ext;
}
