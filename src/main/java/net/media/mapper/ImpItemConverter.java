package net.media.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Deal;
import net.media.openrtb24.request.Imp;
import net.media.openrtb24.request.Pmp;
import net.media.openrtb24.request.Video;
import net.media.openrtb3.DisplayPlacement;
import net.media.openrtb3.Item;
import net.media.openrtb3.Placement;
import net.media.openrtb3.Request;
import net.media.openrtb3.Spec;

import org.mapstruct.MappingTarget;

import static java.util.Objects.nonNull;

@Generated(
  value = "org.mapstruct.ap.MappingProcessor",
  date = "2018-12-27T10:08:40+0530",
  comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 1.8.0_91 (Oracle Corporation)"
)
public class ImpItemConverter {

  private final DisplayConverter displayConverter = new DisplayConverter();
  private final VideoConverter videoConverter = new VideoConverter();
  private final AudioConverter audioConverter = new AudioConverter();

  public Item impToItem(Imp imp, BidRequest bidRequest) {
    if ( imp == null && bidRequest == null ) {
      return null;
    }

    Item item = new Item();

    if ( imp != null ) {
      if ( item.getSpec() == null ) {
        item.setSpec( new Spec() );
      }
      impToSpec1( imp, bidRequest, item.getSpec() );
      Map<String, Object> map = imp.getExt();
      if ( map != null ) {
        item.setExt( new HashMap<String, Object>( map ) );
      }
      item.setFlrcur( imp.getBidfloorcur() );
      List<Deal> deals = impPmpDeals( imp );
      item.setDeal( dealListToDealList( deals ) );
      item.setFlr( imp.getBidfloor() );
      item.setId( imp.getId() );
      Integer private_auction = impPmpPrivate_auction( imp );
      if ( private_auction != null ) {
        item.setPriv( private_auction );
      }
      Integer sequence = impVideoSequence( imp );
      if ( sequence != null ) {
        item.setSeq( sequence );
      }
      item.setExp( imp.getExp() );
    }
    if ( bidRequest != null ) {
      if ( item.getSpec() == null ) {
        item.setSpec( new Spec() );
      }
      bidRequestToSpec( bidRequest, item.getSpec() );
    }

    impToItemAfterMapping( imp, bidRequest, item );

    return item;
  }

  public Imp itemToImp(Item item, Request request) {
    if ( item == null ) {
      return null;
    }

    Imp imp = new Imp();

    imp.setPmp( itemToPmp( item ) );
    Map<String, Object> map = item.getExt();
    if ( map != null ) {
      imp.setExt( new HashMap<String, Object>( map ) );
    }
    imp.setBidfloor( item.getFlr() );
    String sdkver = itemSpecPlacementSdkver( item );
    if ( sdkver != null ) {
      imp.setDisplaymanagerver( sdkver );
    }
    DisplayPlacement display = itemSpecPlacementDisplay( item );
    if ( display != null ) {
      imp.setBanner( displayConverter.bannerToDisplayPlacement( display ) );
    }
    imp.setBidfloorcur( item.getFlrcur() );
    String tagid = itemSpecPlacementTagid( item );
    if ( tagid != null ) {
      imp.setTagId( tagid );
    }
    String sdk = itemSpecPlacementSdk( item );
    if ( sdk != null ) {
      imp.setDisplaymanager( sdk );
    }
    imp.setId( item.getId() );
    imp.setExp( item.getExp() );

    return imp;
  }

  public net.media.openrtb3.Deal map(Deal deal) {
    if ( deal == null ) {
      return null;
    }

    net.media.openrtb3.Deal deal1 = new net.media.openrtb3.Deal();

    deal1.setFlrcur( deal.getBidFloorCur() );
    deal1.setFlr( deal.getBidFloor() );
    deal1.setId( deal.getId() );
    deal1.setAt( deal.getAt() );
    List<String> list = deal.getWseat();
    if ( list != null ) {
      deal1.setWseat( new ArrayList<String>( list ) );
    }
    deal1.setWadomain(deal.getWadomain());
    Map<String, Object> map = deal.getExt();
    if ( map != null ) {
      deal1.setExt( new HashMap<String, Object>( map ) );
    }

    return deal1;
  }

  public Deal map(net.media.openrtb3.Deal deal) {
    if ( deal == null ) {
      return null;
    }

    Deal deal1 = new Deal();

    deal1.setBidFloorCur( deal.getFlrcur() );
    if ( deal.getFlr() != null ) {
      deal1.setBidFloor( deal.getFlr() );
    }
    deal1.setId( deal.getId() );
    deal1.setAt( deal.getAt() );
    List<String> list = deal.getWseat();
    if ( list != null ) {
      deal1.setWseat( new ArrayList<String>( list ) );
    }
    deal1.setWadomain(deal.getWadomain());
    Map<String, Object> map = deal.getExt();
    if ( map != null ) {
      deal1.setExt( new HashMap<String, Object>( map ) );
    }

    return deal1;
  }

  private List<Deal> impPmpDeals(Imp imp) {
    if ( imp == null ) {
      return null;
    }
    Pmp pmp = imp.getPmp();
    if ( pmp == null ) {
      return null;
    }
    List<Deal> deals = pmp.getDeals();
    if ( deals == null ) {
      return null;
    }
    return deals;
  }

  protected List<net.media.openrtb3.Deal> dealListToDealList(List<Deal> list) {
    if ( list == null ) {
      return null;
    }

    List<net.media.openrtb3.Deal> list1 = new ArrayList<net.media.openrtb3.Deal>( list.size() );
    for ( Deal deal : list ) {
      list1.add( map( deal ) );
    }

    return list1;
  }

  private Integer impPmpPrivate_auction(Imp imp) {
    if ( imp == null ) {
      return null;
    }
    Pmp pmp = imp.getPmp();
    if ( pmp == null ) {
      return null;
    }
    Integer private_auction = pmp.getPrivate_auction();
    if ( private_auction == null ) {
      return null;
    }
    return private_auction;
  }

  protected void impToPlacement1(Imp imp, BidRequest bidRequest, Placement mappingTarget) {
    if ( imp == null ) {
      return;
    }

    mappingTarget.setAudio( audioConverter.map( imp.getAudio(), imp, bidRequest ) );
    mappingTarget.setVideo( videoConverter.videoToVideoPlacement( imp.getVideo(), imp, bidRequest
    ) );
    mappingTarget.setTagid( imp.getTagId() );
    mappingTarget.setSdk( imp.getDisplaymanager() );
    mappingTarget.setSdkver( imp.getDisplaymanagerver() );
    mappingTarget.setDisplay( displayConverter.impToDisplayPlacementMapping( imp.getBanner(), imp
      .getNat(), imp, bidRequest) );
  }

  protected void impToSpec1(Imp imp, BidRequest bidRequest, Spec mappingTarget) {
    if ( imp == null ) {
      return;
    }

    if ( mappingTarget.getPlacement() == null ) {
      mappingTarget.setPlacement( new Placement() );
    }
    impToPlacement1( imp, bidRequest, mappingTarget.getPlacement() );
  }

  protected void bidRequestToPlacement(BidRequest bidRequest, Placement mappingTarget) {
    if ( bidRequest == null ) {
      return;
    }

    if ( mappingTarget.getWlang() != null ) {
      List<String> list = bidRequest.getWlang();
      if ( list != null ) {
        mappingTarget.getWlang().clear();
        mappingTarget.getWlang().addAll( list );
      }
      else {
        mappingTarget.setWlang( null );
      }
    }
    else {
      List<String> list = bidRequest.getWlang();
      if ( list != null ) {
        mappingTarget.setWlang( new ArrayList<String>( list ) );
      }
    }
  }

  protected void bidRequestToSpec(BidRequest bidRequest, Spec mappingTarget) {
    if ( bidRequest == null ) {
      return;
    }

    if ( mappingTarget.getPlacement() == null ) {
      mappingTarget.setPlacement( new Placement() );
    }
    bidRequestToPlacement( bidRequest, mappingTarget.getPlacement() );
  }

  private Integer impVideoSequence(Imp imp) {
    if ( imp == null ) {
      return null;
    }
    Video video = imp.getVideo();
    if ( video == null ) {
      return null;
    }
    Integer sequence = video.getSequence();
    if ( sequence == null ) {
      return null;
    }
    return sequence;
  }

  protected List<Deal> dealListToDealList1(List<net.media.openrtb3.Deal> list) {
    if ( list == null ) {
      return null;
    }

    List<Deal> list1 = new ArrayList<Deal>( list.size() );
    for ( net.media.openrtb3.Deal deal : list ) {
      list1.add( map( deal ) );
    }

    return list1;
  }

  protected Pmp itemToPmp(Item item) {
    if ( item == null ) {
      return null;
    }

    Pmp pmp = new Pmp();

    pmp.setDeals( dealListToDealList1( item.getDeal() ) );
    pmp.setPrivate_auction( item.getPriv() );

    return pmp;
  }

  private String itemSpecPlacementSdkver(Item item) {
    if ( item == null ) {
      return null;
    }
    Spec spec = item.getSpec();
    if ( spec == null ) {
      return null;
    }
    Placement placement = spec.getPlacement();
    if ( placement == null ) {
      return null;
    }
    String sdkver = placement.getSdkver();
    if ( sdkver == null ) {
      return null;
    }
    return sdkver;
  }

  private DisplayPlacement itemSpecPlacementDisplay(Item item) {
    if ( item == null ) {
      return null;
    }
    Spec spec = item.getSpec();
    if ( spec == null ) {
      return null;
    }
    Placement placement = spec.getPlacement();
    if ( placement == null ) {
      return null;
    }
    DisplayPlacement display = placement.getDisplay();
    if ( display == null ) {
      return null;
    }
    return display;
  }

  private String itemSpecPlacementTagid(Item item) {
    if ( item == null ) {
      return null;
    }
    Spec spec = item.getSpec();
    if ( spec == null ) {
      return null;
    }
    Placement placement = spec.getPlacement();
    if ( placement == null ) {
      return null;
    }
    String tagid = placement.getTagid();
    if ( tagid == null ) {
      return null;
    }
    return tagid;
  }

  private String itemSpecPlacementSdk(Item item) {
    if ( item == null ) {
      return null;
    }
    Spec spec = item.getSpec();
    if ( spec == null ) {
      return null;
    }
    Placement placement = spec.getPlacement();
    if ( placement == null ) {
      return null;
    }
    String sdk = placement.getSdk();
    if ( sdk == null ) {
      return null;
    }
    return sdk;
  }

  protected void impToItemAfterMapping(Imp imp, BidRequest bidRequest, Item item) {
    if (nonNull(imp) && nonNull(imp.getExt()) && !imp.getExt().isEmpty()) {
      if (nonNull(item)) {
        item.setQty((Integer) imp.getExt().get("qty"));
        item.getExt().remove("qty");
        item.setDt((Integer) imp.getExt().get("dt"));
        item.getExt().remove("dt");
        item.setDlvy((Integer) imp.getExt().get("dlvy"));
        item.getExt().remove("dlvy");
      }
      if (nonNull(item) && nonNull(item.getSpec()) && nonNull(item.getSpec().getPlacement())) {
        item.getSpec().getPlacement().setSsai((Integer) imp.getExt().get("ssai"));
        item.getExt().remove("ssai");
        item.getSpec().getPlacement().setReward((Integer) imp.getExt().get("reward"));
        item.getExt().remove("reward");
        item.getSpec().getPlacement().setAdmx((Integer) imp.getExt().get("admx"));
        item.getExt().remove("admx");
        item.getSpec().getPlacement().setCurlx((Integer) imp.getExt().get("curlx"));
        item.getExt().remove("curlx");
      }
    }
  }
}
