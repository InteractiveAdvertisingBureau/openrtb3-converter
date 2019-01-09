package net.media.converters.request30toRequest25;

import lombok.AllArgsConstructor;
import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb24.request.User;
import net.media.openrtb3.*;
import net.media.utils.CollectionUtils;
import net.media.utils.ListToListConverter;
import net.media.utils.Utils;

import java.util.*;

import static java.util.Objects.nonNull;

@AllArgsConstructor
public class RequestToBidRequestConverter implements Converter<Request, BidRequest> {

  private Converter<net.media.openrtb3.User, User> userUserConverter;
  private Converter<Request, User> requestUserConverter;
  private Converter<App, net.media.openrtb24.request.App> appAppConverter;
  private Converter<Regs, net.media.openrtb24.request.Regs> regsRegsConverter;
  private Converter<Site, net.media.openrtb24.request.Site> siteSiteConverter;
  private Converter<Device, net.media.openrtb24.request.Device> deviceDeviceConverter;
  private Converter<Source, net.media.openrtb24.request.Source> sourceSourceConverter;
  private Converter<Item, net.media.openrtb24.request.Imp> itemImpConverter;
  @Override
  public BidRequest map(Request source, Config config) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    BidRequest bidRequest = new BidRequest();

    inhance( source, bidRequest, config );

    return bidRequest;
  }

  @Override
  public void inhance(Request source, BidRequest target, Config config) throws OpenRtbConverterException {
    if(source == null)
      return;

    if(source.getContext() != null) {

      if (source.getContext().getUser() != null) {
        if (target.getUser() == null) {
          target.setUser(userUserConverter.map(source.getContext().getUser(), config));
        }
      } else {
        target.setUser(null);
      }
      if (source.getCdata() != null) {
        if (target.getUser() == null) {
          target.setUser(new User());
        }
        requestUserConverter.inhance(source, target.getUser(), config);
      }

      App app = source.getContext().getApp();
      if ( app != null ) {
        target.setApp( appAppConverter.map( app, config ) );
      }

      Regs regs = source.getContext().getRegs();
      if ( regs != null ) {
        target.setRegs( regsRegsConverter.map( regs, config ) );
      }

      Site site = source.getContext().getSite();
      if ( site != null ) {
        target.setSite( siteSiteConverter.map( site, config ) );
      }

      if(source.getContext().getRestrictions() != null) {
        target.setBapp( Utils.copyList(source.getContext().getRestrictions().getBapp(), config) );
        target.setBcat( Utils.copySet(source.getContext().getRestrictions().getBcat(), config) );
        target.setBadv( Utils.copySet(source.getContext().getRestrictions().getBadv(), config) );
        if (source.getContext().getRestrictions().getCattax() != null) {
          if (target.getExt() == null)
            target.setExt(new HashMap<>());
          target.getExt().put("cattax", source.getContext().getRestrictions().getCattax());
        }

        if (source.getContext().getRestrictions().getExt() != null) {
          if (target.getExt() == null)
            target.setExt(new HashMap<>());
          target.getExt().put("restrictionsExt", source.getContext().getRestrictions().getExt());
        }
      }

      Device device = source.getContext().getDevice();
      if ( device != null ) {
        target.setDevice( deviceDeviceConverter.map( device, config ) );
      }
    }
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( new HashMap<String, Object>( map ) );
    }
    target.setAllimps( source.getPack() );
    target.setImp( ListToListConverter.convert( source.getItem(), itemImpConverter, config ) );
    if (!CollectionUtils.isEmpty(target.getImp())) {
      if (nonNull(source.getContext()) && nonNull(source.getContext().getRestrictions())) {
        for (Imp imp : target.getImp()) {
          if (nonNull(imp.getBanner())) {
            if (nonNull(source.getContext().getRestrictions().getBattr())) {
              imp.getBanner().setBattr(Utils.copySet(source.getContext().getRestrictions().getBattr
                (), config));
            }
          }
          if (nonNull(imp.getVideo())) {
            if (nonNull(source.getContext().getRestrictions().getBattr())) {
              imp.getVideo().setBattr(Utils.copySet(source.getContext().getRestrictions().getBattr
                (), config));
            }
          }
          if (nonNull(imp.getNat())) {
            if (nonNull(source.getContext().getRestrictions().getBattr())) {
              imp.getNat().setBattr(Utils.copySet(source.getContext().getRestrictions().getBattr
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
    target.setSource( sourceSourceConverter.map( source.getSource(), config ) );
    List<String> list1 = source.getCur();
    if ( list1 != null ) {
      target.setCur( Utils.copyList( list1, config ) );
    }

    if(source.getWseat() != null) {

      if (source.getWseat() == 0) {
        target.setBseat(Utils.copySet(source.getSeat(), config));
        target.setBseat(Utils.copySet(source.getSeat(), config));
      } else {
        target.setWseat(Utils.copySet(source.getSeat(), config));
      }
    }

    if(source.getItem() != null && source.getItem().size() > 0) {
      Set<String> wlang = new HashSet<>();
      for(Item item : source.getItem()) {
        if(item.getSpec() != null && item.getSpec().getPlacement() != null && item.getSpec().getPlacement().getWlang() != null)
          wlang.addAll(item.getSpec().getPlacement().getWlang());
      }
      target.setWlang(new ArrayList<>( wlang ));
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
