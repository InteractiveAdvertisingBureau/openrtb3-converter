package net.media.openrtb24.request;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import lombok.Data;


@Data
public class User {

  public String id;

  public String buyeruid;

  public Integer yob;

  public String gender;

  public Geo geo;

  private String keywords;

  private String customdata;

  private List<net.media.openrtb24.request.Data> data;

  private Map<String, Object> ext;

  private Integer age;

}
