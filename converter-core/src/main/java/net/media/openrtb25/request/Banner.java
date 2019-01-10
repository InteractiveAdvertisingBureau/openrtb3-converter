package net.media.openrtb25.request;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by vishnu on 6/5/16.
 */
@Data
@NoArgsConstructor
public class Banner {

  public static final List<Integer> DEFAULT_MIME_TYPES = Arrays.asList(1, 2, 4);

  public static final Integer DEFAULT_BANNER_POSITION = 1;

  private Integer w;

  private Integer h;

  private List<Format> format;

  private String id;

  private List<Integer> btype;

  private Set<Integer> battr;

  private Integer pos;

  private List<String> mimes;

  private Integer topframe;

  private List<Integer> expdir;

  private List<Integer> api;

  private Integer vcm;

  private Map<String, Object> ext;

  public Banner(Banner banner) {
    this.id = banner.id;
    this.w = banner.w;
    this.h = banner.h;
    this.format = banner.format;
    this.btype = banner.btype;
    this.battr = banner.battr;
    this.pos = banner.pos;
    this.mimes = banner.mimes;
    this.topframe = banner.topframe;
    this.expdir = banner.expdir;
    this.api = banner.api;
    this.ext = banner.ext;
  }
}
