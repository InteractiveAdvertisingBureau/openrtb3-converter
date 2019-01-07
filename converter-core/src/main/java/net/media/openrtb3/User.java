package net.media.openrtb3;

import lombok.Getter;
import lombok.Setter;
import net.media.utils.validator.CheckAtLeastOneNotNull;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@CheckAtLeastOneNotNull(fieldNames = {"id", "buyeruid"})
public class User {
  private String id;
  private String buyeruid;
  private Integer yob;
  private String gender;
  private String keywords;
  private String consent;
  private Geo geo;
  private List<Data> data;
  private Map<String, Object> ext;
}
