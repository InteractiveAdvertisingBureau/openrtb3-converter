package net.media.api.models;

import lombok.Data;
import net.media.config.Config;
import net.media.openrtb24.response.BidResponse;

@Data
public class Response2xPayload {
  private BidResponse response;
  private Config config;
}
