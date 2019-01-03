package net.media.converters.response24toresponse30;

import net.media.config.Config;
import net.media.converters.Converter;

import net.media.openrtb24.response.nativeresponse.NativeResponse;
import net.media.openrtb3.Native;

/**
 * @author shiva.b
 */
public class Native24ToNative30Converter implements Converter<NativeResponse, Native>{
  @Override
  public Native map(NativeResponse source, Config config) {
    return null;
  }

  @Override
  public void inhance(NativeResponse source, Native target, Config config) {

  }
}
