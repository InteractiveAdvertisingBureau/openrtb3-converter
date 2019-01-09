package net.media.api.models;

import lombok.Data;
import net.media.config.Config;
import net.media.openrtb24.request.BidRequest;

@Data
public class Request2xPayload {
  private BidRequest bidRequest;
  private Config config;
}
