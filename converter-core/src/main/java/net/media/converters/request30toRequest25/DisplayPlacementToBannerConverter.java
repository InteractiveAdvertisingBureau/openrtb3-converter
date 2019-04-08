package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.Format;
import net.media.openrtb3.DisplayFormat;
import net.media.openrtb3.DisplayPlacement;
import net.media.utils.CollectionUtils;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class DisplayPlacementToBannerConverter implements Converter<DisplayPlacement, Banner> {
  @Override
  public Banner map(DisplayPlacement displayPlacement, Config config, Provider converterProvider)
    throws OpenRtbConverterException {
    if ( displayPlacement == null ) {
      return null;
    }
    if (nonNull(displayPlacement.getNativefmt())) {
      return null;
    }
    Banner banner = new Banner();
    enhance(displayPlacement, banner, config, converterProvider);
    return banner;
  }

  @Override
  public void enhance(DisplayPlacement displayPlacement, Banner banner, Config config, Provider
    converterProvider) throws OpenRtbConverterException {
    if (isNull(displayPlacement) || isNull(banner)) {
      return;
    }
    banner.setMimes(Utils.copyCollection(displayPlacement.getMime(), config));
    banner.setFormat( displayFormatListToFormatList( displayPlacement.getDisplayfmt(), config ) );
    if (nonNull(displayPlacement.getDisplayfmt())) {
      for (DisplayFormat displayFormat : displayPlacement.getDisplayfmt()) {
        if (!CollectionUtils.isEmpty(displayFormat.getExpdir())) {
          Collection<Integer> formatExpdir = Utils.copyCollection(displayFormat.getExpdir(),
            config);
          if (isNull(banner.getExpdir())) {
            banner.setExpdir(formatExpdir);
          } else {
            try {
              banner.setExpdir((Collection<Integer>) org.apache.commons.collections
                .CollectionUtils.union(banner.getExpdir(), formatExpdir));
            } catch (ClassCastException e) {
              throw new OpenRtbConverterException("error while typecasting expdir for DisplayPlacement", e);
            }
          }
        }
      }
    }
    banner.setW( displayPlacement.getW() );
    banner.setH( displayPlacement.getH() );
    banner.setPos( displayPlacement.getPos() );
    banner.setTopframe( displayPlacement.getTopframe() );
    banner.setApi(Utils.copyCollection(displayPlacement.getApi(), config));
    Map<String, Object> map = displayPlacement.getExt();
    if ( map != null ) {
      banner.setExt(Utils.copyMap(map, config));
      if (map.containsKey("btype")) {
        try {
          banner.setBtype(Utils.copyCollection((Collection<Integer>) map.get("btype"), config));
          banner.getExt().remove("btype");
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException("error while typecasting ext for DisplayPlacement", e);
        }
      }
      if (map.containsKey("id")) {
        try {
          banner.setId((String) map.get("id"));
          banner.getExt().remove("id");
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException("error while typecasting ext for DisplayPlacement", e);
        }
      }
      if (map.containsKey("hmax")) {
        try {
          banner.setHmax((Integer) map.get("hmax"));
          banner.getExt().remove("hmax");
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException("error while typecasting ext for DisplayPlacement", e);
        }
      }
      if (map.containsKey("wmax")) {
        try {
          banner.setWmax((Integer) map.get("wmax"));
          banner.getExt().remove("wmax");
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException("error while typecasting ext for DisplayPlacement", e);
        }
      }
      if (map.containsKey("hmin")) {
        try {
          banner.setHmin((Integer) map.get("hmin"));
          banner.getExt().remove("hmin");
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException("error while typecasting ext for DisplayPlacement", e);
        }
      }
      if (map.containsKey("wmin")) {
        try {
          banner.setWmin((Integer) map.get("wmin"));
          banner.getExt().remove("wmin");
        } catch (ClassCastException e) {
          throw new OpenRtbConverterException("error while typecasting ext for DisplayPlacement", e);
        }
      }
    }
    if (nonNull(displayPlacement.getUnit())) {
      if (isNull(banner.getExt())) {
        banner.setExt(new HashMap<>());
      }
      banner.getExt().put("unit", displayPlacement.getUnit());
    }
    if(nonNull(displayPlacement.getPtype())) {
      if (isNull(banner.getExt())) {
        banner.setExt(new HashMap<>());
      }
      banner.getExt().put("ptype", displayPlacement.getPtype());
    }
    if(nonNull(displayPlacement.getContext())) {
      if (isNull(banner.getExt())) {
        banner.setExt(new HashMap<>());
      }
      banner.getExt().put("context", displayPlacement.getContext());
    }
    if(nonNull(displayPlacement.getCtype())) {
      if (isNull(banner.getExt())) {
        banner.setExt(new HashMap<>());
      }
      banner.getExt().put("ctype", displayPlacement.getCtype());
    }
    if(nonNull(displayPlacement.getPriv())) {
      if (isNull(banner.getExt())) {
        banner.setExt(new HashMap<>());
      }
      banner.getExt().put("priv", displayPlacement.getPriv());
    }
  }

  private Collection<Format> displayFormatListToFormatList(Collection<DisplayFormat> list, Config
    config) throws OpenRtbConverterException {
    if ( list == null ) {
      return null;
    }

    Collection<Format> list1 = new ArrayList<Format>( list.size() );
    for ( DisplayFormat displayFormat : list ) {
      list1.add( displayFormatToFormat( displayFormat, config ) );
    }

    return list1;
  }

  private Format displayFormatToFormat(DisplayFormat displayFormat, Config config) throws OpenRtbConverterException {
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
      format.setExt(Utils.copyMap(map, config));
      try {
        if(map.containsKey("wmin")) {
          format.setWmin((Integer) map.get("wmin"));
          format.getExt().remove("wmin");
        }
      } catch (ClassCastException e) {
        throw new OpenRtbConverterException("error while typecasting ext for DisplayPlacement", e);
      }
    }

    return format;
  }
}
