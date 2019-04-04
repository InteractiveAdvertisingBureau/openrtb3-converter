package net.media.converters.request25toRequest30;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.Native;
import net.media.openrtb25.request.NativeRequest;
import net.media.openrtb25.request.NativeRequestBody;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.NativeFormat;
import net.media.utils.JacksonObjectMapper;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/01/19.
 */

public class NativeToDisplayPlacementConverter implements Converter<Native, DisplayPlacement> {

  @Override
  public DisplayPlacement map(Native nat, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if (isNull(nat)) {
      return null;
    }

    DisplayPlacement displayPlacement = new DisplayPlacement();
    enhance(nat, displayPlacement, config, converterProvider);
    return displayPlacement;
  }

  @Override
  public void enhance(Native nat, DisplayPlacement displayPlacement, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if (isNull(nat) || isNull(displayPlacement)) {
      return;
    }
    Converter<NativeRequestBody, NativeFormat> nativeRequestBodyNativeFormatConverter = converterProvider.fetch(new Conversion
            (NativeRequestBody.class, NativeFormat.class));
    displayPlacement.setApi(Utils.copyCollection(nat.getApi(), config));
    if (nonNull(nat.getRequest())) {
      NativeRequest nativeRequest = null;
      if (nat.getRequest() instanceof String) {
        String nativeRequestString = (String) nat.getRequest();
        try {
          nativeRequest = JacksonObjectMapper.getMapper().readValue(nativeRequestString,
            NativeRequest.class);
        } catch (IOException e) {
          throw new OpenRtbConverterException(e);
        }
      } else {
        try {
          nativeRequest = JacksonObjectMapper.getMapper().convertValue(nat.getRequest(),
            NativeRequest.class);
        } catch (IllegalArgumentException e) {
          throw new OpenRtbConverterException(e);
        }
      }

      if (nonNull(nativeRequest) && nonNull(nativeRequest.getNativeRequestBody())) {
        displayPlacement.setPtype(nativeRequest.getNativeRequestBody().getPlcmttype());
        displayPlacement.setContext(nativeRequest.getNativeRequestBody().getContext());
        displayPlacement.setNativefmt(nativeRequestBodyNativeFormatConverter.map(nativeRequest
          .getNativeRequestBody(), config, converterProvider));
        if (nonNull(nat.getExt())) {
          if (isNull(displayPlacement.getExt())) {
            displayPlacement.setExt(new HashMap<>());
          }
          displayPlacement.getExt().putAll(nat.getExt());
        }
        if (nonNull(nat.getExt())) {
          if (nat.getExt().containsKey("ctype")) {
            try {
              displayPlacement.setCtype(new ArrayList<>((List<Integer>) nat.getExt().get("ctype")));
            } catch (ClassCastException e) {
              throw new OpenRtbConverterException("error while typecasting ext for Native", e);
            }
            if (nonNull(displayPlacement.getNativefmt()) && nonNull(displayPlacement.getNativefmt()
              .getExt())) {
              displayPlacement.getNativefmt().getExt().remove("ctype");
            }
          }
        }
        if (nonNull(nat.getExt())) {
          if (nat.getExt().containsKey("priv")) {
            displayPlacement.setPriv((Integer) nat.getExt().get("priv"));
            if (nonNull(displayPlacement.getNativefmt()) && nonNull(displayPlacement.getNativefmt()
              .getExt())) {
              displayPlacement.getNativefmt().getExt().remove("priv");
            }
          }
        }
      }
    }
  }
}
