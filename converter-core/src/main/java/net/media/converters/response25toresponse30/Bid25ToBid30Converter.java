package net.media.converters.response25toresponse30;

import com.fasterxml.jackson.core.type.TypeReference;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Macro;
import net.media.openrtb3.Media;
import net.media.template.MacroMapper;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.Collection;
import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author shiva.b
 */
public class Bid25ToBid30Converter implements Converter<Bid, net.media.openrtb3.Bid> {

  @Override
  public net.media.openrtb3.Bid map(Bid source, Config config, Provider converterProvider)throws OpenRtbConverterException {
    if (isNull(source)) {
      return null;
    }
    net.media.openrtb3.Bid bid = new net.media.openrtb3.Bid();
    enhance(source, bid, config, converterProvider);
    return bid;
  }

  @Override
  public void enhance(Bid source, net.media.openrtb3.Bid target, Config config, Provider converterProvider) throws OpenRtbConverterException{
    if (source == null || target == null) {
      return;
    }
    target.setExt(Utils.copyMap(source.getExt(),config));
    target.setItem( source.getImpid() );
    target.setDeal( source.getDealid() );
    target.setPurl( source.getNurl() );
    Converter<Bid, Media> converter = converterProvider.fetch(new Conversion<>(Bid.class, Media
      .class));
    target.setMedia(converter.map(source, config, converterProvider));
    target.setId( source.getId() );
    target.setPrice( source.getPrice() );
    target.setCid( source.getCid() );
    target.setTactic( source.getTactic() );
    target.setBurl( source.getBurl() );
    target.setLurl( source.getLurl() );
    target.setExp( source.getExp() );
    target.setMid(source.getAdid());
    MacroMapper.macroReplaceThreeX(target);
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("macro")) {
        try {
          Collection<Macro> macros = Utils.getMapper().convertValue(source.getExt().get("macro"),
            new TypeReference<Collection<Macro>>() {
            });
          target.setMacro(macros);
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting bid.macro from bid.ext.macro", e);
        }
      }
    }
    if (nonNull(source.getProtocol())) {
      if (isNull(target.getExt())) {
        target.setExt(new HashMap<>());
      }
      target.getExt().put("protocol", source.getProtocol());
    }
  }
}
