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

public class CommonConstants {

  public static final Integer DEFAULT_CATTAX_THREEDOTX = 2;
  public static final Integer DEFAULT_CATTAX_TWODOTX = 1;

  public static final String DELAY = "delay";
  public static final String RESTRICTIONS = "restrictions";
  public static final String EXT = "ext";
  public static final String ARTIST = "artist";
  public static final String GENRE = "genre";
  public static final String ALBUM = "album";
  public static final String ISRC = "isrc";
  public static final String PRODQ = "prodq";
  public static final String DATA = "data";
  public static final String GEOFETCH = "geofetch";
  public static final String FORMAT = "format";
  public static final String ACCURACY = "accuracy";
  public static final String LASTFIX = "lastfix";
  public static final String IPSERVICE = "ipservice";
  public static final String MCCMNC = "mccmnc";
  public static final String VCM = "vcm";
  public static final String METRIC = "metric";
  public static final String BSEAT = "bseat";
  public static final String WLANG = "wlang";
  public static final String SOURCE = "source";
  public static final String PLACEMENT = "placement";
  public static final String STOREID = "storeid";
  public static final String STITCHED = "stitched";
  public static final String PLAYMETHOD = "playmethod";
  public static final String PLAYEND = "playend";
  public static final String VIDEOQUALITY = "videoquality";
  public static final String FLASHVER = "flashver";
  public static final String XFF = "xff";
  public static final String IPTR = "iptr";
  public static final String MCCMNCSIM = "mccmncsim";
  public static final String WMIN = "wmin";
  public static final String CONTEXT = "context";
  public static final String HMIN = "hmin";
  public static final String WMAX = "wmax";
  public static final String HMAX = "hmax";
  public static final String ID = "id";
  public static final String BTYPE = "btype";
  public static final String REGIONFIPS_104 = "regionfips104";
  public static final String QTY = "qty";
  public static final String SEQ = "seq";
  public static final String PMP = "pmp";
  public static final String DT = "dt";
  public static final String DLVY = "dlvy";
  public static final String SSAI = "ssai";
  public static final String REWARD = "reward";
  public static final String ADMX = "admx";
  public static final String CURLX = "curlx";
  public static final String AMPREN = "ampren";
  public static final String CONTEXTSUBTYPE = "contextsubtype";
  public static final String ADUNIT = "adunit";
  public static final String LAYOUT = "layout";
  public static final String VER = "ver";
  public static final String GDPR = "gdpr";
  public static final String DOOH = "dooh";
  public static final String AMP = "amp";
  public static final String TS = "ts";
  public static final String DS = "ds";
  public static final String DSMAP = "dsmap";
  public static final String CERT = "cert";
  public static final String DIGEST = "digest";
  public static final String FD = "fd";
  public static final String CONSENT = "consent";
  public static final String CORR = "corr";
  public static final String STATUS = "status";
  public static final String FEEDBACK = "feedback";
  public static final String ADID = "adid";
  public static final String QAGMEDIARATING = "qagmediarating";
  public static final String EXP = "exp";
  public static final String BURL = "burl";
  public static final String LURL = "lurl";
  public static final String TACTIC = "tactic";
  public static final String LANGUAGE = "language";
  public static final String SECURE = "secure";
  public static final String INIT = "init";
  public static final String LASTMOD = "lastmod";
  public static final String CATTAX = "cattax";
  public static final String AUDIT = "audit";
  public static final String LABEL = "label";
  public static final String TYPE = "type";
  public static final String LEN = "len";
  public static final String PROTOCOL = "protocol";
  public static final String MACRO = "macro";
  public static final String PRIV = "priv";
  public static final String EVENT = "event";
  public static final String NATIVE = "native";
  public static final String BANNER = "banner";
  public static final String JS_TRACKER = "jsTracker";
  public static final String IMP_TRACKERS = "impTrackers";
  public static final String CTYPE = "ctype";
  public static final String DUR = "dur";
  public static final String CURL = "curl";
  public static final String MIME = "mime";
  public static final String CLICKBROWSER = "clickbrowser";
  public static final String COMPANIONAD = "companionad";
  public static final String HRATIO = "hratio";
  public static final String WRATIO = "wratio";
  public static final String BOXINGALLOWED = "boxingallowed";
  public static final String PTYPE = "ptype";
  public static final String POS = "pos";
  public static final String STARTDELAY = "startdelay";
  public static final String SKIP = "skip";
  public static final String SKIPMIN = "skipmin";
  public static final String SKIPAFTER = "skipafter";
  public static final String PLAYBACKMETHOD = "playbackmethod";
  public static final String PLAYBACKEND = "playbackend";
  public static final String API = "api";
  public static final String W = "w";
  public static final String H = "h";
  public static final String UNIT = "unit";
  public static final String MAXEXTENDED = "maxextended";
  public static final String MINBITRATE = "minbitrate";
  public static final String MAXBITRATE = "maxbitrate";
  public static final String DELIVERY = "delivery";
  public static final String MAXSEQ = "maxseq";
  public static final String LINEARITY = "linearity";
  public static final String COMPANIONTYPE = "companiontype";
  public static final String BAPP = "bapp";

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
