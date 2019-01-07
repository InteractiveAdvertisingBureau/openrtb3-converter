package net.media.openrtb3;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Video {

  private List<String> mime = null;
  private List<Integer> api = null;//bid.api
  private Integer ctype;//bid.ext.ctype
  private Integer dur;//bid.ext.dur
  private Object adm;//bid.adm
  private String curl;//bid.ext.curl
  private Map<String,Object> ext;
}