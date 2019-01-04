package net.media.converters.request24toRequest30;

import lombok.AllArgsConstructor;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb24.request.Source;
import net.media.openrtb24.request.User;
import net.media.openrtb3.Context;
import net.media.openrtb3.Dooh;
import net.media.openrtb3.Item;
import net.media.openrtb3.Request;
import net.media.utils.ListToListConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rajat.go on 03/01/19.
 */
@AllArgsConstructor
public class BidRequestToRequestConverter implements Converter<BidRequest, Request> {

  Converter<Imp, Item> impItemConverter;
  Converter<BidRequest, Context> bidRequestContextConverter;
  Converter<Source, net.media.openrtb3.Source> source24Source3Converter;

  private String bidRequestUserCustomdata(BidRequest bidRequest) {
    if ( bidRequest == null ) {
      return null;
    }
    User user = bidRequest.getUser();
    if ( user == null ) {
      return null;
    }
    String customdata = user.getCustomdata();
    if ( customdata == null ) {
      return null;
    }
    return customdata;
  }

  @Override
  public Request map(BidRequest source, Config config) {
    if ( source == null ) {
      return null;
    }

    Request request = new Request();

    request.setContext( bidRequestContextConverter.map( source, config ) );
    request.setItem( ListToListConverter.convert( source.getImp(), impItemConverter, config ) );
    request.setPack( source.getAllimps() );
    String customdata = bidRequestUserCustomdata( source );
    if ( customdata != null ) {
      request.setCdata( customdata );
    }
    request.setId( source.getId() );
    request.setTest( source.getTest() );
    request.setTmax( source.getTmax() );
    request.setAt( source.getAt() );
    List<String> list1 = source.getCur();
    if ( list1 != null ) {
      request.setCur( new ArrayList<String>( list1 ) );
    }
    request.setSource( source24Source3Converter.map(source.source, config ));
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      request.setExt( new HashMap<String, Object>( map ) );
    }

    inhance( source, request, config );

    return request;
  }

  @Override
  public void inhance(BidRequest source, Request target, Config config) {
    if(source == null)
      return;
    if (source.getWseat()!=null && source.getWseat().size()>0){
      target.setWseat(1);
      target.setSeat(source.getWseat());
    } else {
      target.setWseat(0);
      target.setSeat(source.getBseat());
    }
    if(target.getExt() == null)
      return;
    target.getExt().remove("cattax");
    if(source.getExt() == null)
      return;
    if(source.getExt().containsKey("dooh")) {
      if(target.getContext() == null)
        target.setContext(new Context());
      Dooh dooh = (Dooh)source.getExt().get("dooh");
      target.getContext().setDooh(dooh);
    }
  }
}
