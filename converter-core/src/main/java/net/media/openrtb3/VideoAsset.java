package net.media.openrtb3;

import net.media.utils.validator.CheckExactlyOneNotNull;
import lombok.Data;
import java.util.Map;

@Data
@CheckExactlyOneNotNull(fieldNames = {"adm", "curl"})
public class VideoAsset {

  private String adm;
  private String curl;
  private Map<String,Object> ext;

}