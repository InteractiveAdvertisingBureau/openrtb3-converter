package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.Format;
import net.media.openrtb3.DisplayFormat;
import net.media.openrtb3.DisplayPlacement;
import net.media.utils.CollectionUtils;

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
    if (nonNull(displayPlacement.getDisplayfmt())) {
      for (DisplayFormat displayFormat : displayPlacement.getDisplayfmt()) {
        List<Integer> expdir = impBannerExpdir( banner );
        if ( expdir != null ) {
          displayFormat.setExpdir( new ArrayList<>( expdir ) );
        }
      }
    }
    List<String> list1 = banner.getMimes();
    if ( list1 != null ) {
      displayPlacement.setMime( new ArrayList<String>( list1 ) );
    }
    displayPlacement.setPos( banner.getPos() );
    displayPlacement.setTopframe( banner.getTopframe() );
    List<Integer> list2 = banner.getApi();
    if ( list2 != null ) {
      displayPlacement.setApi( new ArrayList<>( list2 ) );
    }
    else {
      displayPlacement.setApi( null );
    }
    displayPlacement.setW( banner.getW() );
    displayPlacement.setH( banner.getH() );
    Map<String, Object> bannerExt = banner.getExt();
    if (nonNull(bannerExt)) {
      if (isNull(displayPlacement.getExt())) {
        displayPlacement.setExt(new HashMap<>());
      }
      displayPlacement.getExt().putAll(bannerExt);
      if (bannerExt.containsKey("unit")) {
        displayPlacement.setUnit((Integer) bannerExt.get("unit"));
        displayPlacement.getExt().remove("unit");
      }
      if (bannerExt.containsKey("ctype")) {
        displayPlacement.setCtype((new ArrayList<>((List<Integer>) bannerExt.get
          ("ctype"))));
        displayPlacement.getExt().remove("ctype");
      }
      if (bannerExt.containsKey("seq")) {
        displayPlacement.getExt().remove("seq");
      }
    }
    if (!CollectionUtils.isEmpty(banner.getBtype())) {
      if (isNull(displayPlacement.getExt())) {
        displayPlacement.setExt(new HashMap<>());
      }
      displayPlacement.getExt().put("btype", new ArrayList<>(banner.getBtype()));
    }
  }

  private List<Integer> impBannerExpdir(Banner banner) {
    List<Integer> expdir = banner.getExpdir();
    if ( expdir == null ) {
      return null;
    }
    return expdir;
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
