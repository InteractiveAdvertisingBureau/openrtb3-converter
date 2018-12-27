package net.media.mapper;

import net.media.openrtb24.request.Asset;
import net.media.openrtb24.request.Audio;
import net.media.openrtb24.request.Banner;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Format;
import net.media.openrtb24.request.Imp;
import net.media.openrtb24.request.NativeImage;
import net.media.openrtb24.request.NativeRequestBody;
import net.media.openrtb24.request.NativeTitle;
import net.media.openrtb24.request.NativeVideo;
import net.media.openrtb24.request.Site;
import net.media.openrtb24.request.Video;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb3.Ad;
import net.media.openrtb3.AssetFormat;
import net.media.openrtb3.AudioPlacement;
import net.media.openrtb3.Bid;
import net.media.openrtb24.request.Native;
import net.media.openrtb3.Companion;
import net.media.openrtb3.Deal;
import net.media.openrtb3.Display;
import net.media.openrtb3.DisplayFormat;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.EventSpec;
import net.media.openrtb3.Ext;
import net.media.openrtb3.ImageAssetFormat;
import net.media.openrtb3.Item;
import net.media.openrtb3.NativeFormat;
import net.media.openrtb3.Request;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;
import net.media.openrtb3.Source;
import net.media.openrtb3.TitleAssetFormat;
import net.media.openrtb3.VideoAsset;
import net.media.openrtb3.VideoPlacement;
import net.media.openrtb3.enums.SizeUnit;
import net.media.util.FirstElement;
import net.media.util.IterableNonInterableUtil;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static java.util.Objects.nonNull;

@Mapper(uses = BidRequestToRequestMapper.class)
public interface OpenRtb24To3Mapper {

  OpenRtb24To3Mapper MAPPER = Mappers.getMapper(OpenRtb24To3Mapper.class);

//  @Mappings({
//    @Mapping(source = "bidRequest.imp", target = "item"),
//    @Mapping(target = "wseat", ignore = true),
//  })
//  Request map(BidRequest bidRequest);
//
//  @Mappings({
//    @Mapping(source = "imp.id", target = "id"),
//    @Mapping(source = "imp.bidfloor", target = "flr"),
//    @Mapping(source = "imp.bidfloorcur", target = "flrcur"),
//    @Mapping(source = "imp.pmp.private_auction", target = "priv"),
//    @Mapping(source = "imp.pmp.deals", target = "deal"),
//    @Mapping(source = "imp.tagId", target = "spec.placement.tagid"),
//    @Mapping(source = "imp.displaymanager", target = "spec.placement.sdk"),
//    @Mapping(source = "imp.displaymanagerver", target = "spec.placement.sdkver"),
//    @Mapping(source = "imp.banner", target = "spec.placement.display"),
//    @Mapping(source = "imp.ext", target = "ext")
//  })
//  Item impToItem(Imp imp);
//
//  @AfterMapping
//  default void impToItemAfterMapping(Imp imp, @MappingTarget Item item) {
//    if (nonNull(imp.getExt()) && !imp.getExt().isEmpty() && nonNull(item.getSpec()) && nonNull
//      (item.getSpec().getPlacement())) {
//      item.getSpec().getPlacement().setSsai((Integer) imp.getExt().get("ssai"));
//      item.getSpec().getPlacement().setReward((Integer) imp.getExt().get("reward"));
//      item.getSpec().getPlacement().setAdmx((Integer) imp.getExt().get("admx"));
//      item.getSpec().getPlacement().setCurlx((Integer) imp.getExt().get("curlx"));
//    }
//  }
//
//  @Mappings({
//    @Mapping(source = "imp.id", target = "id"),
//    @Mapping(source = "imp.bidfloor", target = "flr"),
//    @Mapping(source = "imp.bidfloorcur", target = "flrcur"),
//    @Mapping(source = "imp.pmp.private_auction", target = "priv"),
//    @Mapping(source = "imp.pmp.deals", target = "deal"),
//    @Mapping(source = "imp.tagId", target = "spec.placement.tagid"),
//    @Mapping(source = "imp.displaymanager", target = "spec.placement.sdk"),
//    @Mapping(source = "imp.displaymanagerver", target = "spec.placement.sdkver"),
//    @Mapping(source = "bidRequest.wlang", target = "spec.placement.wlang"),
//    @Mapping(source = "imp.banner", target = "spec.placement.display"),
//    @Mapping(source = "imp.video", target = "spec.placement.video"),
//    @Mapping(source = "imp.audio", target = "spec.placement.audio"),
//    @Mapping(source = "imp.ext", target = "ext")
//  })
//  Item impToItem(Imp imp, BidRequest bidRequest);
//
//  @AfterMapping
//  default void impToItemAfterMapping(Imp imp, BidRequest bidRequest, @MappingTarget Item item) {
//    if (nonNull(imp.getExt()) && !imp.getExt().isEmpty() && nonNull(item.getSpec()) && nonNull
//      (item.getSpec().getPlacement())) {
//      item.getSpec().getPlacement().setSsai((Integer) imp.getExt().get("ssai"));
//      item.getSpec().getPlacement().setReward((Integer) imp.getExt().get("reward"));
//      item.getSpec().getPlacement().setAdmx((Integer) imp.getExt().get("admx"));
//      item.getSpec().getPlacement().setCurlx((Integer) imp.getExt().get("curlx"));
//    }
//  }
//
//  @Mappings({
//    @Mapping(source = "banner.mimes", target = "mime"),
//    @Mapping(source = "banner.format", target = "displayfmt")
//  })
//  DisplayPlacement impToDisplayPlacementMapping(Banner banner);
//
//  @Mappings({
//    @Mapping(source = "imp.instl", target = "instl"),
//    @Mapping(source = "imp.iframebuster", target = "ifrbust"),
//    @Mapping(source = "imp.clickbrowser", target = "clktype"),
//    @Mapping(source = "banner.mimes", target = "mime"),
//    @Mapping(target = "ext", source = "imp.ext"),
//    @Mapping(target = "priv", expression = "java(bidRequest.getSite() == null ? bidRequest" +
//      ".getSite().getPrivacypolicy() : bidRequest.getApp().getPrivacypolicy())"),
//    @Mapping(source = "banner.format", target = "displayfmt")
//  })
//  DisplayPlacement impToDisplayPlacementMapping(Banner banner, Imp imp, BidRequest bidRequest);
//
//  @AfterMapping
//  default void displayPlacementToImpAfterMapping(Banner banner, @MappingTarget DisplayPlacement
//    displayPlacement) {
//    displayPlacement.setAmpren((Integer) banner.getExt().get("ampren"));
//  }
//
//  @AfterMapping
//  default void displayPlacementToImpAfterMapping(Banner banner, Imp imp, BidRequest bidRequest,
//                                                 @MappingTarget DisplayPlacement displayPlacement) {
//    if (nonNull(imp.getExt()) && !imp.getExt().isEmpty() && nonNull(displayPlacement)) {
//      displayPlacement.setAmpren((Integer) imp.getExt().get("ampren"));
//      displayPlacement.setCtype((List<Integer>) imp.getExt().get
//        ("ctype"));
//      displayPlacement.setUnit((Integer) imp.getExt().get("unit"));
//      displayPlacement.setEvent((EventSpec) imp.getExt().get("event"));
//    }
//  }
//
//  @Mappings({
//    @Mapping(target = "ext", ignore = true),
//  })
//  DisplayFormat map(Format format);
//
//  @Mappings({
//    @Mapping(source = "imp.banner.expdir", target = "expdir"),
//    @Mapping(target = "ext", ignore = true),
//  })
//  DisplayFormat map(Format format, Imp imp, BidRequest bidRequest);
//
//  @Mappings({
//    @Mapping(target = "ext", ignore = true),
//    @Mapping(source = "video.placement", target = "ptype"),
//    @Mapping(source = "video.startdelay", target = "delay"),
//    @Mapping(source = "video.playbackmethod", target = "playmethod", qualifiedBy = FirstElement
//      .class),
//    @Mapping(source = "video.playbackend", target = "playend"),
//    @Mapping(source = "video.mimes", target = "mime"),
//    @Mapping(source = "video.minduration", target = "mindur"),
//    @Mapping(source = "video.maxduration", target = "maxdur"),
//    @Mapping(source = "video.maxextended", target = "maxext"),
//    @Mapping(source = "video.minbitrate", target = "minbitr"),
//    @Mapping(source = "video.maxbitrate", target = "maxbitr"),
//    @Mapping(source = "video.linearity", target = "linear"),
//    @Mapping(source = "video.boxingallowed", target = "boxing"),
//    @Mapping(source = "video.companiontype", target = "comptype"),
//    @Mapping(source = "video.protocols", target = "ctype"),
//    @Mapping(source = "video.companionad", target = "comp")
//  })
//  VideoPlacement map(Video video);
//
//  @Mappings({
//    @Mapping(target = "ext", ignore = true),
//    @Mapping(source = "video.placement", target = "ptype"),
//    @Mapping(source = "video.startdelay", target = "delay"),
//    @Mapping(source = "video.playbackmethod", target = "playmethod", qualifiedBy = FirstElement
//      .class),
//    @Mapping(source = "video.playbackend", target = "playend"),
//    @Mapping(source = "imp.clickbrowser", target = "clktype"),
//    @Mapping(source = "video.mimes", target = "mime"),
//    @Mapping(source = "video.minduration", target = "mindur"),
//    @Mapping(source = "video.maxduration", target = "maxdur"),
//    @Mapping(source = "video.maxextended", target = "maxext"),
//    @Mapping(source = "video.minbitrate", target = "minbitr"),
//    @Mapping(source = "video.maxbitrate", target = "maxbitr"),
//    @Mapping(source = "video.linearity", target = "linear"),
//    @Mapping(source = "video.boxingallowed", target = "boxing"),
//    @Mapping(source = "video.companiontype", target = "comptype"),
//    @Mapping(source = "video.protocols", target = "ctype"),
//    @Mapping(source = "video.companionad", target = "comp")
//  })
//  VideoPlacement map(Video video, Imp imp, BidRequest bidRequest);
//
//  @AfterMapping
//  default void videoPlacementToImpAfterMapping(Video video, Imp imp, BidRequest bidRequest,
//                                                 @MappingTarget Item item) {
//    if (nonNull(imp.getExt()) && !imp.getExt().isEmpty() && nonNull(item.getSpec()) && nonNull
//      (item.getSpec().getPlacement()) && nonNull(item.getSpec().getPlacement().getVideo())) {
//      item.getSpec().getPlacement().getVideo().setUnit((Integer) imp.getExt().get("unit"));
//    }
//    item.getSpec().getPlacement().getVideo().setMaxseq((Integer) video.getExt().get("maxseq"));
//  }
//
//  @Mappings({
//    @Mapping(source = "banner", target = "display"),
//    @Mapping(source = "banner.vcm", target = "vcm"),
//  })
//  Companion map (Banner banner);
//
//  @Mappings({
//    @Mapping(target = "ext", ignore = true),
//    @Mapping(source = "audio.mimes", target = "mime"),
//    @Mapping(source = "audio.startdelay", target = "delay"),
//    @Mapping(source = "audio.minduration", target = "mindur"),
//    @Mapping(source = "audio.maxduration", target = "maxdur"),
//    @Mapping(source = "audio.companiontype", target = "comptype"),
//    @Mapping(source = "audio.minduration", target = "minbitr"),
//    @Mapping(source = "audio.maxduration", target = "maxbitr"),
//    @Mapping(source = "audio.protocols", target = "ctype"),
//    @Mapping(source = "audio.maxextended", target = "maxext"),
//    @Mapping(source = "audio.companionad", target = "comp")
//  })
//  AudioPlacement map(Audio audio, Imp imp, BidRequest bidRequest);
//
//  @AfterMapping
//  default void audioPlacementToImpAfterMapping(Audio audio, Imp imp, BidRequest bidRequest,
//                                               @MappingTarget Item item) {
//    if (nonNull(audio) && nonNull(audio.getExt()) && nonNull(item) && nonNull(item.getSpec()) &&
//      nonNull(item.getSpec().getPlacement()) && nonNull(item.getSpec().getPlacement().getAudio())) {
//      item.getSpec().getPlacement().getAudio().setSkip((Integer) audio.getExt().get("skip"));
//      item.getSpec().getPlacement().getAudio().setSkipmin((Integer) audio.getExt().get("skipmin"));
//      item.getSpec().getPlacement().getAudio().setSkipafter((Integer) audio.getExt().get
//        ("skipafter"));
//      item.getSpec().getPlacement().getAudio().setPlaymethod((Integer) audio.getExt().get
//        ("playmethod"));
//      item.getSpec().getPlacement().getAudio().setPlayend((Integer) audio.getExt().get
//        ("playend"));
//    }
//  }

  @Mappings({
    @Mapping(source = "nativeRequestBody.assets", target = "asset"),
    @Mapping(source = "nativeRequestBody.ext", target = "ext"),
  })
  NativeFormat map(NativeRequestBody nativeRequestBody, Imp imp, BidRequest bidRequest);

  @Mappings({
    @Mapping(source = "required", target = "req"),
    @Mapping(source = "asset.video", target = "video"),
  })
  AssetFormat map(Asset asset);

  @Mappings({
    @Mapping(source = "mimes", target = "mime"),
  })
  ImageAssetFormat map(NativeImage nativeImage);

  @Mappings({
    @Mapping(source = "nativeVideo.mimes", target = "mime"),
    @Mapping(source = "nativeVideo.minduration", target = "mindur"),
    @Mapping(source = "nativeVideo.maxduration", target = "maxdur"),
    @Mapping(source = "nativeVideo.protocols", target = "ctype"),
  })
  VideoPlacement map(NativeVideo nativeVideo);

//  @AfterMapping
//  default void afterMapping(Native nat, Imp imp, @MappingTarget DisplayPlacement displayPlacement) {
//    if (nonNull(nat)) {
//      if (nonNull(nat.getRequest())) {
//        if (nat.getRequest() instanceof NativeRequestBody) {
//          NativeRequestBody nativeRequestBody = (NativeRequestBody) nat.getRequest();
//          displayPlacement.setPtype(nativeRequestBody.getPlcmttype());
//          displayPlacement.setContext(nativeRequestBody.getContext());
//          displayPlacement.setNativefmt(new NativeFormat());
//        }
//      }
//    }
//  }


//  @Mappings({
//    @Mapping(source = "deal.bidFloor", target = "flr"),
//    @Mapping(source = "deal.bidFloorCur", target = "flrcur")
//  })
//  Deal map(net.media.openrtb24.request.Deal deal);

//  @Mappings({
//  })
//  Response map(BidResponse bidResponse);
//
//  @Mappings({
//    @Mapping(source = "seat", target = "bidResponse.seat"),
//    @Mapping(source = "package", target = "bidResponse.group"),
//  })
//  Seatbid seatMapper(BidResponse bidResponse);
//
//  @Mappings({
//    @Mapping(source = "purl", target = "nurl"),
//  })
//  Bid map(net.media.openrtb24.response.Bid bid);
//
//  @Mappings({
//    @Mapping(source = "id", target = "crid"),
//    @Mapping(source = "lang", target = "language"),
//    @Mapping(source = "lang", target = "language"),
//  })
//  Ad adMapper(net.media.openrtb24.response.Bid bid);



  public static void main(String[] args) {
    BidRequest bidRequest = new BidRequest();
    Imp imp = new Imp();
    imp.setId("1");
    imp.setBidfloor(1.4);
    bidRequest.setImp(Arrays.asList(imp));
    Request request = BidRequestToRequestMapper.REQUEST_MAPPER.map(bidRequest);
    imp.setId("2");
//    MAPPER.updateRequestFromBidRequest(bidRequest, request);
    System.out.println("a");
  }
}
