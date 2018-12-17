package net.media.mapper;

import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb24.request.Native;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Asset;
import net.media.openrtb3.Banner;
import net.media.openrtb3.Bid;
import net.media.openrtb3.DataAsset;
import net.media.openrtb3.Display;
import net.media.openrtb3.Event;
import net.media.openrtb3.ImageAsset;
import net.media.openrtb3.Item;
import net.media.openrtb3.LinkAsset;
import net.media.openrtb3.Request;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;
import net.media.openrtb3.Source;
import net.media.openrtb3.TitleAsset;
import net.media.openrtb3.VideoAsset;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

public interface OpenRtb24To3Mapper {

  @Mappings({
  })
  Request map(BidRequest request);

  @Mappings({
    @Mapping( source = "imp.bidfloor", target = "flr"),
    @Mapping(source = "imp.bidfloorcur", target = "flrcur"),
  })
  Item map(Imp imp);

  @Mappings({
    @Mapping(source="cdata", target = "ext.cdata"),
  })
  Response map(BidResponse bidResponse);

  @Mappings({
    @Mapping(source = "seat", target = "bidResponse.seat"),
    @Mapping(source = "package", target = "bidResponse.group"),
  })
  Seatbid seatMapper(BidResponse bidResponse);

  @Mappings({
    @Mapping(target = "purl", source = "nurl"),
    @Mapping(target = "deal", source = "dealid"),
    @Mapping(target = "tactic", source = "ext.tactic"),
    @Mapping(target = "burl", source = "ext.burl"),
    @Mapping(target = "lurl", source = "lurl"),
    @Mapping(target = "mid", source = "ext.mid"),
    @Mapping(target = "media", source = "ext.media")
  })
  Bid map(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(target = "id", source = "crid"),
    @Mapping(target = "lang", source = "language"),
    @Mapping(target = "cattax", source = "ext.cattax"),
    @Mapping(target = "secure", source = "ext.secure"),
    @Mapping(target = "mrating", source = "ext.mrating"),
    @Mapping(target = "init", source = "ext.init"),
    @Mapping(target = "lastmod", source = "ext.lastmod")
  })
  Ad adMapper(net.media.openrtb24.response.Bid bid);


  @Mappings({
    @Mapping(target="ctype", source = "ext.ctype"),
    @Mapping(target="mime", source = "ext.mime"),
    @Mapping(target="api", source = "ext.api"),
    @Mapping(target="wratio", source = "ext.wratio"),
    @Mapping(target="hratio", source = "ext.hratio"),
    @Mapping(target="priv", source = "ext.priv"),
    @Mapping(target="curl", source = "ext.curl"),
  })
  Display displayMapper(net.media.openrtb24.response.Bid bid);


  @Mappings({
    @Mapping(target = "img", source = "ext.img"),
    @Mapping(target = "link", source = "ext.link"),
  })
  Banner bannerMapper(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(target = "link", source = "ext.link"),
    @Mapping(target = "asset", source = "ext.asset"),
  })
  net.media.openrtb3.Native nativeMapper(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(target="id", source = "ext.assetFormatId"),
    @Mapping(target="req", source = "ext.req"),
  })
  Asset AssetMapper(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(target = "url", source = "ext.url"),
    @Mapping(target = "urlfb", source = "ext.urlfb"),
    @Mapping(target = "trkr", source = "ext.trkr")
  })
  LinkAsset linkAssetMapper(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(target = "text", source = "ext.text"),
    @Mapping(target = "len", source = "ext.len"),
  })
  TitleAsset titleAssetMapper(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(target = "url", source = "ext.url"),
    @Mapping(target = "type", source = "ext.imageAssetType"),
  })
  ImageAsset imageAssetMapper(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(target = "curl", source = "ext.curl")
  })
  VideoAsset videoAssetMapper(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(target = "value", source = "ext.dataValue"),
    @Mapping(target = "len", source = "ext.dataLen"),
    @Mapping(target = "type", source = "ext.type"),
  })
  DataAsset dataAssetMapper(net.media.openrtb24.response.Bid bid);

  @Mappings({
    @Mapping(target = "type", source = "ext.eventType"),
    @Mapping(target = "method", source = "ext.trackMethod"),
    @Mapping(target = "api", source = "ext.eventApi"),
    @Mapping(target = "url", source = "ext.evenUrl"),
    @Mapping(target = "cdata", source = "ext.eventCdata")

  })
  Event eventMapper(net.media.openrtb24.response.Bid bid);
}
