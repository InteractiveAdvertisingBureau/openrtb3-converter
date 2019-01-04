package net.media.converters.request24toRequest30;

import lombok.AllArgsConstructor;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.*;
import net.media.openrtb3.Context;
import net.media.openrtb3.Restrictions;

@AllArgsConstructor
public class BidRequestToContextConverter implements Converter<BidRequest, Context> {

  private Converter<Regs, net.media.openrtb3.Regs> regsRegsConverter;
  private Converter<Site, net.media.openrtb3.Site> siteSiteConverter;
  private Converter<User, net.media.openrtb3.User> userUserConverter;
  private Converter<BidRequest, Restrictions> bidRequestRestrictionsConverter;
  private Converter<App, net.media.openrtb3.App> appAppConverter;
  private Converter<Device, net.media.openrtb3.Device> deviceDeviceConverter;

  @Override
  public Context map(BidRequest source, Config config) {
    if ( source == null ) {
      return null;
    }

    Context context = new Context();

    context.setRegs( regsRegsConverter.map( source.getRegs(), config ) );
    context.setSite( siteSiteConverter.map( source.getSite(), config ) );
    context.setUser( userUserConverter.map( source.getUser(), config ) );
    context.setRestrictions( bidRequestRestrictionsConverter.map( source, config ) );
    context.setApp( appAppConverter.map( source.getApp(), config ) );
    context.setDevice( deviceDeviceConverter.map( source.getDevice(), config ) );

    return context;
  }

  @Override
  public void inhance(BidRequest source, Context target, Config config) {

  }
}
