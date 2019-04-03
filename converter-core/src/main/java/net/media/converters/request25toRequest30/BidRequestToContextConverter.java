package net.media.converters.request25toRequest30;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.App;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.request.Device;
import net.media.openrtb25.request.Regs;
import net.media.openrtb25.request.Site;
import net.media.openrtb25.request.User;
import net.media.openrtb3.Context;
import net.media.openrtb3.Restrictions;
import net.media.utils.Provider;

public class BidRequestToContextConverter implements Converter<BidRequest, Context> {

  @Override
  public Context map(BidRequest source, Config config, Provider<Conversion, Converter> converterProvider) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    Context context = new Context();

    enhance( source, context, config, converterProvider );

    return context;
  }

  @Override
  public void enhance(BidRequest source, Context target, Config config, Provider<Conversion, Converter> converterProvider) throws OpenRtbConverterException {
    if(source == null || target == null)
      return;
    Converter<Regs, net.media.openrtb3.Regs> regsRegsConverter = converterProvider.fetch(new Conversion
            (Regs.class, net.media.openrtb3.Regs.class));
    Converter<Site, net.media.openrtb3.Site> siteSiteConverter = converterProvider.fetch(new Conversion
            (Site.class, net.media.openrtb3.Site.class));
    Converter<User, net.media.openrtb3.User> userUserConverter = converterProvider.fetch(new Conversion
            (User.class, net.media.openrtb3.User.class));
    Converter<BidRequest, Restrictions> bidRequestRestrictionsConverter = converterProvider.fetch(new Conversion
            (BidRequest.class, Restrictions.class));
    Converter<App, net.media.openrtb3.App> appAppConverter = converterProvider.fetch(new Conversion
            (App.class, net.media.openrtb3.App.class));
    Converter<Device, net.media.openrtb3.Device> deviceDeviceConverter = converterProvider.fetch(new Conversion
            (Device.class, net.media.openrtb3.Device.class));
    target.setRegs( regsRegsConverter.map( source.getRegs(), config, converterProvider ) );
    target.setSite( siteSiteConverter.map( source.getSite(), config, converterProvider ) );
    target.setUser( userUserConverter.map( source.getUser(), config, converterProvider ) );
    target.setRestrictions( bidRequestRestrictionsConverter.map( source, config, converterProvider ) );
    target.setApp( appAppConverter.map( source.getApp(), config, converterProvider ) );
    target.setDevice( deviceDeviceConverter.map( source.getDevice(), config, converterProvider ) );
  }
}
