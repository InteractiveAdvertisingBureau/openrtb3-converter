package net.media.openrtb24.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class App extends AbstractExtensible<App.AppReqExt>{

  private String id;

  private String name;

  private String bundle;

  private String domain;

  private String storeurl;

  private List<String> cat;

  private List<String> sectioncat;

  private Set<String> pagecat;

  private String ver;

  private Integer privacypolicy;

  private Integer paid;

  private Publisher publisher;

  private Content content;

  private String keywords;

  private Object ext;

  @JsonIgnore
  private String SLD;

  public App(){
    setReqExt(new AppReqExt());
  }

  public static class AppReqExt extends ReqExt{
  }
}
