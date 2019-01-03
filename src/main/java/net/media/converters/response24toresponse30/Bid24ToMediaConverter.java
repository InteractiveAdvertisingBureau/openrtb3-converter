package net.media.converters.response24toresponse30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.Bid;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Media;

/**
 * @author shiva.b
 */
public class Bid24ToMediaConverter implements Converter<Bid, Media> {

  private Converter<Bid, Ad> bidAdConverter;

  public Bid24ToMediaConverter(Converter<Bid, Ad> bidAdConverter) {
    this.bidAdConverter = bidAdConverter;
  }

  @Override
  public Media map(Bid source, Config config) {
    if (source == null) {
      return null;
    }
    Media media = new Media();
    inhance(source, media, config);
    return media;
  }

  @Override
  public void inhance(Bid source, Media target, Config config) {
    if (source == null || target == null) {
      return;
    }
    target.setAd(bidAdConverter.map(source, config));
  }
}
