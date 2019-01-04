package net.media.converters.request30toRequest24;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.Format;
import net.media.openrtb3.DisplayFormat;
import net.media.openrtb3.DisplayPlacement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class DisplayPlacementToBannerConverter implements Converter<DisplayPlacement, Banner> {
  @Override
  public Banner map(DisplayPlacement displayPlacement, Config config) {
    if ( displayPlacement == null ) {
      return null;
    }
    Banner banner = new Banner();
    inhance(displayPlacement, banner, config);
    return banner;
  }

  @Override
  public void inhance(DisplayPlacement displayPlacement, Banner banner, Config config) {
    if (isNull(displayPlacement) || isNull(banner)) {
      return;
    }
    List<String> list = displayPlacement.getMime();
    if ( list != null ) {
      banner.setMimes( new ArrayList<String>( list ) );
    }
    banner.setFormat( displayFormatListToFormatList( displayPlacement.getDisplayfmt() ) );
    banner.setW( displayPlacement.getW() );
    banner.setH( displayPlacement.getH() );
    banner.setPos( displayPlacement.getPos() );
    banner.setTopframe( displayPlacement.getTopframe() );
    List<Integer> list2 = displayPlacement.getApi();
    if ( list2 != null ) {
      banner.setApi( new ArrayList<Integer>( list2 ) );
    }
    Map<String, Object> map = displayPlacement.getExt();
    if ( map != null ) {
      banner.setExt( new HashMap<String, Object>( map ) );
    }
  }

  private List<Format> displayFormatListToFormatList(List<DisplayFormat> list) {
    if ( list == null ) {
      return null;
    }

    List<Format> list1 = new ArrayList<Format>( list.size() );
    for ( DisplayFormat displayFormat : list ) {
      list1.add( displayFormatToFormat( displayFormat ) );
    }

    return list1;
  }

  private Format displayFormatToFormat(DisplayFormat displayFormat) {
    if ( displayFormat == null ) {
      return null;
    }

    Format format = new Format();

    format.setW( displayFormat.getW() );
    format.setH( displayFormat.getH() );
    format.setWratio( displayFormat.getWratio() );
    format.setHratio( displayFormat.getHratio() );
    Map<String, Object> map = displayFormat.getExt();
    if ( map != null ) {
      format.setExt( new HashMap<>( map ) );
      format.setWmin((Integer) map.get("wmin"));
    }

    return format;
  }
}
