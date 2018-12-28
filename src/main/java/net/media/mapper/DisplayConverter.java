package net.media.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Format;
import net.media.openrtb24.request.Imp;
import net.media.openrtb24.request.Native;
import net.media.openrtb24.request.NativeRequest;
import net.media.openrtb3.DisplayFormat;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.EventSpec;
import net.media.openrtb3.NativeFormat;


import org.mapstruct.MappingTarget;

import static java.util.Objects.nonNull;

@Generated(
  value = "org.mapstruct.ap.MappingProcessor",
  date = "2018-12-27T09:30:12+0530",
  comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 1.8.0_91 (Oracle Corporation)"
)
public class DisplayConverter {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private final NativeConverter nativeConverter = new NativeConverter();

  public Banner bannerToDisplayPlacement(DisplayPlacement displayPlacement) {
    if ( displayPlacement == null ) {
      return null;
    }

    Banner banner = new Banner();

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

    return banner;
  }

  public DisplayPlacement impToDisplayPlacementMapping(Banner banner, Native nat, Imp imp, BidRequest bidRequest) {
    if ( (banner == null && nat == null) || imp == null || bidRequest == null ) {
      return null;
    }

    DisplayPlacement displayPlacement = new DisplayPlacement();

    if ( banner != null ) {
      displayPlacement.setDisplayfmt( formatListToDisplayFormatList( banner.getFormat(), banner,
        imp, bidRequest) );
      List<String> list1 = banner.getMimes();
      if ( list1 != null ) {
        displayPlacement.setMime( new ArrayList<String>( list1 ) );
      }
      displayPlacement.setPos( banner.getPos() );
      displayPlacement.setTopframe( banner.getTopframe() );
      displayPlacement.setW( banner.getW() );
      displayPlacement.setH( banner.getH() );
    }
    if ( imp != null ) {
      Map<String, Object> map = imp.getExt();
      if ( map != null ) {
        displayPlacement.setExt( new HashMap<String, Object>( map ) );
      }
      displayPlacement.setClktype( imp.getClickbrowser() );
      List<String> list2 = imp.getIframebuster();
      if ( list2 != null ) {
        displayPlacement.setIfrbust( new ArrayList<String>( list2 ) );
      }
      displayPlacement.setInstl( imp.getInstl() );
    }
    displayPlacement.setApi( nonNull(banner) ? banner.getApi() : nat.getApi() );

    displayPlacementToImpAfterMapping( banner, nat, imp, bidRequest, displayPlacement );

    return displayPlacement;
  }

  public Format map(DisplayFormat displayFormat) {
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
      format.setExt( new HashMap<String, Object>( map ) );
    }

    return format;
  }

  public DisplayFormat map(Format format, Banner banner, Imp imp, BidRequest bidRequest) {
    if ( format == null && imp == null && bidRequest == null ) {
      return null;
    }

    DisplayFormat displayFormat = new DisplayFormat();

    if ( format != null ) {
      Map<String, Object> map = format.getExt();
      if ( map != null ) {
        displayFormat.setExt( new HashMap<String, Object>( map ) );
      }
      displayFormat.setW( format.getW() );
      displayFormat.setH( format.getH() );
      displayFormat.setWratio( format.getWratio() );
      displayFormat.setHratio( format.getHratio() );
    }
    if ( imp != null ) {
      List<Integer> expdir = impBannerExpdir( imp );
      List<Integer> list = expdir;
      if ( list != null ) {
        displayFormat.setExpdir( new ArrayList<Integer>( list ) );
      }
    }

    return displayFormat;
  }

  protected List<DisplayFormat> formatListToDisplayFormatList(List<Format> list, Banner banner,
                                                              Imp imp, BidRequest bidRequest) {
    if ( list == null ) {
      return null;
    }

    List<DisplayFormat> list1 = new ArrayList<DisplayFormat>( list.size() );
    for ( Format format : list ) {
      list1.add( map( format, banner, imp, bidRequest ) );
    }

    return list1;
  }

  protected List<Format> displayFormatListToFormatList(List<DisplayFormat> list) {
    if ( list == null ) {
      return null;
    }

    List<Format> list1 = new ArrayList<Format>( list.size() );
    for ( DisplayFormat displayFormat : list ) {
      list1.add( map( displayFormat ) );
    }

    return list1;
  }

  private List<Integer> impBannerExpdir(Imp imp) {
    if ( imp == null ) {
      return null;
    }
    Banner banner = imp.getBanner();
    if ( banner == null ) {
      return null;
    }
    List<Integer> expdir = banner.getExpdir();
    if ( expdir == null ) {
      return null;
    }
    return expdir;
  }

  protected void displayPlacementToImpAfterMapping(Banner banner, Native nat, Imp imp, BidRequest
    bidRequest, DisplayPlacement displayPlacement) {
    if (nonNull(imp.getExt()) && !imp.getExt().isEmpty() && nonNull(displayPlacement)) {
      displayPlacement.setAmpren((Integer) imp.getExt().get("ampren"));
      displayPlacement.setCtype((List<Integer>) imp.getExt().get
        ("ctype"));
      displayPlacement.setUnit((Integer) imp.getExt().get("unit"));
      displayPlacement.setPriv((Integer) imp.getExt().get("priv"));
      displayPlacement.setEvent((EventSpec) imp.getExt().get("event"));
    }
    if (nonNull(nat)) {
      if (nonNull(nat.getRequest())) {
        NativeRequest nativeRequest = null;
        if (nat.getRequest() instanceof NativeRequest) {
          nativeRequest = (NativeRequest) nat.getRequest();
        } else {
          String nativeRequestString = (String) nat.getRequest();
          try {
            nativeRequest = MAPPER.readValue(nativeRequestString,
              NativeRequest.class);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        if (nonNull(nativeRequest) && nonNull(nativeRequest.getNativeRequestBody())) {
          displayPlacement.setPtype(nativeRequest.getNativeRequestBody().getPlcmttype());
          displayPlacement.setContext(nativeRequest.getNativeRequestBody().getContext());
          displayPlacement.setNativefmt(nativeConverter.map(nativeRequest.getNativeRequestBody()));
        }
      }
    }
  }
}
