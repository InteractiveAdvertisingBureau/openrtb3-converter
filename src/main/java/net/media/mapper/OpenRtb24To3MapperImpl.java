package net.media.mapper;

import net.media.enums.AdType;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb3.*;

import javax.annotation.Generated;
import java.util.*;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-12-20T19:11:16+0530",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"
)
public class OpenRtb24To3MapperImpl {

    public Response map(BidResponse bidResponse,AdType adType) {
        if ( bidResponse == null ) {
            return null;
        }

        Response response = new Response();

        response.setId( bidResponse.getId() );
        response.setBidid( bidResponse.getBidid() );
        response.setNbr( bidResponse.getNbr() );
        response.setCur( bidResponse.getCur() );
        response.setSeatbid( seatBidListToSeatbidList( bidResponse.getSeatbid() ,adType) );
        Map<String, Object> map = bidResponse.getExt();
        if ( map != null ) {
            response.setExt( new HashMap<String, Object>( map ) );
        }
        else {
            response.setExt( null );
        }

        return response;
    }

    public BidResponse map(Response response,AdType adType) {
        if ( response == null ) {
            return null;
        }

        BidResponse bidResponse = new BidResponse();

        bidResponse.setId( response.getId() );
        bidResponse.setSeatbid( seatbidListToSeatBidList( response.getSeatbid() ,adType) );
        bidResponse.setBidid( response.getBidid() );
        bidResponse.setCur( response.getCur() );
        bidResponse.setNbr( response.getNbr() );
        Map<String, Object> map = response.getExt();
        if ( map != null ) {
            bidResponse.setExt( new HashMap<String, Object>( map ) );
        }
        else {
            bidResponse.setExt( null );
        }

        return bidResponse;
    }

    @Override
    public Seatbid seatMapper(SeatBid seatBid, AdType adType) {
        if ( seatBid == null ) {
            return null;
        }

        Seatbid seatbid = new Seatbid();

        Map<String, Object> map = seatBid.getExt();
        if ( map != null ) {
            seatbid.setExt( new HashMap<String, Object>( map ) );
        }
        else {
            seatbid.setExt( null );
        }
        seatbid.set_package( seatBid.getGroup() );
        seatbid.setSeat( seatBid.getSeat() );
        seatbid.setBid( bidListToBidList2( seatBid.getBid(), adType ) );

        return seatbid;
    }

    public SeatBid seatMapper(Seatbid seatBid, AdType adType) {
        if ( seatBid == null ) {
            return null;
        }

        SeatBid seatBid1 = new SeatBid();

        Map<String, Object> map = seatBid.getExt();
        if ( map != null ) {
            seatBid1.setExt( new HashMap<String, Object>( map ) );
        }
        else {
            seatBid1.setExt( null );
        }
        seatBid1.setGroup( seatBid.get_package() );
        seatBid1.setBid( bidListToBidList3( seatBid.getBid(), adType ) );
        seatBid1.setSeat( seatBid.getSeat() );

        return seatBid1;
    }


    public Bid bidMapper(net.media.openrtb24.response.Bid bid, SeatBid seatBid, BidResponse bidResponse, AdType adType) {
        if ( bid == null && seatBid == null && bidResponse == null ) {
            return null;
        }

        Bid bid1 = new Bid();

        if ( bid != null ) {
            Map<String, Object> map = bid.getExt();
            if ( map != null ) {
                bid1.setExt( new HashMap<String, Object>( map ) );
            }
            else {
                bid1.setExt( null );
            }
            bid1.setItem( bid.getImpid() );
            bid1.setDeal( bid.getDealid() );
            bid1.setPurl( bid.getNurl() );
            bid1.setMedia( bidToMedia( bid, adType ) );
            bid1.setId( bid.getId() );
            bid1.setPrice( bid.getPrice() );
            bid1.setCid( bid.getCid() );
            bid1.setTactic( bid.getTactic() );
            bid1.setBurl( bid.getBurl() );
            bid1.setLurl( bid.getLurl() );
            bid1.setExp( bid.getExp() );
        }

        return bid1;
    }


    public net.media.openrtb24.response.Bid bidMapper(Bid bid, Seatbid seatbid, Response response, AdType adType) {
        if ( bid == null && seatbid == null && response == null ) {
            return null;
        }

        net.media.openrtb24.response.Bid bid1 = new net.media.openrtb24.response.Bid();

        if ( bid != null ) {
            Map<String, Object> map = bid.getExt();
            if ( map != null ) {
                bid1.setExt( new HashMap<String, Object>( map ) );
            }
            else {
                bid1.setExt( null );
            }
            bid1.setId( bid.getId() );
            if ( bid.getPrice() != null ) {
                bid1.setPrice( bid.getPrice() );
            }
            bid1.setCid( bid.getCid() );
            bid1.setExp( bid.getExp() );
            bid1.setBurl( bid.getBurl() );
            bid1.setLurl( bid.getLurl() );
            bid1.setTactic( bid.getTactic() );
        }

        return bid1;
    }

    @Override
    public Media map(net.media.openrtb24.response.Bid bid, SeatBid seatBid, BidResponse bidResponse, AdType adType) {
        if ( bid == null && seatBid == null && bidResponse == null ) {
            return null;
        }

        Media media = new Media();

        if ( bid != null ) {
            media.setAd( bidToAd( bid, adType ) );
        }

        return media;
    }

    @Override
    public Ad mapAd(net.media.openrtb24.response.Bid bid, SeatBid seatBid, BidResponse bidResponse, AdType adType) {
        if ( bid == null && seatBid == null && bidResponse == null ) {
            return null;
        }

        Ad ad = new Ad();

        if ( bid != null ) {
            Map<String, Object> map = bid.getExt();
            if ( map != null ) {
                ad.setExt( new HashMap<String, Object>( map ) );
            }
            else {
                ad.setExt( null );
            }
            ad.setId( bid.getCrid() );
            ad.setLang( bid.getLanguage() );
            List<String> list = bid.getAdomain();
            if ( list != null ) {
                ad.setAdomain( new ArrayList<String>( list ) );
            }
            else {
                ad.setAdomain( null );
            }
            List<String> list1 = bid.getBundle();
            if ( list1 != null ) {
                ad.setBundle( new ArrayList<String>( list1 ) );
            }
            else {
                ad.setBundle( null );
            }
            ad.setIurl( bid.getIurl() );
            Set<String> set = bid.getCat();
            if ( set != null ) {
                ad.setCat( new ArrayList<String>( set ) );
            }
            else {
                ad.setCat( null );
            }
            List<Integer> list2 = bid.getAttr();
            if ( list2 != null ) {
                ad.setAttr( new ArrayList<Integer>( list2 ) );
            }
            else {
                ad.setAttr( null );
            }
        }

        mapAd( bid, seatBid, bidResponse, adType, ad );

        return ad;
    }

    @Override
    public Display mapDisplay(net.media.openrtb24.response.Bid bid, SeatBid seatBid, BidResponse bidResponse, AdType adType) {
        if ( bid == null && seatBid == null && bidResponse == null ) {
            return null;
        }

        Display display = new Display();

        if ( bid != null ) {
            display.setH( bid.getH() );
            display.setWratio( bid.getWratio() );
            display.setAdm( bid.getAdm() );
            display.setW( bid.getW() );
            display.setHratio( bid.getHratio() );
            List<Integer> list = bid.getApi();
            if ( list != null ) {
                display.setApi( new ArrayList<Integer>( list ) );
            }
            else {
                display.setApi( null );
            }
        }

        return display;
    }

    @Override
    public Video mapVideo(net.media.openrtb24.response.Bid bid, SeatBid seatBid, BidResponse bidResponse, AdType adType) {
        if ( bid == null && seatBid == null && bidResponse == null ) {
            return null;
        }

        Video video = new Video();

        if ( bid != null ) {
            Map<String, Object> map = bid.getExt();
            if ( map != null ) {
                video.setExt( new HashMap<String, Object>( map ) );
            }
            else {
                video.setExt( null );
            }
            video.setAdm( bid.getAdm() );
            List<Integer> list = bid.getApi();
            if ( list != null ) {
                video.setApi( new ArrayList<Integer>( list ) );
            }
            else {
                video.setApi( null );
            }
        }

        return video;
    }

    @Override
    public Video mapAudio(net.media.openrtb24.response.Bid bid, SeatBid seatBid, BidResponse bidResponse, AdType adType) {
        if ( bid == null && seatBid == null && bidResponse == null ) {
            return null;
        }

        Video video = new Video();

        if ( bid != null ) {
            Map<String, Object> map = bid.getExt();
            if ( map != null ) {
                video.setExt( new HashMap<String, Object>( map ) );
            }
            else {
                video.setExt( null );
            }
            video.setAdm( bid.getAdm() );
            List<Integer> list = bid.getApi();
            if ( list != null ) {
                video.setApi( new ArrayList<Integer>( list ) );
            }
            else {
                video.setApi( null );
            }
        }

        return video;
    }

    protected Bid bidToBid(net.media.openrtb24.response.Bid bid) {
        if ( bid == null ) {
            return null;
        }

        Bid bid1 = new Bid();

        bid1.setId( bid.getId() );
        bid1.setPrice( bid.getPrice() );
        bid1.setCid( bid.getCid() );
        bid1.setTactic( bid.getTactic() );
        bid1.setBurl( bid.getBurl() );
        bid1.setLurl( bid.getLurl() );
        bid1.setExp( bid.getExp() );
        Map<String, Object> map = bid.getExt();
        if ( map != null ) {
            bid1.setExt( new HashMap<String, Object>( map ) );
        }
        else {
            bid1.setExt( null );
        }

        return bid1;
    }

    protected List<Bid> bidListToBidList(List<net.media.openrtb24.response.Bid> list) {
        if ( list == null ) {
            return null;
        }

        List<Bid> list1 = new ArrayList<Bid>( list.size() );
        for ( net.media.openrtb24.response.Bid bid : list ) {
            list1.add( bidToBid( bid ) );
        }

        return list1;
    }

    protected Seatbid seatBidToSeatbid(SeatBid seatBid) {
        if ( seatBid == null ) {
            return null;
        }

        Seatbid seatbid = new Seatbid();

        seatbid.setSeat( seatBid.getSeat() );
        seatbid.setBid( bidListToBidList( seatBid.getBid() ) );
        Map<String, Object> map = seatBid.getExt();
        if ( map != null ) {
            seatbid.setExt( new HashMap<String, Object>( map ) );
        }
        else {
            seatbid.setExt( null );
        }

        return seatbid;
    }

    protected List<Seatbid> seatBidListToSeatbidList(List<SeatBid> list,AdType adType) {
        if ( list == null ) {
            return null;
        }

        List<Seatbid> list1 = new ArrayList<Seatbid>( list.size() );
        for ( SeatBid seatBid : list ) {
            list1.add( seatMapper( seatBid,adType ) );
        }

        return list1;
    }

    protected net.media.openrtb24.response.Bid bidToBid1(Bid bid) {
        if ( bid == null ) {
            return null;
        }

        net.media.openrtb24.response.Bid bid1 = new net.media.openrtb24.response.Bid();

        bid1.setId( bid.getId() );
        if ( bid.getPrice() != null ) {
            bid1.setPrice( bid.getPrice() );
        }
        bid1.setCid( bid.getCid() );
        bid1.setExp( bid.getExp() );
        bid1.setBurl( bid.getBurl() );
        bid1.setLurl( bid.getLurl() );
        bid1.setTactic( bid.getTactic() );
        Map<String, Object> map = bid.getExt();
        if ( map != null ) {
            bid1.setExt( new HashMap<String, Object>( map ) );
        }
        else {
            bid1.setExt( null );
        }

        return bid1;
    }

    protected List<net.media.openrtb24.response.Bid> bidListToBidList1(List<Bid> list) {
        if ( list == null ) {
            return null;
        }

        List<net.media.openrtb24.response.Bid> list1 = new ArrayList<net.media.openrtb24.response.Bid>( list.size() );
        for ( Bid bid : list ) {
            list1.add( bidToBid1( bid ) );
        }

        return list1;
    }

    protected SeatBid seatbidToSeatBid(Seatbid seatbid) {
        if ( seatbid == null ) {
            return null;
        }

        SeatBid seatBid = new SeatBid();

        seatBid.setBid( bidListToBidList1( seatbid.getBid() ) );
        seatBid.setSeat( seatbid.getSeat() );
        Map<String, Object> map = seatbid.getExt();
        if ( map != null ) {
            seatBid.setExt( new HashMap<String, Object>( map ) );
        }
        else {
            seatBid.setExt( null );
        }

        return seatBid;
    }

    protected List<SeatBid> seatbidListToSeatBidList(List<Seatbid> list,AdType adType) {
        if ( list == null ) {
            return null;
        }

        List<SeatBid> list1 = new ArrayList<SeatBid>( list.size() );
        for ( Seatbid seatbid : list ) {
            list1.add( seatMapper( seatbid,adType ) );
        }

        return list1;
    }

    protected Bid bidToBid2(net.media.openrtb24.response.Bid bid, AdType adType) {
        if ( bid == null ) {
            return null;
        }

        Bid bid1 = new Bid();

        bid1.setId( bid.getId() );
        bid1.setPrice( bid.getPrice() );
        bid1.setCid( bid.getCid() );
        bid1.setTactic( bid.getTactic() );
        bid1.setBurl( bid.getBurl() );
        bid1.setLurl( bid.getLurl() );
        bid1.setExp( bid.getExp() );
        Map<String, Object> map = bid.getExt();
        if ( map != null ) {
            bid1.setExt( new HashMap<String, Object>( map ) );
        }
        else {
            bid1.setExt( null );
        }

        return bid1;
    }

    protected List<Bid> bidListToBidList2(List<net.media.openrtb24.response.Bid> list, AdType adType) {
        if ( list == null ) {
            return null;
        }

        List<Bid> list1 = new ArrayList<Bid>( list.size() );
        for ( net.media.openrtb24.response.Bid bid : list ) {
            list1.add( bidToBid2( bid, adType ) );
        }

        return list1;
    }

    protected net.media.openrtb24.response.Bid bidToBid3(Bid bid, AdType adType) {
        if ( bid == null ) {
            return null;
        }

        net.media.openrtb24.response.Bid bid1 = new net.media.openrtb24.response.Bid();

        bid1.setId( bid.getId() );
        if ( bid.getPrice() != null ) {
            bid1.setPrice( bid.getPrice() );
        }
        bid1.setCid( bid.getCid() );
        bid1.setExp( bid.getExp() );
        bid1.setBurl( bid.getBurl() );
        bid1.setLurl( bid.getLurl() );
        bid1.setTactic( bid.getTactic() );
        Map<String, Object> map = bid.getExt();
        if ( map != null ) {
            bid1.setExt( new HashMap<String, Object>( map ) );
        }
        else {
            bid1.setExt( null );
        }

        return bid1;
    }

    protected List<net.media.openrtb24.response.Bid> bidListToBidList3(List<Bid> list, AdType adType) {
        if ( list == null ) {
            return null;
        }

        List<net.media.openrtb24.response.Bid> list1 = new ArrayList<net.media.openrtb24.response.Bid>( list.size() );
        for ( Bid bid : list ) {
            list1.add( bidToBid3( bid, adType ) );
        }

        return list1;
    }

    protected Media bidToMedia(net.media.openrtb24.response.Bid bid, AdType adType) {
        if ( bid == null ) {
            return null;
        }

        Media media = new Media();

        return media;
    }

    protected Ad bidToAd(net.media.openrtb24.response.Bid bid, AdType adType) {
        if ( bid == null ) {
            return null;
        }

        Ad ad = new Ad();

        ad.setId( bid.getId() );
        List<String> list = bid.getAdomain();
        if ( list != null ) {
            ad.setAdomain( new ArrayList<String>( list ) );
        }
        else {
            ad.setAdomain( null );
        }
        List<String> list1 = bid.getBundle();
        if ( list1 != null ) {
            ad.setBundle( new ArrayList<String>( list1 ) );
        }
        else {
            ad.setBundle( null );
        }
        ad.setIurl( bid.getIurl() );
        Set<String> set = bid.getCat();
        if ( set != null ) {
            ad.setCat( new ArrayList<String>( set ) );
        }
        else {
            ad.setCat( null );
        }
        List<Integer> list2 = bid.getAttr();
        if ( list2 != null ) {
            ad.setAttr( new ArrayList<Integer>( list2 ) );
        }
        else {
            ad.setAttr( null );
        }
        Map<String, Object> map = bid.getExt();
        if ( map != null ) {
            ad.setExt( new HashMap<String, Object>( map ) );
        }
        else {
            ad.setExt( null );
        }

        return ad;
    }
}
