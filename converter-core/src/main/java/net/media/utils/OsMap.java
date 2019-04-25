/*
 * Copyright  2019 - present. MEDIA.NET ADVERTISING FZ-LLC
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

package net.media.utils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class OsMap {
  public static BiMap<String, Integer> osMap;

  static {
    osMap = HashBiMap.create();
    osMap.put("other not listed\n", 0);
    osMap.put("3ds system software", 1);
    osMap.put("android", 2);
    osMap.put("apple tv software", 3);
    osMap.put("asha", 4);
    osMap.put("bada", 5);
    osMap.put("blackberry", 6);
    osMap.put("brew", 7);
    osMap.put("chromeos", 8);
    osMap.put("darwin", 9);
    osMap.put("fireos", 10);
    osMap.put("firefoxos", 11);
    osMap.put("helenos", 12);
    osMap.put("ios", 13);
    osMap.put("linux", 14);
    osMap.put("macos", 15);
    osMap.put("meego", 16);
    osMap.put("morphos", 17);
    osMap.put("netbsd", 18);
    osMap.put("nucleusplus", 19);
    osMap.put("ps vita system software", 20);
    osMap.put("ps3 system software", 21);
    osMap.put("ps4 software", 22);
    osMap.put("psp system software", 23);
    osMap.put("symbian", 24);
    osMap.put("tizen", 25);
    osMap.put("watchos", 26);
    osMap.put("webos", 27);
    osMap.put("windows", 28);
  }
}
