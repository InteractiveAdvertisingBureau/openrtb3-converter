package net.media.openrtb25.request;

import java.util.List;
import java.util.Map;

import lombok.Data;
import net.media.utils.validator.CheckAtLeastOneNotNull;


@Data
@CheckAtLeastOneNotNull(fieldNames = {"id", "buyeruid"})
public class User {

  public String id;

  public String buyeruid;

  public Integer yob;

  public String gender;

  public Geo geo;

  private String keywords;

  private String customdata;

  private List<net.media.openrtb25.request.Data> data;

  private Map<String, Object> ext;

  private Integer age;

}
