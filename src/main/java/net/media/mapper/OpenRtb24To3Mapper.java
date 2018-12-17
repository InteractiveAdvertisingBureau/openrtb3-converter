package net.media.mapper;

import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Banner;
import net.media.openrtb3.Bid;
import net.media.openrtb3.Display;
import net.media.openrtb3.Item;
import net.media.openrtb3.Request;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;
import net.media.openrtb3.Source;
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
    @Mapping(source = "ext.ctype", target="ctype")
  })
  Display displayMapper(net.media.openrtb24.response.Bid bid);


  Banner bannerMapper(net.media.openrtb24.response.Bid bid);
}
