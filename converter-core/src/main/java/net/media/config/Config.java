/*
 * Copyright  2019 - present. IAB Tech Lab
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

package net.media.config;

import net.media.enums.AdType;
import net.media.enums.OpenRtbVersion;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Provides a handle to read configuration from several data formats.
 *
 * @author shiva.b
 */
public class Config {

  private static final boolean DEFAULT_NATIVE_REQUEST_AS_STRING = true;

  private static final boolean DEFAULT_NATIVE_RESPONSE_AS_STRING = false;

  private static final boolean DEFAULT_DISABLE_CLONING = false;

  private static final boolean DEFAULT_VALIDATE = true;

  private static final AdType DEFAULT_AD_TYPE = AdType.BANNER;

  private static final OpenRtbVersion DEFAULT_OPENRTB_2_X_VERSION = OpenRtbVersion.TWO_DOT_FIVE;

  /**
   * Determines the type of native request in 2.x while converting from 3.x to 2.x. Note: Native
   * request can be an object as well as a string in 2.x.
   */
  private Boolean nativeRequestAsString;

  /**
   * Determines the type of native response in 2.x while converting from 3.x to 2.x. Note: Native
   * response ADM can be an object as well as a string in 2.x.
   */
  private Boolean nativeResponseAsString;

  /**
   * Used for converting 2.x response to 3.0 response, and vice versa. Stores the {@link AdType}
   * against impression ids which will aid in conversion. Example: Say, the map has the following
   * mapping:
   *
   * <p>
   *
   * <pre>
   *   {imp1, BANNER}
   *   {imp2, VIDEO}
   *   {imp3, NATIVE}
   * </pre>
   *
   * <p>If in 2.x response, bidResponse.seatBid.bid.impId = imp1, then when converting to 3.0, this
   * impression will be converted to {@link net.media.openrtb3.Banner} object. Aditionally, if in
   * 3.0 response, openrtb.response.seatbid.bid.media.ad.display.banner is not null, then an
   * appropriate bidResponse.seatBid.bid will be created with impId = imp1. Note that, support for
   * multiple ad types for a single impression is not supported.
   */
  private Map<String, AdType> adTypeMapping;

  /** Whether to clone object references or not while conversion. Only used for collections. */
  private Boolean disableCloning;

  /** Determines whether the input request or response needs to be v alidated or not. */
  private Boolean validate;

  /**
   * Determines the minor version in 2.x spec. By default, it is set to {@link
   * OpenRtbVersion#TWO_DOT_FIVE}, but can be overridden
   */
  private OpenRtbVersion openRtbVersion2_XVersion;

  /**
   * For internal use when no overriding config to drive the conversion is found.
   *
   * @param oldConfig the last set / updated config
   */
  public Config(Config oldConfig) {
    if (nonNull(oldConfig)) {
      this.nativeRequestAsString = oldConfig.nativeRequestAsString;
      this.nativeResponseAsString = oldConfig.nativeResponseAsString;
      this.adTypeMapping = oldConfig.adTypeMapping;
      this.validate = oldConfig.validate;
      this.disableCloning = oldConfig.disableCloning;
      this.openRtbVersion2_XVersion = oldConfig.openRtbVersion2_XVersion;
    }
  }

  public Config() {}

  /**
   * Creates config object from JSON string.
   *
   * @param content of config
   * @return config see {@link Config}
   * @throws IOException when reading fails
   */
  public static Config fromJSON(String content) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromJSON(content, Config.class);
  }

  /**
   * Creates config object from input stream. The input stream must be drawn from JSON String.
   *
   * @param inputStream see appropriate implementation of {@link InputStream}
   * @return config see {@link Config}
   * @throws IOException when reading fails
   */
  public static Config fromJSON(InputStream inputStream) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromJSON(inputStream, Config.class);
  }

  /**
   * Creates config object from JSON string stored in the file using the input {@link ClassLoader}.
   *
   * @param file object see {@link File}
   * @param classLoader see {@link ClassLoader}
   * @return config see {@link Config}
   * @throws IOException when reading fails
   */
  public static Config fromJSON(File file, ClassLoader classLoader) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromJSON(file, Config.class, classLoader);
  }

  /**
   * Creates config object from JSON string stored in the file.
   *
   * @param file object see {@link File}
   * @return config see {@link Config}
   * @throws IOException when reading fails
   */
  public static Config fromJSON(File file) throws IOException {
    return fromJSON(file, null);
  }

  /**
   * Creates config object from JSON string that will be fetched from the {@link URL}.
   *
   * @param url object see {@link URL}
   * @return config see {@link Config}
   * @throws IOException when reading fails
   */
  public static Config fromJSON(URL url) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromJSON(url, Config.class);
  }

  /**
   * Creates config object from JSON String via {@link Reader}.
   *
   * @param reader see appropriate implementation of {@link Reader}
   * @return config see {@link Config}
   * @throws IOException when reading fails
   */
  public static Config fromJSON(Reader reader) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromJSON(reader, Config.class);
  }

  /**
   * Creates config object from YAML string.
   *
   * @param content YAML string
   * @return config see {@link Config}
   * @throws IOException when reading fails
   */
  public static Config fromYAML(String content) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromYAML(content, Config.class);
  }

  /**
   * Creates config object from input stream. The input stream must be drawn from YAML String.
   *
   * @param inputStream see appropriate implementation of {@link InputStream}
   * @return config see {@link Config}
   * @throws IOException when reading fails
   */
  public static Config fromYAML(InputStream inputStream) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromYAML(inputStream, Config.class);
  }

  /**
   * Creates config object from YAML string stored in the file.
   *
   * @param file object see {@link File}
   * @return config see {@link Config}
   * @throws IOException when reading fails
   */
  public static Config fromYAML(File file) throws IOException {
    return fromYAML(file, null);
  }

  /**
   * Creates config object from YAML string stored in the file using the input {@link ClassLoader}.
   *
   * @param file object see {@link File}
   * @param classLoader see {@link ClassLoader}
   * @return config see {@link Config}
   * @throws IOException when reading fails
   */
  public static Config fromYAML(File file, ClassLoader classLoader) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromYAML(file, Config.class, classLoader);
  }

  /**
   * Creates config object from YAML string that will be fetched from the {@link URL}.
   *
   * @param url object see {@link URL}
   * @return config see {@link Config}
   * @throws IOException when reading fails
   */
  public static Config fromYAML(URL url) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromYAML(url, Config.class);
  }

  /**
   * Creates config object from YAML String via {@link Reader}.
   *
   * @param reader see appropriate implementation of {@link Reader}
   * @return config see {@link Config}
   * @throws IOException when reading fails
   */
  public static Config fromYAML(Reader reader) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromYAML(reader, Config.class);
  }

  /**
   * Sets the fields that are not present in the conversion request.
   *
   * @param config see {@link Config}
   */
  public void updateEmptyFields(Config config) {
    if (nonNull(config)) {
      this.nativeRequestAsString =
          isNull(this.nativeRequestAsString)
              ? config.nativeRequestAsString
              : this.nativeRequestAsString;

      this.nativeResponseAsString =
        isNull(this.nativeResponseAsString)
          ? config.nativeResponseAsString
          : this.nativeResponseAsString;

      this.adTypeMapping = isNull(this.adTypeMapping) ? config.adTypeMapping : this.adTypeMapping;
      this.validate = isNull(this.validate) ? config.validate : this.validate;
      this.disableCloning =
          isNull(this.disableCloning) ? config.disableCloning : this.disableCloning;
      this.openRtbVersion2_XVersion =
          isNull(this.openRtbVersion2_XVersion)
              ? config.openRtbVersion2_XVersion
              : this.openRtbVersion2_XVersion;
    }
  }

  public Boolean getNativeRequestAsString() {
    return nonNull(nativeRequestAsString)
        ? nativeRequestAsString
        : DEFAULT_NATIVE_REQUEST_AS_STRING;
  }

  public void setNativeRequestAsString(Boolean nativeRequestAsString) {
    this.nativeRequestAsString = nativeRequestAsString;
  }

  public Boolean getNativeResponseAsString() {
    return nonNull(nativeResponseAsString)
        ? nativeResponseAsString
        : DEFAULT_NATIVE_RESPONSE_AS_STRING;
  }

  public void setNativeResponseAsString(Boolean nativeResponseAsString) {
    this.nativeResponseAsString = nativeResponseAsString;
  }

  public Boolean getValidate() {
    return nonNull(validate) ? validate : DEFAULT_VALIDATE;
  }

  public void setValidate(Boolean validate) {
    this.validate = validate;
  }

  public Boolean isCloningDisabled() {
    return nonNull(disableCloning) ? disableCloning : DEFAULT_DISABLE_CLONING;
  }

  public OpenRtbVersion getOpenRtbVersion2_XVersion() {
    return nonNull(openRtbVersion2_XVersion) ? openRtbVersion2_XVersion :
      DEFAULT_OPENRTB_2_X_VERSION;
  }

  public void setOpenRtbVersion2_XVersion(OpenRtbVersion openRtbVersion2_XVersion) {
    this.openRtbVersion2_XVersion = openRtbVersion2_XVersion;
  }

  /**
   * Converts current configuration to JSON format
   *
   * @return config in json format
   * @throws IOException when conversion fails
   */
  public String toJSON() throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.toJSON(this);
  }

  /**
   * Converts current configuration to YAML format
   *
   * @return config in YAML format
   * @throws IOException when conversion fails
   */
  public String toYAML() throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.toYAML(this);
  }

  public AdType getAdType(String impressionId) {
    if (isNull(adTypeMapping) || !adTypeMapping.containsKey(impressionId)) {
      return DEFAULT_AD_TYPE;
    }
    return adTypeMapping.get(impressionId);
  }

  public Boolean getDisableCloning() {
    return this.disableCloning;
  }

  public void setDisableCloning(Boolean disableCloning) {
    this.disableCloning = disableCloning;
  }

  public void setAdTypeMapping(Map<String, AdType> adTypeMapping) {
    this.adTypeMapping = adTypeMapping;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Config;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Config)) return false;

    Config config = (Config) o;

    if (getNativeRequestAsString() != null
        ? !getNativeRequestAsString().equals(config.getNativeRequestAsString())
        : config.getNativeRequestAsString() != null) return false;
    if (getNativeResponseAsString() != null
        ? !getNativeResponseAsString().equals(config.getNativeResponseAsString())
        : config.getNativeResponseAsString() != null) return false;
    if (adTypeMapping != null
        ? !adTypeMapping.equals(config.adTypeMapping)
        : config.adTypeMapping != null) return false;
    if (getDisableCloning() != null
        ? !getDisableCloning().equals(config.getDisableCloning())
        : config.getDisableCloning() != null) return false;
    if (getValidate() != null
        ? !getValidate().equals(config.getValidate())
        : config.getValidate() != null) return false;
    return getOpenRtbVersion2_XVersion() == config.getOpenRtbVersion2_XVersion();
  }

  @Override
  public int hashCode() {
    int result = getNativeRequestAsString() != null ? getNativeRequestAsString().hashCode() : 0;
    result =
        31 * result
            + (getNativeResponseAsString() != null ? getNativeResponseAsString().hashCode() : 0);
    result = 31 * result + (adTypeMapping != null ? adTypeMapping.hashCode() : 0);
    result = 31 * result + (getDisableCloning() != null ? getDisableCloning().hashCode() : 0);
    result = 31 * result + (getValidate() != null ? getValidate().hashCode() : 0);
    result =
        31 * result
            + (getOpenRtbVersion2_XVersion() != null
                ? getOpenRtbVersion2_XVersion().hashCode()
                : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Config{"
        + "nativeRequestAsString="
        + nativeRequestAsString
        + ", nativeResponseAsString="
        + nativeResponseAsString
        + ", adTypeMapping="
        + adTypeMapping
        + ", disableCloning="
        + disableCloning
        + ", validate="
        + validate
        + ", openRtbVersion2_XVersion="
        + openRtbVersion2_XVersion
        + '}';
  }
}
