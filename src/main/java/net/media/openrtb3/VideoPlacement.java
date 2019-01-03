package net.media.openrtb3;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by shiva.b on 14/12/18.
 */

@Getter
@Setter
public class VideoPlacement {
  private Integer ptype;
  private Integer pos;
  private Integer delay;
  private Integer skip;
  private Integer skipmin = 0;
  private Integer skipafter = 0;
  private Integer playmethod;
  private Integer playend;
  private Integer clktype;
  @NotEmpty
  private Set<String> mime;
  private Set<Integer> api;
  private Set<Integer> ctype;
  private Integer w;
  private Integer h;
  private Integer unit = 1;
  private Integer mindur;
  private Integer maxdur;
  private Integer maxext = 0;
  private Integer minbitr;
  private Integer maxbitr;
  private List<Integer> delivery;
  private Integer maxseq;
  private Integer linear;
  private Integer boxing = 1;
  private List<Companion> comp;
  private List<Integer> comptype;
  private Map<String, Object> ext;
}
