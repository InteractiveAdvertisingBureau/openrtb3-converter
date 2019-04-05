package net.media.template;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author shiva.b
 */
public class MacroProcessor {
  public static final Pattern OPEN_RTB_TOKEN_PATTERN = Pattern.compile("\\$\\{(?<macro>.*?)(:(?<algo>.*?)){0,1}\\}");
  public static Template.TokenProvider TOKEN_PROVIDER = Template.getTokenProviderByGroupNames(Arrays
    .asList("macro", "algo"));
  private static EncodeTemplate.EncoderProvider ENCODER_PROVIDER = EncodeTemplate.getEncoderProviderByGroupName("algo");

  public static Template getOpenRtbMacroProcessor(String text) {
    if (Objects.isNull(text))
      return tokenValue -> "";
    return new EncodeTemplate(text, OPEN_RTB_TOKEN_PATTERN, TOKEN_PROVIDER, ENCODER_PROVIDER, token -> token.getTextValue());
  }

  public static final Template.TokenValue getTwoXToken() {
    return token -> {
      String macro = token.getGroup("macro");
      return MacroMapper.getTwoXMacro(MacroMapper.macroBuilder(macro));
    };
  }

  public static final Template.TokenValue getThreeXToken() {
    return token -> {
      String macro = token.getGroup("macro");
      return MacroMapper.getThreeXMacro(MacroMapper.macroBuilder(macro));
    };
  }

  public static final Template.TokenValue getBannerFields(net.media.openrtb3.Banner banner) {
    return token -> {
      String macro = token.getGroup("macro");
      switch (macro) {
        case "BANNER_URL":
          return banner.getImg();
        default:
          return "";
      }
    };
  }

}
