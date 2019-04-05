package net.media.converters.request30toRequest25;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.request.Imp;
import net.media.openrtb25.request.User;
import net.media.openrtb3.*;
import net.media.utils.CollectionUtils;
import net.media.utils.CollectionToCollectionConverter;
import net.media.utils.Provider;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.nonNull;

public class RequestToBidRequestConverter implements Converter<Request, BidRequest> {

  @Override
  public BidRequest map(Request source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    BidRequest bidRequest = new BidRequest();

    enhance( source, bidRequest, config, converterProvider );

    return bidRequest;
  }

  @Override
  public void enhance(Request source, BidRequest target, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if(source == null || target == null)
      return;

    Converter<net.media.openrtb3.User, User> userUserConverter =
      converterProvider.fetch(new Conversion<>(net.media.openrtb3.User.class, User.class));
    Converter<Request, User> requestUserConverter = converterProvider.fetch(new Conversion<>(Request.class, User.class));
    Converter<App, net.media.openrtb25.request.App> appAppConverter =
      converterProvider.fetch(new Conversion<>(App.class, net.media.openrtb25.request.App.class));
    Converter<Regs, net.media.openrtb25.request.Regs> regsRegsConverter =
      converterProvider.fetch(new Conversion<>(Regs.class, net.media.openrtb25.request.Regs.class));
    Converter<Site, net.media.openrtb25.request.Site> siteSiteConverter =
      converterProvider.fetch(new Conversion<>(Site.class, net.media.openrtb25.request.Site.class));
    Converter<Device, net.media.openrtb25.request.Device> deviceDeviceConverter =
      converterProvider.fetch(new Conversion<>(Device.class, net.media.openrtb25.request.Device.class));
    Converter<Source, net.media.openrtb25.request.Source> sourceSourceConverter =
      converterProvider.fetch(new Conversion<>(Source.class, net.media.openrtb25.request.Source.class));
    Converter<Item, net.media.openrtb25.request.Imp> itemImpConverter =
      converterProvider.fetch(new Conversion<>(Item.class, Imp.class));

    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt(Utils.copyMap(map, config));
    }

    if(source.getContext() != null) {

      if (source.getContext().getUser() != null) {
        if (target.getUser() == null) {
          target.setUser(userUserConverter.map(source.getContext().getUser(), config, converterProvider));
        }
      } else {
        target.setUser(null);
      }
      if (source.getCdata() != null) {
        if (target.getUser() == null) {
          target.setUser(new User());
        }
        requestUserConverter.enhance(source, target.getUser(), config, converterProvider);
      }

      App app = source.getContext().getApp();
      if ( app != null ) {
        target.setApp( appAppConverter.map( app, config, converterProvider ) );
      }

      Regs regs = source.getContext().getRegs();
      if ( regs != null ) {
        target.setRegs( regsRegsConverter.map( regs, config, converterProvider ) );
      }

      Site site = source.getContext().getSite();
      if ( site != null ) {
        target.setSite( siteSiteConverter.map( site, config, converterProvider ) );
      }

      if(source.getContext().getRestrictions() != null) {
        target.setBapp( Utils.copyCollection(source.getContext().getRestrictions().getBapp(), config) );
        target.setBcat( Utils.copyCollection(source.getContext().getRestrictions().getBcat(), config) );
        target.setBadv( Utils.copyCollection(source.getContext().getRestrictions().getBadv(), config) );
        if (source.getContext().getRestrictions().getCattax() != null) {
          if (target.getExt() == null)
            target.setExt(new HashMap<>());
          target.getExt().put("cattax", source.getContext().getRestrictions().getCattax());
        }

        if (source.getContext().getRestrictions().getExt() != null) {
          if (target.getExt() == null)
            target.setExt(new HashMap<>());
          Restrictions restrictions = new Restrictions();
          restrictions.setCattax(null);
          restrictions.setExt(source.getContext().getRestrictions().getExt());
          target.getExt().put("restrictions", restrictions);
        }
      }

      Device device = source.getContext().getDevice();
      if ( device != null ) {
        target.setDevice( deviceDeviceConverter.map( device, config, converterProvider ) );
      }
    }
    target.setAllimps( source.getPack() );
    target.setImp( CollectionToCollectionConverter.convert( source.getItem(), itemImpConverter,
      config, converterProvider ) );
    if (!CollectionUtils.isEmpty(target.getImp())) {
      if (nonNull(source.getContext()) && nonNull(source.getContext().getRestrictions())) {
        for (Imp imp : target.getImp()) {
          if (nonNull(imp.getBanner())) {
            if (nonNull(source.getContext().getRestrictions().getBattr())) {
              imp.getBanner().setBattr(Utils.copyCollection(source.getContext().getRestrictions().getBattr
                (), config));
            }
          }
          if (nonNull(imp.getVideo())) {
            if (nonNull(source.getContext().getRestrictions().getBattr())) {
              imp.getVideo().setBattr(Utils.copyCollection(source.getContext().getRestrictions().getBattr
                (), config));
            }
          }
          if (nonNull(imp.getAudio())) {
            if (nonNull(source.getContext().getRestrictions().getBattr())) {
              imp.getAudio().setBattr(Utils.copyCollection(source.getContext().getRestrictions().getBattr
                (), config));
            }
          }
          if (nonNull(imp.getNat())) {
            if (nonNull(source.getContext().getRestrictions().getBattr())) {
              imp.getNat().setBattr(Utils.copyCollection(source.getContext().getRestrictions().getBattr
                (), config));
            }
          }
        }
      }
    }
    target.setId( source.getId() );
    target.setAt( source.getAt() );
    target.setTest( source.getTest() );
    target.setTmax( source.getTmax() );
    target.setSource( sourceSourceConverter.map( source.getSource(), config, converterProvider ) );
    Collection<String> list1 = source.getCur();
    if ( list1 != null ) {
      target.setCur( Utils.copyCollection( list1, config ) );
    }

    if(source.getWseat() != null) {

      if (source.getWseat() == 0) {
        target.setBseat(Utils.copyCollection(source.getSeat(), config));
        target.setBseat(Utils.copyCollection(source.getSeat(), config));
      } else {
        target.setWseat(Utils.copyCollection(source.getSeat(), config));
      }
    }

    if(source.getItem() != null && source.getItem().size() > 0) {
      Collection<String> wlang = new HashSet<>();
      for(Item item : source.getItem()) {
        if(item.getSpec() != null && item.getSpec().getPlacement() != null && item.getSpec()
          .getPlacement().getWlang() != null) {
          wlang.addAll(item.getSpec().getPlacement().getWlang());
        }
      }
      target.setWlang(Utils.copyCollection(wlang, config));
    }

    if(target.getImp() != null) {
      for (Imp imp : target.getImp()) {
        if (imp.getBanner() != null)
          imp.getBanner().setBattr(source.getContext().getRestrictions().getBattr());
        if (imp.getVideo() != null)
          imp.getVideo().setBattr(source.getContext().getRestrictions().getBattr());
        if (imp.getNat() != null)
          imp.getNat().setBattr(source.getContext().getRestrictions().getBattr());
      }
    }

    if(source.getContext().getDooh() == null)
      return;

    if(target.getExt() == null)
      target.setExt(new HashMap<>());
    target.getExt().put("dooh", source.getContext().getDooh());
  }

}
