package net.media.openrtb3;

import javax.validation.constraints.NotNull;

public abstract class DistributionChannel {
  @NotNull
  private String id;
  private String name;
  private Publisher pub;
  private Content content;

  public @NotNull String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public Publisher getPub() {
    return this.pub;
  }

  public Content getContent() {
    return this.content;
  }

  public void setId(@NotNull String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPub(Publisher pub) {
    this.pub = pub;
  }

  public void setContent(Content content) {
    this.content = content;
  }
}
