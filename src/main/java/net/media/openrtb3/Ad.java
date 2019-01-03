package net.media.openrtb3;

import net.media.utils.validator.CheckExactlyOneNotNull;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.*;

/**
 * Created by shiva.b on 17/12/18.
 */
@lombok.Data
@CheckExactlyOneNotNull(fieldNames = {"video", "audio", "display"})
public class Ad {

  @NotNull
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
  @Valid
  private Display display;
  @Valid
  private Video video;
  @Valid
  private Audio audio;
  @Valid
  private Audit audit;
  private Map<String, Object> ext;
}
