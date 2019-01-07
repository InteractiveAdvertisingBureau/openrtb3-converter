package net.media.mapper;

import net.media.openrtb24.request.BidRequest;
import net.media.openrtb3.Request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * Created by rajat.go on 25/12/18.
 */

@Mapper(uses = ImpToItemMapper.class)
public interface BidRequestToRequestMapper {

  BidRequestToRequestMapper REQUEST_MAPPER = Mappers.getMapper(BidRequestToRequestMapper.class);

  @Mappings({
    @Mapping(source = "bidRequest.imp", target = "item"),
    @Mapping(target = "wseat", ignore = true),
  })
  Request map(BidRequest bidRequest);
}
