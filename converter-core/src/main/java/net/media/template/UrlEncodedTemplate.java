package net.media.template;

import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Created by shiva.b on 02/01/19.
 */
public class UrlEncodedTemplate extends EncodeTemplate {

  public UrlEncodedTemplate(String template, String placeHolderRegex,
                            TokenProvider tokenProvider,
                            EncoderProvider encoderProvider) {
    super(template, placeHolderRegex, tokenProvider, encoderProvider, token -> "");
  }

  public UrlEncodedTemplate(String template, Pattern pattern,
                            TokenProvider tokenProvider,
                            EncoderProvider encoderProvider) {
    super(template, pattern, tokenProvider, encoderProvider, token -> "");
  }


  public UrlEncodedTemplate(String template, Map<String,String> macros, Pattern pattern,
                            TokenProvider tokenProvider,
                            EncoderProvider encoderProvider) {
    super(template,  macros, pattern, tokenProvider, encoderProvider, token -> "");
  }

  @Override
  public String replace(TokenValue tokenValue, Function<Exception, Exception> exceptionSupplier) throws Exception {
    try {
      StringBuilder res = new StringBuilder(0);
      boolean appendedTail = false;
      for (Group group : groupPrefix) {
        String value = encoderProvider.getEncoder(group.token).apply(tokenValue.get(group.token));
        switch (value) {
          case "TRUE":
            throw new Exception();
          case "FALSE":
            continue;
          default:
            appendToURL(res, appendedTail, group, value);
            appendedTail=true;
        }
      }
      if(!appendedTail) res.append(tail);
      return res.toString();
    } catch (Exception e) {
      throw exceptionSupplier.apply(e);
    }
  }

  private void appendToURL(StringBuilder res, boolean appendedTail, Group group, String value) {
    if(group.prefix.startsWith("http://") || group.prefix.startsWith("https://"))
    {
      res.append(group.prefix).append(getMacroReplacement(value, group.token));
      res.append(tail);

    }
    else{
      if(!appendedTail)
        res.append(tail);
      res.append(group.prefix).append(getMacroReplacement(value, group.token));
    }
  }
}
