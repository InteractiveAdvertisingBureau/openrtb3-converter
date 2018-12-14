package net.media.mapper;

import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb3.Item;
import net.media.openrtb3.Request;
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
}
