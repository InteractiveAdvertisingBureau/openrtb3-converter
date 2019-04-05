package net.media.openrtb3;


import java.util.Map;

import javax.validation.constraints.NotNull;

public class Macro {

  @NotNull
  private String key;
  private String value;
  private Map<String, Object> ext;

  public Macro() {
  }

  public @NotNull String getKey() {
    return this.key;
  }

  public Map<String, Object> getExt() {
    return ext;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public String getValue() {
    return this.value;
  }

  public void setKey(@NotNull String key) {
    this.key = key;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Macro)) return false;

    Macro macro = (Macro) o;

    if (getKey() != null ? !getKey().equals(macro.getKey()) : macro.getKey() != null) return false;
    if (getValue() != null ? !getValue().equals(macro.getValue()) : macro.getValue() != null)
      return false;
    return getExt() != null ? getExt().equals(macro.getExt()) : macro.getExt() == null;

  }

  @Override
  public int hashCode() {
    int result = getKey() != null ? getKey().hashCode() : 0;
    result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
    result = 31 * result + (getExt() != null ? getExt().hashCode() : 0);
    return result;
  }

  public void setValue(String value) {
    this.value = value;

  }

  @Override
  public String toString() {
    return "Macro{" +
      "key='" + key + '\'' +
      ", value='" + value + '\'' +
      ", ext=" + ext +
      '}';
  }

  protected boolean canEqual(Object other) {
    return other instanceof Macro;
  }
}