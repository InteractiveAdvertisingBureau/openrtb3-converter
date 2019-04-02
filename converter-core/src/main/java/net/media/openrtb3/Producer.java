package net.media.openrtb3;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import static net.media.utils.CommonConstants.CATTAX_VERSION_THREEDOTX;

public class Producer {
  @NotNull
  private String id;
  private String name;
  private String domain;
  private Collection<String> cat;
  private Integer cattax = CATTAX_VERSION_THREEDOTX;
  private Map<String, Object> ext;

  public @NotNull String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getDomain() {
    return this.domain;
  }

  public Collection<String> getCat() {
    return this.cat;
  }

  public Integer getCattax() {
    return this.cattax;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setId(@NotNull String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public void setCat(Collection<String> cat) {
    this.cat = cat;
  }

  public void setCattax(Integer cattax) {
    this.cattax = cattax;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
