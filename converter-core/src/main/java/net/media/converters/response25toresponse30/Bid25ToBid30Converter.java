/*
 * Copyright  2019 - present. MEDIA.NET ADVERTISING FZ-LLC
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

package net.media.converters.response25toresponse30;

import com.fasterxml.jackson.databind.JavaType;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Macro;
import net.media.openrtb3.Media;
import net.media.template.MacroMapper;
import net.media.utils.CommonConstants;
import net.media.utils.JacksonObjectMapperUtils;
import net.media.utils.Provider;

import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.media.utils.ExtUtils.*;

/** @author shiva.b */
public class Bid25ToBid30Converter implements Converter<Bid, net.media.openrtb3.Bid> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();
  private static final JavaType javaTypeForMacroCollection =
    JacksonObjectMapperUtils.getMapper()
      .getTypeFactory()
      .constructCollectionType(Collection.class, Macro.class);

  static {
    extraFieldsInExt.add(CommonConstants.MACRO);
    extraFieldsInExt.add(CommonConstants.AUDIT);
    extraFieldsInExt.add(CommonConstants.CATTAX);
    extraFieldsInExt.add(CommonConstants.LASTMOD);
    extraFieldsInExt.add(CommonConstants.INIT);
    extraFieldsInExt.add(CommonConstants.SECURE);
    extraFieldsInExt.add(CommonConstants.CTYPE);
    extraFieldsInExt.add(CommonConstants.PRIV);
    extraFieldsInExt.add(CommonConstants.MIME);
    extraFieldsInExt.add(CommonConstants.BANNER);
    extraFieldsInExt.add(CommonConstants.NATIVE);
    extraFieldsInExt.add(CommonConstants.EVENT);
    extraFieldsInExt.add(CommonConstants.DUR);
  }

  @Override
  public net.media.openrtb3.Bid map(Bid source, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    net.media.openrtb3.Bid bid = new net.media.openrtb3.Bid();
    enhance(source, bid, config, converterProvider);
    return bid;
  }

  @Override
  public void enhance(
      Bid source, net.media.openrtb3.Bid target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    try {
      target.setItem(source.getImpid());
      target.setDeal(source.getDealid());
      target.setPurl(source.getNurl());
      Converter<Bid, Media> converter =
          converterProvider.fetch(new Conversion<>(Bid.class, Media.class));
      target.setId(source.getId());
      target.setPrice(source.getPrice());
      target.setCid(source.getCid());
      target.setTactic(source.getTactic());
      target.setBurl(source.getBurl());
      target.setLurl(source.getLurl());
      target.setExp(source.getExp());
      target.setMid(source.getAdid());
      MacroMapper.macroReplaceThreeX(target);
      fetchFromExt(
        target::setMacro,
        source.getExt(),
        CommonConstants.MACRO,
        "Error while mapping macro from bid.ext",
        javaTypeForMacroCollection);
      putToExt(source::getProtocol, target.getExt(), CommonConstants.PROTOCOL, target::setExt);
      target.setMedia(converter.map(source, config, converterProvider));
      Map<String, Object> map = source.getExt();
      if (nonNull(map)) {
        if(isNull(target.getExt()))
          target.setExt(new HashMap<>());
        target.getExt().putAll(map);
      }
    } catch (Exception e) {
      throw new OpenRtbConverterException(e);
    }
    removeFromExt(target.getExt(), extraFieldsInExt);
  }
}
