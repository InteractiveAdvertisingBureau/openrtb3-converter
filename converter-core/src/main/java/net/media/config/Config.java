package net.media.config;

import com.google.common.base.Strings;

import net.media.enums.AdType;
import net.media.enums.OpenRtbVersion;
import net.media.utils.PropertiesFileLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * ORTB converter configuration
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
   * This config determines the type of native request from 3.x to 2.x,
   * as native request can be an object as well as a string in 2.x
   */
  private Boolean nativeRequestAsString;

  private Boolean nativeResponseAsString;

  /**
   * {@link AdType} provides the adType for response conversion
   */
  private Map<String, AdType> adTypeMapping;

  private Boolean disableCloning;
  /**
   * This config determines whether the input request or response needs to be validated
   */
  private Boolean validate;

  private OpenRtbVersion openRtbVersion2_XVersion;

  public Config(Config oldConfig) {
    if (nonNull(oldConfig)) {
      this.nativeRequestAsString = oldConfig.nativeRequestAsString;
      this.adTypeMapping = oldConfig.adTypeMapping;
      this.validate = oldConfig.validate;
      this.disableCloning = oldConfig.disableCloning;
      this.openRtbVersion2_XVersion = oldConfig.openRtbVersion2_XVersion;
    }
  }

  public Config() {
  }

  /**
   * fills the fields that are not present in the conversion request
   *
   * @param config
   */
  public void updateEmptyFields(Config config) {
    this.nativeRequestAsString = isNull(this.nativeRequestAsString) ? config
      .nativeRequestAsString : this.nativeRequestAsString;
    this.adTypeMapping = isNull(this.adTypeMapping) ? config.adTypeMapping : this.adTypeMapping;
    this.validate = isNull(this.validate) ? config.validate : this.validate;
    this.disableCloning = isNull(this.disableCloning) ? config.getDisableCloning() : this
      .disableCloning;
    this.openRtbVersion2_XVersion = isNull(this.openRtbVersion2_XVersion) ? config
      .getOpenRtbVersion2_XVersion() : this.openRtbVersion2_XVersion;
  }

  public Boolean getNativeRequestAsString() {
    return nonNull(nativeRequestAsString) ? nativeRequestAsString :
      DEFAULT_NATIVE_REQUEST_AS_STRING;
  }

  public Boolean getNativeResponseAsString() {
    return nonNull(nativeResponseAsString) ? nativeResponseAsString :
      DEFAULT_NATIVE_RESPONSE_AS_STRING;
  }

  public Boolean getValidate() {
    return nonNull(validate) ? validate : DEFAULT_VALIDATE;
  }

  public Boolean isCloningDisabled() {
    return nonNull(disableCloning) ? disableCloning : DEFAULT_DISABLE_CLONING;
  }

  public OpenRtbVersion getOpenRtbVersion2_XVersion() {
    return nonNull(openRtbVersion2_XVersion) ? openRtbVersion2_XVersion :
      DEFAULT_OPENRTB_2_X_VERSION;
  }

  /**
   * Read config object stored in JSON format from <code>String</code>
   *
   * @param content of config
   * @return config
   * @throws IOException error
   */
  public static Config fromJSON(String content) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromJSON(content, Config.class);
  }

  /**
   * Read config object stored in JSON format from <code>InputStream</code>
   *
   * @param inputStream object
   * @return config
   * @throws IOException error
   */
  public static Config fromJSON(InputStream inputStream) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromJSON(inputStream, Config.class);
  }

  /**
   * Read config object stored in JSON format from <code>File</code>
   *
   * @param file object
   * @param classLoader class loader
   * @return config
   * @throws IOException error
   */
  public static Config fromJSON(File file, ClassLoader classLoader) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromJSON(file, Config.class, classLoader);
  }

  /**
   * Read config object stored in JSON format from <code>File</code>
   *
   * @param file object
   * @return config
   * @throws IOException error
   */
  public static Config fromJSON(File file) throws IOException {
    return fromJSON(file, null);
  }

  /**
   * Read config object stored in JSON format from <code>URL</code>
   *
   * @param url object
   * @return config
   * @throws IOException error
   */
  public static Config fromJSON(URL url) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromJSON(url, Config.class);
  }

  /**
   * Read config object stored in JSON format from <code>Reader</code>
   *
   * @param reader object
   * @return config
   * @throws IOException error
   */
  public static Config fromJSON(Reader reader) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromJSON(reader, Config.class);
  }

  /**
   * Convert current configuration to JSON format
   *
   * @return config in json format
   * @throws IOException error
   */
  public String toJSON() throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.toJSON(this);
  }

  /**
   * Read config object stored in YAML format from <code>String</code>
   *
   * @param content of config
   * @return config
   * @throws IOException error
   */
  public static Config fromYAML(String content) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromYAML(content, Config.class);
  }

  /**
   * Read config object stored in YAML format from <code>InputStream</code>
   *
   * @param inputStream object
   * @return config
   * @throws IOException error
   */
  public static Config fromYAML(InputStream inputStream) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromYAML(inputStream, Config.class);
  }

  /**
   * Read config object stored in YAML format from <code>File</code>
   *
   * @param file object
   * @return config
   * @throws IOException error
   */
  public static Config fromYAML(File file) throws IOException {
    return fromYAML(file, null);
  }

  public static Config fromYAML(File file, ClassLoader classLoader) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromYAML(file, Config.class, classLoader);
  }

  /**
   * Read config object stored in YAML format from <code>URL</code>
   *
   * @param url object
   * @return config
   * @throws IOException error
   */
  public static Config fromYAML(URL url) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromYAML(url, Config.class);
  }

  /**
   * Read config object stored in YAML format from <code>Reader</code>
   *
   * @param reader object
   * @return config
   * @throws IOException error
   */
  public static Config fromYAML(Reader reader) throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.fromYAML(reader, Config.class);
  }

  /**
   * Convert current configuration to YAML format
   *
   * @return config in yaml format
   * @throws IOException error
   */
  public String toYAML() throws IOException {
    ConfigSupport support = new ConfigSupport();
    return support.toYAML(this);
  }

  public AdType  getAdType(String impressionId) {
    if (isNull(adTypeMapping) || !adTypeMapping.containsKey(impressionId)) {
      return DEFAULT_AD_TYPE;
    }
    return adTypeMapping.get(impressionId);
  }

  public Boolean getDisableCloning() {
    return this.disableCloning;
  }

  public void setNativeRequestAsString(Boolean nativeRequestAsString) {
    this.nativeRequestAsString = nativeRequestAsString;
  }

  public void setNativeResponseAsString(Boolean nativeResponseAsString) {
    this.nativeResponseAsString = nativeResponseAsString;
  }

  public void setAdTypeMapping(Map<String, AdType> adTypeMapping) {
    this.adTypeMapping = adTypeMapping;
  }

  public void setDisableCloning(Boolean disableCloning) {
    this.disableCloning = disableCloning;
  }

  public void setValidate(Boolean validate) {
    this.validate = validate;
  }

  public void setOpenRtbVersion2_XVersion(OpenRtbVersion openRtbVersion2_XVersion) {
    this.openRtbVersion2_XVersion = openRtbVersion2_XVersion;
  }


  protected boolean canEqual(Object other) {
    return other instanceof Config;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Config)) return false;
    final Config other = (Config) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$nativeRequestAsString = this.getNativeRequestAsString();
    final Object other$nativeRequestAsString = other.getNativeRequestAsString();
    if (this$nativeRequestAsString == null ? other$nativeRequestAsString != null : !this$nativeRequestAsString.equals(other$nativeRequestAsString))
      return false;
    final Object this$nativeResponseAsString = this.getNativeResponseAsString();
    final Object other$nativeResponseAsString = other.getNativeResponseAsString();
    if (this$nativeResponseAsString == null ? other$nativeResponseAsString != null : !this$nativeResponseAsString.equals(other$nativeResponseAsString))
      return false;
    final Object this$adTypeMapping = this.adTypeMapping;
    final Object other$adTypeMapping = other.adTypeMapping;
    if (this$adTypeMapping == null ? other$adTypeMapping != null : !this$adTypeMapping.equals(other$adTypeMapping))
      return false;
    final Object this$disableCloning = this.getDisableCloning();
    final Object other$disableCloning = other.getDisableCloning();
    if (this$disableCloning == null ? other$disableCloning != null : !this$disableCloning.equals(other$disableCloning))
      return false;
    final Object this$validate = this.getValidate();
    final Object other$validate = other.getValidate();
    if (this$validate == null ? other$validate != null : !this$validate.equals(other$validate))
      return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $nativeRequestAsString = this.getNativeRequestAsString();
    result = result * PRIME + ($nativeRequestAsString == null ? 43 : $nativeRequestAsString.hashCode());
    final Object $nativeResponseAsString = this.getNativeResponseAsString();
    result = result * PRIME + ($nativeResponseAsString == null ? 43 : $nativeResponseAsString.hashCode());
    final Object $adTypeMapping = this.adTypeMapping;
    result = result * PRIME + ($adTypeMapping == null ? 43 : $adTypeMapping.hashCode());
    final Object $disableCloning = this.getDisableCloning();
    result = result * PRIME + ($disableCloning == null ? 43 : $disableCloning.hashCode());
    final Object $validate = this.getValidate();
    result = result * PRIME + ($validate == null ? 43 : $validate.hashCode());
    return result;
  }

  public String toString() {
    return "net.media.config.Config(nativeRequestAsString=" + this.getNativeRequestAsString() + ", nativeResponseAsString=" + this.getNativeResponseAsString() + ", adTypeMapping=" + this.adTypeMapping + ", disableCloning=" + this.getDisableCloning() + ", validate=" + this.getValidate() + ")";
  }
}
