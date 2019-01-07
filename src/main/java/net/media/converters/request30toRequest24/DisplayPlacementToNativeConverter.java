package net.media.converters.request30toRequest24;

import com.fasterxml.jackson.core.JsonProcessingException;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.mapper.DisplayConverter;
import net.media.openrtb24.request.Native;
import net.media.openrtb24.request.NativeRequest;
import net.media.openrtb24.request.NativeRequestBody;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.NativeFormat;
import net.media.util.JacksonObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@AllArgsConstructor
public class DisplayPlacementToNativeConverter implements Converter<DisplayPlacement, Native> {

  private Converter<NativeFormat, NativeRequestBody> nativeFormatNativeRequestBodyConverter;

  @Override
  public Native map(DisplayPlacement displayPlacement, Config config) {
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
  public void inhance(DisplayPlacement displayPlacement, Native nat, Config config) {
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
    }
    List<Integer> api = displayPlacement.getApi();
    if ( api != null ) {
      nat.setApi( new ArrayList<>( api ) );
    }
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

      }
    } else {
      nat.setRequest(nativeRequest);
    }
    Map<String, Object> map = displayPlacement.getExt();
    if ( map != null ) {
      nat.setExt( new HashMap<>( map ) );
    }
  }
}
