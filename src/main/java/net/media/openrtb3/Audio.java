package net.media.openrtb3;

import lombok.Data;

import java.util.List;

@Data
public class Audio {

  private List<String> mime = null;
  private List<Integer> api = null;
  private Integer ctype;
  private Integer dur;
  private String adm;
  private String curl;
  private String ext;

}