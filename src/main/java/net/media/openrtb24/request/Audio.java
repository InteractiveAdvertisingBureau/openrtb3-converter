package net.media.openrtb24.request;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by rajat.go on 19/12/18.
 */

@Getter
@Setter
public class Audio {

  private List<String> mimes;

  private Integer minduration;

  private Integer maxduration;

  private List<Integer> protocols;

  private Integer startdelay;

  private Integer sequence;

  private List<Integer> battr;

  private Integer maxextended;

  private Integer minbitrate;

  private Integer maxbitrate;

  private List<Integer> delivery;

  private List<Banner> companionad;

  private List<Integer> api;

  private List<Integer> companiontype;

  private Integer maxseq;

  private Integer feed;

  private Integer stitched;

  private Integer nvol;

  private Map<String, Object> ext;

}
