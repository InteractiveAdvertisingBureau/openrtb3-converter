package net.media.template;

import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shiva.b on 02/01/19.
 */
public class SimpleTemplate implements Template {
  final LinkedList<Group> groupPrefix = new LinkedList<>();
  String tail;
  private Template.DefaultValueProvider defaultValueProvider;

  public SimpleTemplate(String template,
                        String placeHolderRegex,
                        Template.TokenProvider tokenProvider,
                        Template.DefaultValueProvider defaultValueProvider) {
    this(template, Pattern.compile(placeHolderRegex), tokenProvider, defaultValueProvider);
  }

  public SimpleTemplate(String template,
                        Pattern pattern,
                        Template.TokenProvider tokenProvider,
                        Template.DefaultValueProvider defaultValueProvider) {
    final Matcher matcher = pattern.matcher(template);
    int prev = 0;
    while (matcher.find()) {
      groupPrefix.add(new Group(tokenProvider.getToken(matcher), template.substring(prev, matcher.start())));
      prev = matcher.end();
    }
    tail = template.substring(prev);
    this.defaultValueProvider = defaultValueProvider;
  }

  public SimpleTemplate(String template,
                        Pattern pattern, Map<String, String> macros,
                        Template.TokenProvider tokenProvider,
                        Template.DefaultValueProvider defaultValueProvider) {
    int queryParamCount=0;
    if(template.contains("?"))
      queryParamCount++;
    final Matcher matcher = pattern.matcher(template);
    int prev = 0;
    while (matcher.find()) {
      groupPrefix.add(new Group(tokenProvider.getToken(matcher), template.substring(prev, matcher.start())));
      prev = matcher.end();
    }
    for (Map.Entry<String, String> token : macros.entrySet()) {
      String macro = token.getValue();
      char delimiter = (queryParamCount == 0) ? '?' : '&';
      Matcher matcher1 = pattern.matcher(macro);
      if(matcher1.find())
        groupPrefix.add(new Group(tokenProvider.getToken(matcher1), delimiter + token.getKey()));
      queryParamCount++;
    }
    tail = template.substring(prev);
    this.defaultValueProvider = defaultValueProvider;
  }

  protected String getMacroReplacement(String value,
                                       Token token) {
    return (value == null || value.isEmpty()) ? defaultValueProvider.getDefaultValue(token) : value;
  }

  @Override
  public String replace(Template.TokenValue tokenValue) {
    StringBuilder res = new StringBuilder(0);
    for (Group group : groupPrefix) {
      String value = tokenValue.get(group.token);
      res.append(group.prefix).append(getMacroReplacement(value, group.token));
    }
    return res.append(tail).toString();
  }

  static class Group {
    Token token;
    String prefix;

    @java.beans.ConstructorProperties({"token", "prefix"})
    public Group(Token token, String prefix) {
      this.token = token;
      this.prefix = prefix;
    }
  }
}
