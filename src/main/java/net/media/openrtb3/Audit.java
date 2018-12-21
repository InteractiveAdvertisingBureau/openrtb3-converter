package net.media.openrtb3;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Audit {

  private Integer status;
  private List<String> feedback = null;
  private Integer init;
  private Integer lastmod;
  private Corr corr;
  private Map<String,Object> ext;

}