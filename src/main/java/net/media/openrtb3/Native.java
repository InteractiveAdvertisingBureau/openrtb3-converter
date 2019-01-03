package net.media.openrtb3;

import lombok.Data;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;

@Data
public class Native {


  @Valid
  private LinkAsset link;
  @Valid
  private List<Asset> asset = null;
  private Map<String,Object> ext;

}