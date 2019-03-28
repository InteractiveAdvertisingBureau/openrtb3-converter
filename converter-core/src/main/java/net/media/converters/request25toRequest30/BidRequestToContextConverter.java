package net.media.converters.request25toRequest30;

import net.media.OpenRtbConverterException;
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

public class BidRequestToContextConverter implements Converter<BidRequest, Context> {

  private Converter<Regs, net.media.openrtb3.Regs> regsRegsConverter;
  private Converter<Site, net.media.openrtb3.Site> siteSiteConverter;
  private Converter<User, net.media.openrtb3.User> userUserConverter;
  private Converter<BidRequest, Restrictions> bidRequestRestrictionsConverter;
  private Converter<App, net.media.openrtb3.App> appAppConverter;
  private Converter<Device, net.media.openrtb3.Device> deviceDeviceConverter;

  @java.beans.ConstructorProperties({"regsRegsConverter", "siteSiteConverter", "userUserConverter", "bidRequestRestrictionsConverter", "appAppConverter", "deviceDeviceConverter"})
  public BidRequestToContextConverter(Converter<Regs, net.media.openrtb3.Regs> regsRegsConverter, Converter<Site, net.media.openrtb3.Site> siteSiteConverter, Converter<User, net.media.openrtb3.User> userUserConverter, Converter<BidRequest, Restrictions> bidRequestRestrictionsConverter, Converter<App, net.media.openrtb3.App> appAppConverter, Converter<Device, net.media.openrtb3.Device> deviceDeviceConverter) {
    this.regsRegsConverter = regsRegsConverter;
    this.siteSiteConverter = siteSiteConverter;
    this.userUserConverter = userUserConverter;
    this.bidRequestRestrictionsConverter = bidRequestRestrictionsConverter;
    this.appAppConverter = appAppConverter;
    this.deviceDeviceConverter = deviceDeviceConverter;
  }

  @Override
  public Context map(BidRequest source, Config config) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    Context context = new Context();

    enhance( source, context, config );

    return context;
  }

  @Override
  public void enhance(BidRequest source, Context target, Config config) throws OpenRtbConverterException {
    if(source == null)
      return;
    target.setRegs( regsRegsConverter.map( source.getRegs(), config ) );
    target.setSite( siteSiteConverter.map( source.getSite(), config ) );
    target.setUser( userUserConverter.map( source.getUser(), config ) );
    target.setRestrictions( bidRequestRestrictionsConverter.map( source, config ) );
    target.setApp( appAppConverter.map( source.getApp(), config ) );
    target.setDevice( deviceDeviceConverter.map( source.getDevice(), config ) );
  }
}
