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
import net.media.openrtb25.response.BidResponse2_X;

public class Response2xPayload {
  private BidResponse2_X response;
  private Config config;

  public Response2xPayload() {}

  public BidResponse2_X getResponse() {
    return this.response;
  }

  public void setResponse(BidResponse2_X response) {
    this.response = response;
  }

  public Config getConfig() {
    return this.config;
  }

  public void setConfig(Config config) {
    this.config = config;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Response2xPayload)) return false;
    final Response2xPayload other = (Response2xPayload) o;
    if (!other.canEqual(this)) return false;
    final Object this$response = this.getResponse();
    final Object other$response = other.getResponse();
    if (this$response == null ? other$response != null : !this$response.equals(other$response))
      return false;
    final Object this$config = this.getConfig();
    final Object other$config = other.getConfig();
    if (this$config == null ? other$config != null : !this$config.equals(other$config))
      return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $response = this.getResponse();
    result = result * PRIME + ($response == null ? 43 : $response.hashCode());
    final Object $config = this.getConfig();
    result = result * PRIME + ($config == null ? 43 : $config.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Response2xPayload;
  }

  public String toString() {
    return "net.media.api.models.Response2xPayload(response="
        + this.getResponse()
        + ", config="
        + this.getConfig()
        + ")";
  }
}
