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
public class AudioPlacement {
  private Integer delay;
  private Integer skip;
  private Integer skipmin = 0;
  private Integer skipafter = 0;
  private Integer playmethod;
  private Integer playend;
  private Integer feed;
  private Integer nvol;
  private List<String> mime;
  private List<Integer> api;
  private List<Integer> ctype;
  private Integer mindur;
  private Integer maxdur;
  private Integer maxext;
  private Integer minbitr;
  private Integer maxbitr;
  private List<Integer> delivery;
  private Integer maxseq;
  private List<Companion> comp;
  private List<Integer> comptype;
  private Map<String, Object> ext;
}
