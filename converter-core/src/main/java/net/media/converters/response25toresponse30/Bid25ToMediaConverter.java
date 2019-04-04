package net.media.converters.response25toresponse30;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Media;

/**
 * @author shiva.b
 */
public class Bid25ToMediaConverter implements Converter<Bid, Media> {

  private Converter<Bid, Ad> bidAdConverter;

  public Bid25ToMediaConverter(Converter<Bid, Ad> bidAdConverter) {
    this.bidAdConverter = bidAdConverter;
  }

  @Override
  public Media map(Bid source, Config config)throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }
    Media media = new Media();
    enhance(source, media, config);
    return media;
  }

  @Override
  public void enhance(Bid source, Media target, Config config)throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    target.setAd(bidAdConverter.map(source, config));
  }
}
