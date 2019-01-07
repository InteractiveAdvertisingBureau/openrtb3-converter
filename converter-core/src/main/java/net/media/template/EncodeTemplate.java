package net.media.template;

import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Created by shiva.b on 02/01/19.
 */
public class EncodeTemplate extends SimpleTemplate {

  EncoderProvider encoderProvider;

//  Default values arn't encoded as the user might want to enter the specific text they want in such a senario.
//  private static Template.DefaultValueProvider getEncodedDefaultValueProvider(EncoderProvider encoderProvider, Template.DefaultValueProvider defaultValueProvider) {
//    return token -> encoderProvider.getEncoder(token).apply(defaultValueProvider.getDefaultValue(token));
//  }

  public EncodeTemplate(String template, String placeHolderRegex, TokenProvider tokenProvider, EncoderProvider encoderProvider, Template.DefaultValueProvider defaultValueProvider) {
    super(template, placeHolderRegex, tokenProvider, defaultValueProvider);
    this.encoderProvider = encoderProvider;
  }

  public EncodeTemplate(String template, Pattern pattern, TokenProvider tokenProvider, EncoderProvider encoderProvider, Template.DefaultValueProvider defaultValueProvider) {
    super(template, pattern, tokenProvider, defaultValueProvider);
    this.encoderProvider = encoderProvider;
  }

  public EncodeTemplate(String template, Map<String, String> macros, Pattern pattern, TokenProvider tokenProvider, EncoderProvider encoderProvider, Template.DefaultValueProvider defaultValueProvider) {
    super(template, pattern, macros, tokenProvider, defaultValueProvider);
    this.encoderProvider = encoderProvider;
  }

  public static EncoderProvider getEncoderProviderByGroupName(String groupName) {
    return token -> StaticEncoder.getValue(token.getGroup(groupName));
  }

  @Override
  public String replace(TokenValue tokenValue) {
    return super.replace(token -> encoderProvider.getEncoder(token).apply(tokenValue.get(token)));
  }

  @Override
  public String replace(TokenValue tokenValue, Function<Exception, Exception> exceptionFunction) throws Exception {
    return super.replace(token -> encoderProvider.getEncoder(token).apply(tokenValue.get(token)));
  }

  @FunctionalInterface
  public interface EncoderProvider {
    Function<String, String> getEncoder(Token token);
  }
}
