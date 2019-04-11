/*
 * Copyright © 2019 - present. MEDIA NET SOFTWARE SERVICES PVT. LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.media.api.models;

import net.media.config.Config;
import net.media.openrtb25.request.BidRequest2_X;

public class Request2xPayload {
  private BidRequest2_X bidRequest;
  private Config config;

  public Request2xPayload() {}

  public BidRequest2_X getBidRequest() {
    return this.bidRequest;
  }

  public void setBidRequest(BidRequest2_X bidRequest) {
    this.bidRequest = bidRequest;
  }

  public Config getConfig() {
    return this.config;
  }

  public void setConfig(Config config) {
    this.config = config;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Request2xPayload)) return false;
    final Request2xPayload other = (Request2xPayload) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$bidRequest = this.getBidRequest();
    final Object other$bidRequest = other.getBidRequest();
    if (this$bidRequest == null
        ? other$bidRequest != null
        : !this$bidRequest.equals(other$bidRequest)) return false;
    final Object this$config = this.getConfig();
    final Object other$config = other.getConfig();
    if (this$config == null ? other$config != null : !this$config.equals(other$config))
      return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $bidRequest = this.getBidRequest();
    result = result * PRIME + ($bidRequest == null ? 43 : $bidRequest.hashCode());
    final Object $config = this.getConfig();
    result = result * PRIME + ($config == null ? 43 : $config.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Request2xPayload;
  }

  public String toString() {
    return "net.media.api.models.Request2xPayload(bidRequest="
        + this.getBidRequest()
        + ", config="
        + this.getConfig()
        + ")";
  }
}
