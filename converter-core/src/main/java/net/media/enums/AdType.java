/*
 * Copyright © 2019 - present. MEDIA.NET ADVERTISING FZ-LLC
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

package net.media.enums;

/** Created by vishnu on 9/5/16. */
public enum AdType {
  BANNER(1),
  VIDEO(2),
  NATIVE(3),
  AUDIO(4),
  AUDIT(5);

  int value;

  AdType(int value) {
    this.value = value;
  }

  public static AdType getAdType(int value) {
    for (AdType adType : values()) {
      if (adType.getValue() == value) return adType;
    }
    throw new IllegalArgumentException("Invalid adtype value '" + value + "' provided");
  }

  public int getValue() {
    return this.value;
  }
}
