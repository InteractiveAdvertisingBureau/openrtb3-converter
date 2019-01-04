package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.Native;
import net.media.openrtb24.request.NativeRequest;
import net.media.openrtb24.request.NativeRequestBody;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.NativeFormat;
import net.media.util.JacksonObjectMapper;

import java.io.IOException;
import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/01/19.
 */
public class NativeToDisplayPlacementConverter implements Converter<Native, DisplayPlacement> {

  private Converter<NativeRequestBody, NativeFormat> nativeRequestBodyNativeFormatConverter;

  public NativeToDisplayPlacementConverter(Converter<NativeRequestBody, NativeFormat>
                                             nativeRequestBodyNativeFormatConverter) {
    this.nativeRequestBodyNativeFormatConverter = nativeRequestBodyNativeFormatConverter;
  }

  @Override
  public DisplayPlacement map(Native nat, Config config) {
    if (isNull(nat)) {
      return null;
    }

    DisplayPlacement displayPlacement = new DisplayPlacement();
    inhance(nat, displayPlacement, config);
    return displayPlacement;
  }

  @Override
  public void inhance(Native nat, DisplayPlacement displayPlacement, Config config) {
    if (isNull(nat) || isNull(displayPlacement)) {
      return;
    }
    if (nonNull(nat.getRequest())) {
      NativeRequest nativeRequest = null;
      if (nat.getRequest() instanceof String) {
        String nativeRequestString = (String) nat.getRequest();
        try {
          nativeRequest = JacksonObjectMapper.getMapper().readValue(nativeRequestString,
            NativeRequest.class);
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        nativeRequest = JacksonObjectMapper.getMapper().convertValue(nat.getRequest(),
          NativeRequest.class);
      }
      if (nonNull(nativeRequest) && nonNull(nativeRequest.getNativeRequestBody())) {
        displayPlacement.setPtype(nativeRequest.getNativeRequestBody().getPlcmttype());
        displayPlacement.setContext(nativeRequest.getNativeRequestBody().getContext());
        displayPlacement.setNativefmt(nativeRequestBodyNativeFormatConverter.map(nativeRequest
          .getNativeRequestBody(), config));
        if (nonNull(nat.getExt()) && nonNull(displayPlacement.getNativefmt())) {
          if (isNull(displayPlacement.getNativefmt().getExt())) {
            displayPlacement.getNativefmt().setExt(new HashMap<>());
          }
          displayPlacement.getNativefmt().getExt().putAll(nat.getExt());
        }
      }
    }
  }
}
