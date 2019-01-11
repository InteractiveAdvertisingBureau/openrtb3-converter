package net.media.api.models;

import lombok.Data;
import net.media.config.Config;
import net.media.openrtb3.OpenRTB;

@Data
public class RequestResponse3xPayload {
  private OpenRTB openRTB;
  private Config config;
}
