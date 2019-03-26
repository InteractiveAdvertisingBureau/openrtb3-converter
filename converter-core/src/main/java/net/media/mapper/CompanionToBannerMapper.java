package net.media.mapper;

import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.request.Imp;
import net.media.openrtb3.Companion;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Created by rajat.go on 26/12/18.
 */

@Mapper(uses = BannerToDisplayPlacementMapper.class)
public interface CompanionToBannerMapper {

  @Mappings({
    @Mapping(source = "banner", target = "display"),
    @Mapping(source = "banner.vcm", target = "vcm"),
    @Mapping(source = "banner.id", target = "id"),
    @Mapping(source = "banner.ext", target = "ext"),
  })
  Companion map(Banner banner, Imp imp, BidRequest bidRequest);

  @InheritInverseConfiguration
  Banner map (Companion companion);
}
