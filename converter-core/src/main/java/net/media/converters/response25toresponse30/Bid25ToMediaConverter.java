package net.media.converters.response25toresponse30;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Media;
import net.media.utils.Provider;

/**
 * @author shiva.b
 */
public class Bid25ToMediaConverter implements Converter<Bid, Media> {

  @Override
  public Media map(Bid source, Config config, Provider converterProvider)throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }
    Media media = new Media();
    enhance(source, media, config, converterProvider);
    return media;
  }

  @Override
  public void enhance(Bid source, Media target, Config config, Provider converterProvider)throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    Converter<Bid, Ad> bidAdConverter = converterProvider.fetch(new Conversion<>(Bid.class, Ad.class));
    target.setAd(bidAdConverter.map(source, config, converterProvider));
  }
}
