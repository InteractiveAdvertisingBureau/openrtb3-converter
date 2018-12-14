package net.media.openrtb24.response.nativeresponse;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * Created by rajat.go on 06/12/17.
 */

@Data
public class NativeResponseBody {

  private static final String DEFAULT_NATIVE_VER = "1.1";

  private String ver;

  private List<AssetResponse> assets;

  private Link link;

  private List<String> imptrackers;

  private String jstracker;

  private Map<String, Object> ext;
}
