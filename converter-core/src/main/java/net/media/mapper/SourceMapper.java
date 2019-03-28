package net.media.mapper;

import net.media.openrtb3.Source;

import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

public interface SourceMapper {

  @Mappings({

  })
  Source mapRtb3SourcetoRtb24Source(net.media.openrtb25.request.Source source);

  @AfterMapping
  default void afterMapping(@MappingTarget Source target, net.media.openrtb25.request.Source source) {

  }
}
