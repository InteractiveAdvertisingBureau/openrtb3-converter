package net.media.openrtb3;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Native {

  private LinkAsset link; //Link
  private List<Asset> asset = null;
  private Map<String,Object> ext;

}