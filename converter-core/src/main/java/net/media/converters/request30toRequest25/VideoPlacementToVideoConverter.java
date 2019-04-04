package net.media.converters.request30toRequest25;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.Companion;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.CollectionToCollectionConverter;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.Collections;
import java.util.Collection;
import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class VideoPlacementToVideoConverter implements Converter<VideoPlacement, Video> {

  @Override
  public Video map(VideoPlacement videoPlacement, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if ( videoPlacement == null ) {
      return null;
    }

    Video video = new Video();
    enhance(videoPlacement, video, config, converterProvider);

    return video;
  }

  @Override
  public void enhance(VideoPlacement videoPlacement, Video video, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if (isNull(video) || isNull(videoPlacement)) {
      return;
    }
    Converter<Companion, Banner> companionBannerConverter = converterProvider.fetch(new Conversion(Companion.class, Banner.class));
    video.setMinbitrate( videoPlacement.getMinbitr() );
    video.setMaxbitrate( videoPlacement.getMaxbitr() );
    video.setProtocols(Utils.copyCollection(videoPlacement.getCtype(), config));
    video.setBoxingallowed( videoPlacement.getBoxing() );
    video.setPlacement( videoPlacement.getPtype() );
    video.setPlaybackend( videoPlacement.getPlayend() );
    video.setMinduration( videoPlacement.getMindur() );
    video.setCompaniontype(Utils.copyCollection(videoPlacement.getComptype(), config));
    video.setCompanionad( CollectionToCollectionConverter.convert( videoPlacement.getComp(),
      companionBannerConverter, config, converterProvider ) );
    video.setMimes(Utils.copyCollection(videoPlacement.getMime(), config));
    video.setMaxduration( videoPlacement.getMaxdur() );
    video.setMaxextended( videoPlacement.getMaxext() );
    video.setStartdelay( videoPlacement.getDelay() );
    video.setLinearity( videoPlacement.getLinear() );
    video.setW( videoPlacement.getW() );
    video.setH( videoPlacement.getH() );
    video.setSkip( videoPlacement.getSkip() );
    video.setSkipmin( videoPlacement.getSkipmin() );
    video.setSkipafter( videoPlacement.getSkipafter() );
    video.setDelivery(Utils.copyCollection(videoPlacement.getDelivery(), config));
    video.setPos( videoPlacement.getPos() );
    video.setApi(Utils.copyCollection(videoPlacement.getDelivery(), config));
    video.setExt(Utils.copyMap(videoPlacement.getExt(), config));

    videoPlacementToVideoAfterMapping( videoPlacement, video );
  }

  private void videoPlacementToVideoAfterMapping(VideoPlacement videoPlacement, Video video) {
    if (nonNull(videoPlacement) && nonNull(video)) {
      if (nonNull(videoPlacement.getPlaymethod())) {
        video.setPlaybackmethod(Collections.singletonList(videoPlacement.getPlaymethod()));
      }
    }
    if (nonNull(videoPlacement.getUnit())) {
      if (isNull(video.getExt())) {
        video.setExt(new HashMap<>());
      }
      video.getExt().put("unit", videoPlacement.getUnit());
    }
    if (nonNull(videoPlacement.getMaxseq())) {
      if (isNull(video.getExt())) {
        video.setExt(new HashMap<>());
      }
      video.getExt().put("maxseq", videoPlacement.getMaxseq());
    }
  }
}
