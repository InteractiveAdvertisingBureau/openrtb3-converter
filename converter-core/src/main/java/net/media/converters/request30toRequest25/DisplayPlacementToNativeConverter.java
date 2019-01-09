package net.media.converters.request30toRequest25;

import com.fasterxml.jackson.core.JsonProcessingException;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Native;
import net.media.openrtb25.request.NativeRequest;
import net.media.openrtb25.request.NativeRequestBody;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.NativeFormat;
import net.media.utils.JacksonObjectMapper;
import net.media.utils.Utils;

import java.util.HashMap;

import lombok.AllArgsConstructor;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@AllArgsConstructor
public class DisplayPlacementToNativeConverter implements Converter<DisplayPlacement, Native> {

  private Converter<NativeFormat, NativeRequestBody> nativeFormatNativeRequestBodyConverter;

  @Override
  public Native map(DisplayPlacement displayPlacement, Config config) throws OpenRtbConverterException {
    if (displayPlacement == null) {
      return null;
    }
    Native nat = new Native();
    inhance(displayPlacement, nat, config);
    if (isNull(nat.getNativeRequestBody())) {
      return null;
    }
    return nat;
  }

  @Override
  public void inhance(DisplayPlacement displayPlacement, Native nat, Config config) throws OpenRtbConverterException {
    if (isNull(displayPlacement) || isNull(nat)) {
      return;
    }
    NativeRequestBody nativeRequestBody = nativeFormatNativeRequestBodyConverter.map
      (displayPlacement.getNativefmt(), config);
    if (isNull(nativeRequestBody)) {
      return;
    }
    NativeRequest nativeRequest = new NativeRequest();
    nativeRequest.setNativeRequestBody(nativeRequestBody);
    if (nonNull(nativeRequest.getNativeRequestBody())) {
      nativeRequest.getNativeRequestBody().setContext(displayPlacement.getContext());
      nativeRequest.getNativeRequestBody().setPlcmttype(displayPlacement.getPtype());
      if (nonNull(displayPlacement.getExt())) {
        nativeRequest.getNativeRequestBody().setContextsubtype((Integer) displayPlacement.getExt
          ().get("contextsubtype"));
      }
    }
    nat.setApi(Utils.copyList(displayPlacement.getApi(), config));
    if (nonNull(displayPlacement.getExt())) {
      if (isNull(nat.getExt())) {
        nat.setExt(new HashMap<>());
      }
      nat.getExt().putAll(displayPlacement.getExt());
      nat.setVer((String) displayPlacement.getExt().get("nativeversion"));
      nat.getExt().remove("nativeversion");
    }
    if (config.getNativeRequestAsString()) {
      try {
        nat.setRequest(JacksonObjectMapper.getMapper().writeValueAsString(nativeRequest));
      } catch (JsonProcessingException e) {
        throw new OpenRtbConverterException(e);
      }
    } else {
      nat.setRequest(nativeRequest);
    }
    nat.setExt(Utils.copyMap(displayPlacement.getExt(), config));
  }
}
