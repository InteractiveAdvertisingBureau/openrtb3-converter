package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.mapper.DisplayConverter;
import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Format;
import net.media.openrtb24.request.Imp;
import net.media.openrtb3.DisplayFormat;
import net.media.openrtb3.DisplayPlacement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/01/19.
 */
public class BannerToDisplayPlacementConverter implements Converter<Banner, DisplayPlacement> {

  @Override
  public DisplayPlacement map(Banner banner, Config config) {
    if (isNull(banner)) {
      return null;
    }
    DisplayPlacement displayPlacement = new DisplayPlacement();
    inhance(banner, displayPlacement, config);
    return displayPlacement;
  }

  @Override
  public void inhance(Banner banner, DisplayPlacement displayPlacement, Config config) {
    if (isNull(banner) || isNull(displayPlacement)) {
      return;
    }
    displayPlacement.setDisplayfmt( formatListToDisplayFormatList( banner.getFormat()) );
    List<String> list1 = banner.getMimes();
    if ( list1 != null ) {
      displayPlacement.setMime( new ArrayList<String>( list1 ) );
    }
    displayPlacement.setPos( banner.getPos() );
    displayPlacement.setTopframe( banner.getTopframe() );
    displayPlacement.setW( banner.getW() );
    displayPlacement.setH( banner.getH() );
    Map<String, Object> bannerExt = banner.getExt();
    if (nonNull(bannerExt)) {
      if (isNull(displayPlacement.getExt())) {
        displayPlacement.setExt(new HashMap<>());
      }
      displayPlacement.getExt().putAll(bannerExt);
    }
  }

  private List<DisplayFormat> formatListToDisplayFormatList(List<Format> list) {
    if ( list == null ) {
      return null;
    }

    List<DisplayFormat> list1 = new ArrayList<DisplayFormat>( list.size() );
    for ( Format format : list ) {
      list1.add( map( format) );
    }

    return list1;
  }

  private DisplayFormat map(Format format) {
    if ( format == null ) {
      return null;
    }

    DisplayFormat displayFormat = new DisplayFormat();

    Map<String, Object> map = format.getExt();
    if ( map != null ) {
      displayFormat.setExt( new HashMap<String, Object>( map ) );
    }
    displayFormat.setW( format.getW() );
    displayFormat.setH( format.getH() );
    displayFormat.setWratio( format.getWratio() );
    displayFormat.setHratio( format.getHratio() );
    if (nonNull(format.getWmin())) {
      displayFormat.setExt(new HashMap<>());
      displayFormat.getExt().put("wmin", format.getWmin());
    }

    return displayFormat;
  }
}
