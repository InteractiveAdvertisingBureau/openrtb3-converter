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

  private String id;
  private String impid;
  private double price;
  private String adid;
  private String nurl;
  private Object adm;
  private List<String> adomain;
  private String bundle;
  private String iurl;
  private String cid;
  private String crid;
  private Set<String> cat;
  private List<Integer> attr;
  private Integer api;
  private Integer protocol;
  private Integer qagmediarating;
  private String dealid;
  private Integer h;
  private Integer w;
  private Integer exp;
  private Map ext;

}
