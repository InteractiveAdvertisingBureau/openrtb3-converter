package net.media.utils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

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
