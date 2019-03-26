package net.media.mapper;


import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.request.Format;
import net.media.openrtb25.request.Imp;
import net.media.openrtb25.request.Native;
import net.media.openrtb25.request.NativeRequest;
import net.media.openrtb3.DisplayFormat;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.EventSpec;
import net.media.openrtb3.NativeFormat;

import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 26/12/18.
 */

@Mapper
public interface BannerToDisplayPlacementMapper {

  @Mappings({
    @Mapping(source = "banner.mimes", target = "mime"),
    @Mapping(source = "banner.format", target = "displayfmt")
  })
  DisplayPlacement displayPlacementToImpMap(Banner banner);

  @InheritInverseConfiguration
  Banner bannerToDisplayPlacement(DisplayPlacement displayPlacement);

  @Mappings({
    @Mapping(source = "imp.instl", target = "instl"),
    @Mapping(source = "imp.iframebuster", target = "ifrbust"),
    @Mapping(source = "imp.clickbrowser", target = "clktype"),
    @Mapping(source = "banner.mimes", target = "mime"),
    @Mapping(target = "ext", source = "imp.ext"),
    @Mapping(target = "api", expression = "java(banner == null ? banner.getApi() : nat.getApi())"),
    @Mapping(source = "banner.format", target = "displayfmt")
  })
  DisplayPlacement displayPlacementToImpMap(Banner banner, Native nat, Imp imp, BidRequest
    bidRequest);

  @AfterMapping
  default void displayPlacementToImpAfterMapping(Banner banner, Native nat, @MappingTarget
    DisplayPlacement displayPlacement) {
    displayPlacement.setAmpren((Integer) banner.getExt().get("ampren"));
  }

  @AfterMapping
  default void displayPlacementToImpAfterMapping(Banner banner, Native nat, Imp imp, BidRequest
    bidRequest, @MappingTarget DisplayPlacement displayPlacement) {
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
//          try {
//          } catch (IOException e) {
//            e.printStackTrace();
//          }
        }
        if (nonNull(nativeRequest) && nonNull(nativeRequest.getNativeRequestBody())) {
          displayPlacement.setPtype(nativeRequest.getNativeRequestBody().getPlcmttype());
          displayPlacement.setContext(nativeRequest.getNativeRequestBody().getContext());
          displayPlacement.setNativefmt(new NativeFormat());
        }
      }
    }
  }

  @Mappings({
    @Mapping(target = "ext", ignore = true),
  })
  DisplayFormat map(Format format);

  @InheritInverseConfiguration
  Format map(DisplayFormat displayFormat);

  @Mappings({
    @Mapping(source = "imp.banner.expdir", target = "expdir"),
    @Mapping(source = "format.ext", target = "ext"),
    @Mapping(source = "format.h", target = "h"),
    @Mapping(source = "format.w", target = "w"),
  })
  DisplayFormat map(Format format, Banner banner, Imp imp, BidRequest bidRequest);
}
