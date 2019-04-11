/*
 * Copyright © 2019 - present. MEDIA NET SOFTWARE SERVICES PVT. LTD.
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

package net.media.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;

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

  class Token {
    final String textValue;
    private final Map<String, String> groups;

    @java.beans.ConstructorProperties({"textValue", "groups"})
    public Token(String textValue, Map<String, String> groups) {
      this.textValue = textValue;
      this.groups = groups;
    }

    public String getGroup(String name) {
      return groups.get(name);
    }

    public String getTextValue() {
      return this.textValue;
    }

    public Map<String, String> getGroups() {
      return this.groups;
    }

    public boolean equals(Object o) {
      if (o == this) return true;
      if (!(o instanceof Token)) return false;
      final Token other = (Token) o;
      if (!other.canEqual((Object) this)) return false;
      final Object this$textValue = this.getTextValue();
      final Object other$textValue = other.getTextValue();
      if (this$textValue == null ? other$textValue != null : !this$textValue.equals(other$textValue))
        return false;
      final Object this$groups = this.getGroups();
      final Object other$groups = other.getGroups();
      if (this$groups == null ? other$groups != null : !this$groups.equals(other$groups))
        return false;
      return true;
    }

    public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      final Object $textValue = this.getTextValue();
      result = result * PRIME + ($textValue == null ? 43 : $textValue.hashCode());
      final Object $groups = this.getGroups();
      result = result * PRIME + ($groups == null ? 43 : $groups.hashCode());
      return result;
    }

    protected boolean canEqual(Object other) {
      return other instanceof Token;
    }

    public String toString() {
      return "net.media.template.Template.Token(textValue=" + this.getTextValue() + ", groups=" + this.getGroups() + ")";
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
