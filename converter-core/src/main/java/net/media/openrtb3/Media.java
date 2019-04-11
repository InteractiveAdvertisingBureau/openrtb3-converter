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

package net.media.openrtb3;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class Media {

  @NotNull @Valid private Ad ad;

  public Media() {}

  public @NotNull @Valid Ad getAd() {
    return this.ad;
  }

  public void setAd(@NotNull @Valid Ad ad) {
    this.ad = ad;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Media)) return false;
    final Media other = (Media) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$ad = this.getAd();
    final Object other$ad = other.getAd();
    if (this$ad == null ? other$ad != null : !this$ad.equals(other$ad)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $ad = this.getAd();
    result = result * PRIME + ($ad == null ? 43 : $ad.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Media;
  }

  public String toString() {
    return "net.media.openrtb3.Media(ad=" + this.getAd() + ")";
  }
}
