package net.media.converters.request25toRequest30;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.Video;
import net.media.openrtb3.Companion;
import net.media.openrtb3.VideoPlacement;
import net.media.utils.CollectionToCollectionConverter;
import net.media.utils.CollectionUtils;
import net.media.utils.Utils;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/01/19.
 */

public class VideoToVideoPlacementConverter implements Converter<Video, VideoPlacement> {

  private Converter<Banner, Companion> bannerCompanionConverter;

  @java.beans.ConstructorProperties({"bannerCompanionConverter"})
  public VideoToVideoPlacementConverter(Converter<Banner, Companion> bannerCompanionConverter) {
    this.bannerCompanionConverter = bannerCompanionConverter;
  }

  @Override
  public VideoPlacement map(Video video, Config config) throws OpenRtbConverterException {
    if ( video == null) {
      return null;
    }

    VideoPlacement videoPlacement = new VideoPlacement();
    enhance(video, videoPlacement, config);

    return videoPlacement;
  }

  @Override
  public void enhance(Video video, VideoPlacement videoPlacement, Config config) throws OpenRtbConverterException {
    if (isNull(video) || isNull(videoPlacement)) {
      return;
    }
    videoPlacement.setComptype(Utils.copyCollection(video.getCompaniontype(), config));
    videoPlacement.setComp( CollectionToCollectionConverter.convert( video.getCompanionad(), bannerCompanionConverter, config ) );
    videoPlacement.setLinear( video.getLinearity() );
    videoPlacement.setMime(Utils.copyCollection(video.getMimes(), config));
    videoPlacement.setMinbitr( video.getMinbitrate() );
    videoPlacement.setPtype( video.getPlacement() );
    videoPlacement.setMaxdur( video.getMaxduration() );
    videoPlacement.setMaxext( video.getMaxextended() );
    videoPlacement.setDelay( video.getStartdelay() );
    videoPlacement.setPlayend( video.getPlaybackend() );
    videoPlacement.setMindur( video.getMinduration() );
    videoPlacement.setCtype(Utils.copyCollection(video.getProtocols(), config));
    videoPlacement.setBoxing( video.getBoxingallowed() );
    videoPlacement.setPlaymethod( CollectionUtils.firstElementFromCollection( video
      .getPlaybackmethod() ) );
    videoPlacement.setMaxbitr( video.getMaxbitrate() );
    videoPlacement.setPos( video.getPos() );
    videoPlacement.setSkip( video.getSkip() );
    videoPlacement.setSkipmin( video.getSkipmin() );
    videoPlacement.setSkipafter( video.getSkipafter() );
    videoPlacement.setApi(Utils.copyCollection(video.getApi(), config));
    videoPlacement.setW( video.getW() );
    videoPlacement.setH( video.getH() );
    videoPlacement.setDelivery(Utils.copyCollection(video.getDelivery(), config));
    videoToVideoPlacementAfterMapping(video, videoPlacement);
  }

  private void videoToVideoPlacementAfterMapping(Video video, VideoPlacement videoPlacement) throws OpenRtbConverterException {
    try {
      if (nonNull(video) && nonNull(video.getExt()) && nonNull(videoPlacement)) {
        if (video.getExt().containsKey("unit")) {
          videoPlacement.setUnit((Integer) video.getExt().get("unit"));
          videoPlacement.getExt().remove("unit");
        }
        if (video.getExt().containsKey("maxseq")) {
          videoPlacement.setMaxseq((Integer) video.getExt().get("maxseq"));
          videoPlacement.getExt().remove("maxseq");
        }
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for Video", e);
    }
  }
}
