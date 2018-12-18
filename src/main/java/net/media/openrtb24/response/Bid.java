package net.media.openrtb24.response;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

  private String id;//
  private String impid;//
  private double price;//
  private String adid;
  private String nurl;//bid.purl
  private Object adm;//media.ad.(display.adm/audio.adm/video.adm)
  private List<String> adomain;//media.ad
  private String bundle;//media.ad
  private String iurl;//media.ad
  private String cid;//
  private String crid;//media.ad.id
  private Set<String> cat;//media.ad
  private List<Integer> attr;//media.ad
  private Integer api;
  private Integer protocol;
  private Integer qagmediarating;
  private String dealid;//
  private Integer h;//media.ad.display.h
  private String language;//media.ad.lang
  private Integer w;//media.ad.display.w
  private Integer exp;//
  private Map<String,Object> ext;

}
