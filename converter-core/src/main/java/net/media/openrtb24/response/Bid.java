package net.media.openrtb24.response;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static java.util.Objects.nonNull;

/**
 * Created by vishnu on 30/5/16.
 */
@Data
@NoArgsConstructor
public class Bid {

  @NotNull
  private String id;//
  @NotNull
  private String impid;//
  @NotNull
  private double price;//
  private String adid;
  private String nurl;//bid.purl
  private Object adm;//media.ad.(display.adm/audio.adm/video.adm)
  private List<String> adomain;//media.ad
  private String iurl;//media.ad
  private String cid;//
  private String crid;//media.ad.id
  private Set<String> cat;//media.ad
  private List<Integer> attr;//media.ad
  // TODO  revert to String
  private String bundle;//media.ad
  // TODO revert to Integer
  private Integer api;//media.ad.display
  private Integer protocol;
  private Integer qagmediarating;
  private String dealid;//
  private Integer h;//media.ad.display.h
  private String language;//media.ad.lang
  private Integer w;//media.ad.display.w
  private Integer  wratio;//media.ad.display.wratio
  private Integer hratio;//media.ad.display.hratio
  private Integer exp;//
  private String burl;//
  private String lurl;//
  private String tactic;//
  private Map<String,Object> ext;

}
