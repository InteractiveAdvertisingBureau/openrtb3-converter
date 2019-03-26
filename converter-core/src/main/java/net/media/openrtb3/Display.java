package net.media.openrtb3;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.media.utils.validator.CheckExactlyOneNotNull;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import lombok.*;

@lombok.Data
@CheckExactlyOneNotNull(fieldNames = {"adm", "banner", "_native", "curl"})
public class Display {

  private String mime;
  private List<Integer> api = null;//bid.api
  private Integer ctype;//bid.ext.ctype
  private Integer w;//bid.w
  private Integer h;//bid.h
  private Integer wratio;//bid.wratio
  private Integer hratio;//bid.hratio
  private String priv;//bid.ext.priv
  private Object adm;//bid.adm
  private String curl;//bid.ext.curl
  @Valid
  private Banner banner;//
  @Valid
  @JsonProperty("native")
  private Native _native;
  @Valid
  private List<Event> event = null;
  private Map<String,Object> ext;
}
