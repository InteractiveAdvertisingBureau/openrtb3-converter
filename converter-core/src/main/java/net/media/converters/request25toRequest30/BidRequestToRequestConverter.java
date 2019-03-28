package net.media.converters.request25toRequest30;

import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.request.Imp;
import net.media.openrtb25.request.Source;
import net.media.openrtb25.request.User;
import net.media.openrtb3.Context;
import net.media.openrtb3.Dooh;
import net.media.openrtb3.Item;
import net.media.openrtb3.Placement;
import net.media.openrtb3.Request;
import net.media.openrtb3.Spec;
import net.media.utils.CollectionUtils;
import net.media.utils.ListToListConverter;
import net.media.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rajat.go on 03/01/19.
 */
public class BidRequestToRequestConverter implements Converter<BidRequest, Request> {

  Converter<Imp, Item> impItemConverter;
  Converter<BidRequest, Context> bidRequestContextConverter;
  Converter<Source, net.media.openrtb3.Source> source25Source3Converter;

  @java.beans.ConstructorProperties({"impItemConverter", "bidRequestContextConverter", "source25Source3Converter"})
  public BidRequestToRequestConverter(Converter<Imp, Item> impItemConverter, Converter<BidRequest, Context> bidRequestContextConverter, Converter<Source, net.media.openrtb3.Source> source25Source3Converter) {
    this.impItemConverter = impItemConverter;
    this.bidRequestContextConverter = bidRequestContextConverter;
    this.source25Source3Converter = source25Source3Converter;
  }

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
  public Request map(BidRequest source, Config config) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    Request request = new Request();

    enhance( source, request, config );

    return request;
  }

  @Override
  public void enhance(BidRequest source, Request target, Config config) throws OpenRtbConverterException {
    if(source == null)
      return;
    target.setContext( bidRequestContextConverter.map( source, config ) );
    target.setItem( ListToListConverter.convert( source.getImp(), impItemConverter, config ) );
    target.setPack( source.getAllimps() );
    String customdata = bidRequestUserCustomdata( source );
    if ( customdata != null ) {
      target.setCdata( customdata );
    }
    target.setId( source.getId() );
    target.setTest( source.getTest() );
    target.setTmax( source.getTmax() );
    target.setAt( source.getAt() );
    List<String> list1 = Utils.copyList(source.getCur(), config);
    if ( list1 != null ) {
      target.setCur( new ArrayList<String>( list1 ) );
    }
    target.setSource( source25Source3Converter.map(source.source, config ));
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( Utils.copyMap(map, config) );
    }

    if (!CollectionUtils.isEmpty(target.getItem())) {
      for (Item item : target.getItem()) {
        if ( item.getSpec() == null ) {
          item.setSpec( new Spec() );
        }
        bidRequestToSpec( source, item.getSpec(), config );
      }
    }
    if (source.getWseat()!=null && source.getWseat().size()>0){
      target.setWseat(1);
      target.setSeat(Utils.copySet(source.getWseat(), config));
    } else {
      target.setWseat(0);
      target.setSeat(Utils.copySet(source.getBseat(), config));
    }
    if(target.getExt() == null)
      return;
    target.getExt().remove("cattax");
    target.getExt().remove("restrictionsExt");
    if(source.getExt() == null)
      return;
    if(source.getExt().containsKey("dooh")) {
      if(target.getContext() == null)
        target.setContext(new Context());
      Dooh dooh = (Dooh)source.getExt().get("dooh");
      target.getContext().setDooh(dooh);
    }
  }

  private void bidRequestToSpec(BidRequest bidRequest, Spec mappingTarget, Config config) {
    if ( bidRequest == null ) {
      return;
    }

    if ( mappingTarget.getPlacement() == null ) {
      mappingTarget.setPlacement( new Placement() );
    }
    bidRequestToPlacement( bidRequest, mappingTarget.getPlacement(), config );
  }

  private void bidRequestToPlacement(BidRequest bidRequest, Placement mappingTarget, Config config) {
    if ( bidRequest == null ) {
      return;
    }

    if ( mappingTarget.getWlang() != null ) {
      if ( bidRequest.getWlang() != null ) {
        mappingTarget.getWlang().clear();
        mappingTarget.getWlang().addAll( Utils.copyList(bidRequest.getWlang(), config) );
      }
      else {
        mappingTarget.setWlang( null );
      }
    }
    else {
      if ( bidRequest.getWlang() != null ) {
        mappingTarget.setWlang( Utils.copyList( bidRequest.getWlang(), config ) );
      }
    }
  }
}
