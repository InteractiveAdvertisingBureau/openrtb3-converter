package net.media.converters.request30toRequest24;

import lombok.AllArgsConstructor;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb24.request.User;
import net.media.openrtb3.*;
import net.media.utils.CollectionUtils;
import net.media.utils.ListToListConverter;

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
  public BidRequest map(Request source, Config config) {
    if ( source == null ) {
      return null;
    }

    BidRequest bidRequest = new BidRequest();

    if(source.getContext() != null) {

      if (source.getContext().getUser() != null) {
        if (bidRequest.getUser() == null) {
          bidRequest.setUser(userUserConverter.map(source.getContext().getUser(), config));
        }
      } else {
        bidRequest.setUser(null);
      }
      if (source.getCdata() != null) {
        if (bidRequest.getUser() == null) {
          bidRequest.setUser(new User());
        }
        requestUserConverter.inhance(source, bidRequest.getUser(), config);
      }

      App app = source.getContext().getApp();
      if ( app != null ) {
        bidRequest.setApp( appAppConverter.map( app, config ) );
      }

      Regs regs = source.getContext().getRegs();
      if ( regs != null ) {
        bidRequest.setRegs( regsRegsConverter.map( regs, config ) );
      }

      Site site = source.getContext().getSite();
      if ( site != null ) {
        bidRequest.setSite( siteSiteConverter.map( site, config ) );
      }

      if(source.getContext().getRestrictions() != null) {
        bidRequest.setBapp( source.getContext().getRestrictions().getBapp() );
        bidRequest.setBcat( source.getContext().getRestrictions().getBcat() );
        bidRequest.setBadv( source.getContext().getRestrictions().getBadv() );
      }

      Device device = source.getContext().getDevice();
      if ( device != null ) {
        bidRequest.setDevice( deviceDeviceConverter.map( device, config ) );
      }
    }
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      bidRequest.setExt( new HashMap<String, Object>( map ) );
    }
    bidRequest.setAllimps( source.getPack() );
    bidRequest.setImp( ListToListConverter.convert( source.getItem(), itemImpConverter, config ) );
    if (!CollectionUtils.isEmpty(bidRequest.getImp())) {
      if (nonNull(source.getContext()) && nonNull(source.getContext().getRestrictions())) {
        for (Imp imp : bidRequest.getImp()) {
          if (nonNull(imp.getBanner())) {
            if (nonNull(source.getContext().getRestrictions().getBattr())) {
              imp.getBanner().setBattr(new HashSet<>(source.getContext().getRestrictions().getBattr
                ()));
            }
          }
          if (nonNull(imp.getVideo())) {
            if (nonNull(source.getContext().getRestrictions().getBattr())) {
              imp.getVideo().setBattr(new HashSet<>(source.getContext().getRestrictions().getBattr
                ()));
            }
          }
          if (nonNull(imp.getNat())) {
            if (nonNull(source.getContext().getRestrictions().getBattr())) {
              imp.getNat().setBattr(new HashSet<>(source.getContext().getRestrictions().getBattr
                ()));
            }
          }
        }
      }
    }
    bidRequest.setId( source.getId() );
    bidRequest.setAt( source.getAt() );
    bidRequest.setTest( source.getTest() );
    bidRequest.setTmax( source.getTmax() );
    bidRequest.setSource( sourceSourceConverter.map( source.getSource(), config ) );
    List<String> list1 = source.getCur();
    if ( list1 != null ) {
      bidRequest.setCur( new ArrayList<String>( list1 ) );
    }

    inhance( source, bidRequest, config );

    return bidRequest;
  }

  @Override
  public void inhance(Request source, BidRequest target, Config config) {
    if(source == null)
      return;

    if(source.getWseat() != null) {

      if (source.getWseat() == 0) {
        target.setBseat(source.getSeat());
      } else {
        target.setWseat(source.getSeat());
      }
    }

    if(source.getContext() != null) {
      if (source.getContext().getRestrictions() != null) {

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
