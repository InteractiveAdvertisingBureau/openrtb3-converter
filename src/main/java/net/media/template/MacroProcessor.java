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

  public static final Template.TokenValue getMowgliTokenValue(String string) {
    return token -> {
      String macro = token.getGroup("macro");
      switch (macro) {

        default:
          return "";
      }
    };
  }

  public static final Template.TokenValue getMowgliTokenValue(String str1, String str2) {
    Template.TokenValue openRTBTokenValue = getMowgliTokenValue("");

    return token -> {
      String macro = token.getGroup("macro");
      switch (macro) {

        default:
          return openRTBTokenValue.get(token);
      }
    };
  }

}
