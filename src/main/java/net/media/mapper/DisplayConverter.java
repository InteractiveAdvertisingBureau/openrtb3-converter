package net.media.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

import net.media.config.Config;
import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Format;
import net.media.openrtb24.request.Imp;
import net.media.openrtb24.request.Native;
import net.media.openrtb24.request.NativeRequest;
import net.media.openrtb24.request.NativeRequestBody;
import net.media.openrtb3.DisplayFormat;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.EventSpec;
import net.media.openrtb3.Item;
import net.media.openrtb3.Request;
import net.media.util.JacksonObjectMapper;


import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Generated(
  value = "org.mapstruct.ap.MappingProcessor",
  date = "2018-12-27T09:30:12+0530",
  comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 1.8.0_91 (Oracle Corporation)"
)
public class DisplayConverter {

  private final NativeConverter nativeConverter = new NativeConverter();

  public Banner displayPlacementToBanner(DisplayPlacement displayPlacement, Item item, Request
    request) {
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
    if (nonNull(item.getSeq())) {
      if (isNull(banner.getExt())) {
        banner.setExt(new HashMap<>());
      }
      banner.getExt().put("seq", item.getSeq());
    }
    if (nonNull(request)) {
      if (nonNull(request.getContext())) {
        if (nonNull(request.getContext().getRestrictions())) {
          if (nonNull(request.getContext().getRestrictions())) {
            if (nonNull(request.getContext().getRestrictions().getBattr())) {
              banner.setBattr(new HashSet<>(request.getContext().getRestrictions().getBattr()));
            }
          }
        }
      }
    }
    return banner;
  }

  public Native displayPlacementToNative(DisplayPlacement displayPlacement, Item item, Request
    request) {
    if (displayPlacement == null) {
      return null;
    }
    NativeRequestBody nativeRequestBody = nativeConverter.map(displayPlacement.getNativefmt());
    if (isNull(nativeRequestBody)) {
      return null;
    }
    NativeRequest nativeRequest = new NativeRequest();
    nativeRequest.setNativeRequestBody(nativeRequestBody);
    if (nonNull(nativeRequest.getNativeRequestBody())) {
      nativeRequest.getNativeRequestBody().setContext(displayPlacement.getContext());
      nativeRequest.getNativeRequestBody().setPlcmttype(displayPlacement.getPtype());
      nativeRequest.getNativeRequestBody().setPlcmtcnt(item.getQty());
      nativeRequest.getNativeRequestBody().setSeq(item.getSeq());
    }
    Native nat = new Native();
    List<Integer> api = displayPlacement.getApi();
    if ( api != null ) {
      nat.setApi( new ArrayList<Integer>( api ) );
    }
    if (nonNull(request)) {
      if (nonNull(request.getContext())) {
        if (nonNull(request.getContext().getRestrictions())) {
          if (nonNull(request.getContext().getRestrictions())) {
            if (nonNull(request.getContext().getRestrictions().getBattr())) {
              nat.setBattr(new HashSet<>(request.getContext().getRestrictions().getBattr()));
            }
          }
        }
      }
    }
    if (nonNull(displayPlacement.getExt())) {
      if (isNull(nat.getExt())) {
        nat.setExt(new HashMap<>());
      }
      nat.getExt().putAll(displayPlacement.getExt());
      nat.setVer((String) displayPlacement.getExt().get("nativeversion"));
      nat.getExt().remove("nativeversion");
    }
    Config config = new Config();
    if (config.isNativeRequestAsString()) {
      try {
        nat.setRequest(JacksonObjectMapper.getMapper().writeValueAsString(nativeRequest));
      } catch (JsonProcessingException e) {

      }
    } else {
      nat.setRequest(nativeRequest);
    }
    Map<String, Object> map = displayPlacement.getExt();
    if ( map != null ) {
      nat.setExt( new HashMap<String, Object>( map ) );
    }
    return nat;
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
      Map<String, Object> bannerExt = banner.getExt();
      if (nonNull(bannerExt)) {
        if (isNull(displayPlacement.getExt())) {
          displayPlacement.setExt(new HashMap<>());
        }
        displayPlacement.getExt().putAll(bannerExt);
      }
    }
    if ( imp != null ) {
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
      format.setWmin((Integer) map.get("wmin"));
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
      if (nonNull(format.getWmin())) {
        displayFormat.setExt(new HashMap<>());
        displayFormat.getExt().put("wmin", format.getWmin());
      }
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
      if (imp.getExt().containsKey("ampren")) {
        displayPlacement.setAmpren((Integer) imp.getExt().get("ampren"));
      }
      if (imp.getExt().containsKey("ctype")) {
        displayPlacement.setCtype((List<Integer>) imp.getExt().get("ctype"));
      }
      if (imp.getExt().containsKey("unit")) {
        displayPlacement.setUnit((Integer) imp.getExt().get("unit"));
      }
      if (imp.getExt().containsKey("priv")) {
        displayPlacement.setPriv((Integer) imp.getExt().get("priv"));
      }
      if (imp.getExt().containsKey("event")) {
        displayPlacement.setEvent((EventSpec) imp.getExt().get("event"));
      }
    }
    if (nonNull(nat)) {
      if (nonNull(nat.getRequest())) {
        NativeRequest nativeRequest = null;
        if (nat.getRequest() instanceof String) {
          String nativeRequestString = (String) nat.getRequest();
          try {
            nativeRequest = JacksonObjectMapper.getMapper().readValue(nativeRequestString,
              NativeRequest.class);
          } catch (IOException e) {
            e.printStackTrace();
          }
        } else {
          nativeRequest = JacksonObjectMapper.getMapper().convertValue(nat.getRequest(),
            NativeRequest.class);
        }
        if (nonNull(nativeRequest) && nonNull(nativeRequest.getNativeRequestBody())) {
          displayPlacement.setPtype(nativeRequest.getNativeRequestBody().getPlcmttype());
          displayPlacement.setContext(nativeRequest.getNativeRequestBody().getContext());
          displayPlacement.setNativefmt(nativeConverter.map(nativeRequest.getNativeRequestBody()));
          if (nonNull(nat.getExt()) && nonNull(displayPlacement.getNativefmt())) {
            if (isNull(displayPlacement.getNativefmt().getExt())) {
              displayPlacement.getNativefmt().setExt(new HashMap<>());
            }
            displayPlacement.getNativefmt().getExt().putAll(nat.getExt());
          }
        }
      }
    }
  }
}
