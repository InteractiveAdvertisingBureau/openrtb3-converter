package net.media.openrtb24.request;

import java.util.Calendar;
import java.util.List;

import lombok.Data;

import static java.util.Objects.nonNull;

@Data
public class User extends AbstractExtensible<User.UserReqExt> {

  public String id;

  public String buyeruid;

  public Integer yob;

  public String gender;

  public Geo geo;

  private String keywords;

  private String customdata;

  private List<Data> data;

  private Ext ext;

  private Integer age;


  public User() {
    setReqExt(new UserReqExt());
  }

  public void setYob(Integer yob) {
    this.yob = yob;

    this.age = nonNull(yob)?Calendar.getInstance().get(Calendar.YEAR) - yob:null;
  }

  public static class UserReqExt extends ReqExt {
  }
}
