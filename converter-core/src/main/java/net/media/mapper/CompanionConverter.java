package net.media.mapper;

import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.request.Imp;
import net.media.openrtb3.Companion;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import static java.util.Objects.nonNull;

@Generated(
  value = "org.mapstruct.ap.MappingProcessor",
  date = "2018-12-27T01:21:12+0530",
  comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 1.8.0_91 (Oracle Corporation)"
)
public class CompanionConverter implements CompanionToBannerMapper {

  private final DisplayConverter displayConverter = new DisplayConverter();

  @Override
  public Companion map(Banner banner, Imp imp, BidRequest bidRequest) {
    if ( banner == null ) {
      return null;
    }

    Companion companion = new Companion();

    if (nonNull(banner.getId())) {
      companion.setId(banner.getId());
    }
    companion.setVcm( banner.getVcm() );
    companion.setDisplay( displayConverter.impToDisplayPlacementMapping( banner, null, imp, bidRequest ) );
    companion.setId( banner.getId() );
    Map<String, Object> map = banner.getExt();
    if ( map != null ) {
      companion.setExt( new HashMap<String, Object>( map ) );
    }

    return companion;
  }

  @Override
  public Banner map(Companion companion) {
    if ( companion == null ) {
      return null;
    }

    Banner banner = new Banner();

    banner.setVcm( companion.getVcm() );
    banner.setId( companion.getId() );
    Map<String, Object> map = companion.getExt();
    if ( map != null ) {
      banner.setExt( new HashMap<String, Object>( map ) );
    }

    return banner;
  }
}
