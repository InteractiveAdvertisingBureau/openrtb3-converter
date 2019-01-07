package net.media.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by shiva.b on 02/01/19.
 */
public interface Template {
  String replace(Template.TokenValue tokenValue);

  default String replace(Template.TokenValue tokenValue, Function<Exception,Exception>
    exceptionFunction) throws Exception {
    try{
      return replace(tokenValue);
    } catch (Exception e){
      throw exceptionFunction.apply(e);
    }
  }


  @FunctionalInterface
  interface TokenValue  {
    String get(Token token);
  }

  @FunctionalInterface
  interface TokenProvider {
    Token getToken(Matcher matcher);
  }

  @FunctionalInterface
  interface DefaultValueProvider {
    String getDefaultValue(Token token);
  }

  @Data
  @AllArgsConstructor
  class Token {
    final String textValue;
    private final Map<String, String> groups;

    public String getGroup(String name) {
      return groups.get(name);
    }
  }

  public static Template.TokenProvider getTokenProviderByGroupNames(List<String> groupNames) {
    return matcher -> {
      Map<String, String> groups = new HashMap<>(groupNames.size());
      for (String groupName : groupNames) {
        groups.put(groupName, matcher.group(groupName));
      }
      return new Template.Token(matcher.group(), groups);
    };
  }
}
