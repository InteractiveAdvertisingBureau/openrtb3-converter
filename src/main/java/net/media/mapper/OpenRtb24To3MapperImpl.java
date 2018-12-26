package net.media.mapper;

import net.media.enums.AdType;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb24.response.SeatBid;
import net.media.openrtb3.*;
import org.mapstruct.Context;
import org.mapstruct.MappingTarget;

import javax.annotation.Generated;
import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-12-20T19:11:16+0530",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"
)
public class OpenRtb24To3MapperImpl {

    /**
     *This  function  maps Rtb 2.4 BidResponse to  Rtb 3.0 Response
     * @param  BidResponse,AdType
     * @return Response(3.0 Response object)
     */
    public Response map(BidResponse bidResponse,AdType adType) {
        if ( bidResponse == null ) {
            return null;
        }

        Response response = new Response();

        response.setId( bidResponse.getId() );
        response.setBidid( bidResponse.getBidid() );
        response.setNbr( bidResponse.getNbr() );
        response.setCur( bidResponse.getCur() );
        response.setSeatbid( seatBidListToSeatbidList( bidResponse.getSeatbid(),bidResponse ,adType) );
        Map<String, Object> map = bidResponse.getExt();
        if ( map != null ) {
            response.setExt( new HashMap<String, Object>( map ) );
        }
        else {
            response.setExt( null );
        }
        mapResponse(bidResponse,adType,response);
        return response;
    }

    /**
     * After mapping of  rtb 3.0 response object from rtb  2.4 bidresponse object
     * @param bidResponse
     * @param adType
     * @param response
     */
    public void mapResponse(BidResponse bidResponse, @org.mapstruct.Context AdType adType,
                            @MappingTarget Response response){
        if (nonNull(bidResponse) && nonNull(response)) {
            if(nonNull(response.getExt()) && nonNull(bidResponse.getExt())){
                response.getExt().put("customerData",bidResponse.getCustomdata());
                response.setCdata((String)bidResponse.getExt().get("cdata"));
                response.getExt().remove("cdata");
            }else if(nonNull(bidResponse.getCustomdata())){
                Map<String,Object>  ext = new HashMap<>();
                ext.put("customData",bidResponse.getCustomdata());
                response.setExt(ext);
            }
        }
    }

    /**
     * Maps Rtb 3.0 response object  to Rtb 2.4
     * @param response
     * @param adType
     * @return  BidResponse (Rtb 2.4 response object)
     */
    public BidResponse map(Response response,AdType adType) {
        if ( response == null ) {
            return null;
        }

        BidResponse bidResponse = new BidResponse();

        bidResponse.setId( response.getId() );
        bidResponse.setSeatbid( seatbidListToSeatBidList( response.getSeatbid() ,response,adType) );
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
        mapResponse(response,adType,bidResponse);
        return bidResponse;
    }

    /**
     * After mapping of  rtb 2.4 bidResponse object from rtb  3.0 response object
     * @param response
     * @param adType
     * @param bidResponse
     */
    public void mapResponse(Response response, @org.mapstruct.Context AdType adType,
                            @MappingTarget BidResponse bidResponse){
        if (nonNull(bidResponse) && nonNull(response)) {
            if(nonNull(response.getExt())  && nonNull(bidResponse.getExt())){
                bidResponse.setCustomdata((String) response.getExt().get("customdata"));
                bidResponse.getExt().remove("customdata");
                bidResponse.getExt().put("cdata",response.getCdata());
            }else if(nonNull(bidResponse.getCustomdata())){
                Map<String,Object>  ext = new HashMap<>();
                ext.put("cdata",response.getCdata());
                bidResponse.setExt(ext);
            }
        }
    }

    /**
     * Maps seatBid list of  openrtb 3.0 to  openrtb 2.4
     * @param list
     * @param response
     * @param adType
     * @return List<SeatBid>(openrtb 2.4)
     */
    protected List<SeatBid> seatbidListToSeatBidList(List<Seatbid> list,Response response,AdType adType) {
        if ( list == null ) {
            return null;
        }

        List<SeatBid> list1 = new ArrayList<SeatBid>( list.size() );
        for ( Seatbid seatbid : list ) {
            list1.add( seatMapper( seatbid,response,adType ) );
        }

        return list1;
    }

    /**
     * Maps rtb 2.4 seatbid list to rtb 3.0 seatBid list
     * @param list
     * @param bidResponse
     * @param adType
     * @return List<Seatbid>(openrtb 3.0 seatbid list)
     */
    protected List<Seatbid> seatBidListToSeatbidList(List<SeatBid> list,BidResponse bidResponse,AdType adType) {
        if ( list == null ) {
            return null;
        }

        List<Seatbid> list1 = new ArrayList<Seatbid>( list.size() );
        for ( SeatBid seatBid : list ) {
            list1.add( seatMapper( seatBid,bidResponse,adType ) );
        }

        return list1;
    }

    /**
     * Maps open rtb 2.4 seatBid to rtb 3.0 seatBid
     * @param seatBid
     * @param bidResponse
     * @param adType
     * @return
     */
    public Seatbid seatMapper(SeatBid seatBid,BidResponse bidResponse, AdType adType) {
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
        seatbid.setBid( bidListToBidList2( seatBid.getBid(),seatBid,bidResponse, adType ) );

        return seatbid;
    }

    /**
     * Maps rtb 3.0 seatBid  Object to rtb 2.4 seatbid object
     * @param seatBid
     * @param response
     * @param adType
     * @return
     */
    public SeatBid seatMapper(Seatbid seatBid, Response response,AdType adType) {
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
        seatBid1.setBid( bidListToBidList3( seatBid.getBid(),seatBid,response, adType ) );
        seatBid1.setSeat( seatBid.getSeat() );

        return seatBid1;
    }

    /**
     * Maps rtb 3.0 bidlist to rtb 2.4 bidlist
     * @param list
     * @param seatbid
     * @param response
     * @param adType
     * @return
     */
    protected List<net.media.openrtb24.response.Bid> bidListToBidList3(List<Bid> list,Seatbid seatbid ,Response response,AdType adType) {
        if ( list == null ) {
            return null;
        }

        List<net.media.openrtb24.response.Bid> list1 = new ArrayList<net.media.openrtb24.response.Bid>( list.size() );
        for ( Bid bid : list ) {
            list1.add( bidMapper( bid, seatbid,response,adType ) );
        }

        return list1;
    }

    /**
     * Maps rtb 2.4 bidList  to rtb  3.0 bidList
     * @param list
     * @param seatbid
     * @param bidResponse
     * @param adType
     * @return
     */
    protected List<Bid> bidListToBidList2(List<net.media.openrtb24.response.Bid> list, SeatBid seatbid,BidResponse bidResponse,AdType adType) {
        if ( list == null ) {
            return null;
        }

        List<Bid> list1 = new ArrayList<Bid>( list.size() );
        for ( net.media.openrtb24.response.Bid bid : list ) {
            list1.add( bidMapper( bid,seatbid,bidResponse ,adType ) );
        }

        return list1;
    }


    /**
     * Maps rtb 2.4 bid object  to 3.0 bid object
     * @param bid
     * @param seatBid
     * @param bidResponse
     * @param adType
     * @return
     */
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
            bid1.setMedia( bidtoMedia( bid,seatBid ,bidResponse,adType ) );
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

    /**
     * Maps rtb 3.0 bid object  to rtb 2.4 bid object
     * @param bid
     * @param seatbid
     * @param response
     * @param adType
     * @return
     */
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
            bid1.setImpid(bid.getItem());
            bid1.setDealid(bid.getDeal());
            bid1.setNurl(bid.getPurl());
            bid1.setCid( bid.getCid() );
            bid1.setExp( bid.getExp() );
            bid1.setBurl( bid.getBurl() );
            bid1.setLurl( bid.getLurl() );
            bid1.setTactic( bid.getTactic() );

            mediaToBid(bid1,bid.getMedia(),adType);
        }

        return bid1;
    }

    /**
     * Maps Rtb 2.4 Bid to Rtb 3.0 media object
     * @param bid
     * @param adType
     * @param seatBid
     * @param bidResponse
     * @return
     */
    public Media bidtoMedia(net.media.openrtb24.response.Bid bid, SeatBid seatBid, BidResponse bidResponse, AdType adType) {
        if ( bid == null && seatBid == null && bidResponse == null ) {
            return null;
        }

        Media media = new Media();

        if ( bid != null ) {
            media.setAd( bidToAd( bid,seatBid,bidResponse, adType ) );
        }

        return media;
    }

    /**
     * Maps rtb 2.4 bid object to  rtb  3.0 Ad  object
     * @param bid
     * @param seatBid
     * @param bidResponse
     * @param adType
     * @return
     */
    protected Ad bidToAd(net.media.openrtb24.response.Bid bid, SeatBid seatBid, BidResponse bidResponse, AdType adType) {
        if ( bid == null ) {
            return null;
        }

        Ad ad = new Ad();

        ad.setId( bid.getCrid() );
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
            ad.setExt( new HashMap<>());
        }
        if (nonNull(bid) && nonNull(bid.getExt())) {
            Map<String,Object> ext = ad.getExt();
            ad.setSecure((Integer) ext.get("secure"));
            ad.setInit((Integer) ext.get("init"));
            ad.setLastmod((Integer) ext.get("lastMod"));
            ad.setMrating((Integer) ext.get("mrating"));
            ad.setCattax((Integer) ext.get("cattax"));
        }
        switch (adType) {
            case BANNER:
            case NATIVE:
                ad.setDisplay(mapDisplay(bid, seatBid, bidResponse, adType));
                break;
            case VIDEO:
                ad.setVideo(mapVideo(bid, seatBid, bidResponse, adType));
                break;
            case AUDIO:
                ad.setAudio(mapAudio(bid, seatBid, bidResponse, adType));
                break;
            case AUDIT:
                ad.setAudit(mapAudit(bid, seatBid, bidResponse, adType));
                break;
        }
        mapAd( bid, seatBid, bidResponse, adType,ad);
        return ad;
    }

    /**
     * Maps rtb 2.4  bidobject to  rtb  3.0  audio object
     * @param bid
     * @param seatBid
     * @param bidResponse
     * @param adType
     * @return
     */
    public Audio mapAudio(net.media.openrtb24.response.Bid bid, SeatBid seatBid, BidResponse
      bidResponse, AdType adType) {
        if ( bid == null && seatBid == null && bidResponse == null ) {
            return null;
        }

        Audio audio = new Audio();

        if ( bid != null ) {
            Map<String, Object> map = bid.getExt();
            if ( map != null ) {
                audio.setExt( new HashMap<String, Object>( map ) );
            }
            else {
                audio.setExt( null );
            }
            audio.setAdm( bid.getAdm() );
            List<Integer> list = bid.getApi();
            if ( list != null ) {
                audio.setApi( new ArrayList<Integer>( list ) );
            }
            else {
                audio.setApi( null );
            }
        }
        mapAudio(bid,seatBid,bidResponse,audio,adType);
        return audio;
    }

    /**
     * After mapping of rtb 3.0 audio object from bid object
     * @param bid
     * @param seatBid
     * @param bidResponse
     * @param audio
     * @param adType
     */
    public void mapAudio(net.media.openrtb24.response.Bid bid, SeatBid seatBid, BidResponse bidResponse, @MappingTarget
      Audio audio, @Context AdType adType) {
        ;
        if (isNull(bid) || isNull(audio) || isNull(bid.getExt())) {
            return;
        }
        Map<String, Object> ext = bid.getExt();
        audio.setCtype((Integer) ext.get("ctype"));
        audio.setCurl((String) ext.get("curl"));
        audio.setDur((Integer) ext.get("dur"));


    }

    /**
     * Maps  rtb  2.4  bid object to rtb  3.0  audit object
     * @param bid
     * @param seatBid
     * @param bidResponse
     * @param adType
     * @return
     */
    public Audit mapAudit(net.media.openrtb24.response.Bid bid, SeatBid seatBid, BidResponse
      bidResponse, AdType adType) {
        if ( bid == null && seatBid == null && bidResponse == null ) {
            return null;
        }

        Audit audit = new Audit();

        if ( bid != null ) {
            Map<String, Object> map = bid.getExt();
            if ( map != null ) {
                audit.setExt( new HashMap<String, Object>( map ) );
                if (map.containsKey("corr")) {
                    audit.setCorr((Corr) map.get("corr"));
                }
                if (map.containsKey("status")) {
                    audit.setStatus((Integer) map.get("status"));
                }
                if (map.containsKey("init")) {
                    audit.setInit((Integer) map.get("init"));
                }
                if (map.containsKey("lastMod")) {
                    audit.setLastmod((Integer) map.get("lastMode"));
                }
                if (map.containsKey("feedback")) {
                    audit.setFeedback((List<String>) map.get("feedback"));
                }
            }
            else {
                audit.setExt( null );
            }
        }

        return audit;
    }

    /**
     * After mapping of rtb 2.4  bid object to rtb 3.0  Ad object
     * @param bid
     * @param seatBid
     * @param bidResponse
     * @param adType
     * @param ad
     */
    public void mapAd(net.media.openrtb24.response.Bid bid, SeatBid seatBid, BidResponse bidResponse, @org.mapstruct.Context AdType adType,
                      Ad ad){
        if (nonNull(bid) && nonNull(bid.getExt()) && nonNull(ad)) {

            Map<String,Object> ext = ad.getExt();
            ad.setSecure((Integer) ext.get("secure"));
            ad.setInit((Integer) ext.get("init"));
            ad.setLastmod((Integer) ext.get("lastMod"));
            ad.setMrating((Integer) ext.get("mrating"));
            ad.setCattax((Integer) ext.get("cattax"));

        }
    }


    /**
     * Maps rtb 2.4  bid  object  to  rtb 3.0 display object
     * @param bid
     * @param seatBid
     * @param bidResponse
     * @param adType
     * @return
     */
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
        mapDisplay(bid,seatBid,bidResponse,display,adType);
        return display;
    }

    /**
     * after mapping of rtb  3.0 display object from rtb 2.4 bid object
     * @param bid
     * @param seatBid
     * @param bidResponse
     * @param display
     * @param adType
     */
    public void mapDisplay(net.media.openrtb24.response.Bid bid, SeatBid seatBid, BidResponse bidResponse, @MappingTarget
      Display display, @org.mapstruct.Context AdType adType) {
        if (isNull(bid) || isNull(display) || isNull(bid.getExt())) {
            return;
        }
        Map<String, Object> ext = bid.getExt();
        display.setCtype((Integer) ext.get("ctype"));
        display.setPriv((String) ext.get("priv"));
        display.setCurl((String) ext.get("curl"));
        if (adType == AdType.BANNER) {
            display.setBanner((net.media.openrtb3.Banner) ext.get("banner"));
        }
        else if (adType == AdType.NATIVE) {
            display.set_native((net.media.openrtb3.Native) ext.get("native"));
        }
        display.setEvent((List<Event>) ext.get(ext.get("event")));
    }

    /**
     * Maps  rtb  2.4  bid  object to rtb 3.0  video object
     * @param bid
     * @param seatBid
     * @param bidResponse
     * @param adType
     * @return
     */
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
        mapVideo(bid,seatBid,bidResponse,video,adType);
        return video;
    }

    /**
     * after mapping of rtb  3.0 display object from rtb 2.4 bid object
     * @param bid
     * @param seatBid
     * @param bidResponse
     * @param video
     * @param adType
     */
    public void mapVideo(net.media.openrtb24.response.Bid bid, SeatBid seatBid, BidResponse bidResponse, @MappingTarget
      Video video, @org.mapstruct.Context AdType adType) {
        if (isNull(bid) || isNull(video) || isNull(bid.getExt())) {
            return;
        }
        Map<String, Object> ext = bid.getExt();
        video.setCtype((Integer) ext.get("ctype"));
        video.setCurl((String) ext.get("curl"));
        video.setDur((Integer) ext.get("dur"));

    }

    /**
     * Fills required parameters  of  rtb 3.0  media  object to rtb 2.4 bid object
     * @param bid
     * @param media
     * @param adType
     */
    protected void mediaToBid(net.media.openrtb24.response.Bid bid, Media media, AdType adType) {
        if (media == null || bid == null) {
            return;
        }
        adToBid(bid, media.getAd(), adType);
    }

    /**
     * Fills required parameters  of  rtb 3.0  ad  object to rtb 2.4 bid object
     * @param bid
     * @param ad
     * @param adType
     */
    private void adToBid(net.media.openrtb24.response.Bid bid, Ad ad, AdType adType) {
        if (bid == null || ad == null) {
            return;
        }
        bid.setCrid(ad.getId());
        List<String> list = ad.getAdomain();
        if ( list != null ) {
            bid.setAdomain( new ArrayList<String>( list ) );
        }
        else {
            bid.setAdomain( null );
        }
        List<String> list1 = ad.getBundle();
        if ( list1 != null ) {
            bid.setBundle( new ArrayList<String>( list1 ) );
        }
        else {
            bid.setBundle( null );
        }
        bid.setIurl( ad.getIurl() );
        List<String> set = ad.getCat();
        if ( set != null ) {
            bid.setCat( new HashSet<>( set ) );
        }
        else {
            bid.setCat( null );
        }
        bid.setLanguage(ad.getLang());
        List<Integer> list2 = ad.getAttr();
        if ( list2 != null ) {
            bid.setAttr( new ArrayList<Integer>( list2 ) );
        }
        else {
            bid.setAttr( null );
        }
        Map<String, Object> map = ad.getExt();
        if ( map != null ) {
            bid.setExt( new HashMap<String, Object>( map ) );
        }
        else {
            bid.setExt( new HashMap<>());
        }
        if (nonNull(ad.getSecure())) {
            bid.getExt().put("secure", ad.getSecure());
        }
        if (nonNull(ad.getInit())) {
            bid.getExt().put("init", ad.getInit());
        }
        if (nonNull(ad.getLastmod())) {
            bid.getExt().put("lastMod", ad.getLastmod());
        }
        if (nonNull(ad.getMrating())) {
            bid.getExt().put("mrating", ad.getMrating());
        }
        if (nonNull(ad.getCattax())) {
            bid.getExt().put("cattax", ad.getCattax());
        }
        switch (adType) {
            case BANNER:
            case NATIVE:
                displayToBid(bid, ad.getDisplay(), adType);
                break;
            case VIDEO:
                videoToBid(bid, ad.getVideo(), adType);
                break;
            case AUDIO:
                audioToBid(bid, ad.getAudio(), adType);
                break;
            case AUDIT:
                auditToBid(bid, ad.getAudit(), adType);
                break;
        }
    }

    private void displayToBid(net.media.openrtb24.response.Bid bid, Display display, AdType adType) {
        if(nonNull(bid) && nonNull(display)){
            bid.setAdm(display.getAdm());
            bid.setH(display.getH());
            bid.setW(display.getW());
            bid.setWratio(display.getWratio());
            bid.setHratio(display.getHratio());
            bid.setApi(display.getApi());
            if(isNull(bid.getExt())){
                bid.setExt(new HashMap<>());
            }
            bid.getExt().put("ctype",display.getCtype());
            bid.getExt().put("banner",display.getBanner());
            bid.getExt().put("native",display.get_native());
            bid.getExt().put("priv",display.getPriv());
            bid.getExt().put("curl",display.getCurl());
            bid.getExt().put("event",display.getEvent());
            bid.getExt().put("mime",display.getMime());
            bid.getExt().putAll(display.getExt());
        }

    }


    private void videoToBid(net.media.openrtb24.response.Bid bid, Video video, AdType adType) {
        if(nonNull(bid) && nonNull(video)){
            bid.setAdm(video.getAdm());
            bid.setApi(video.getApi());
            if(isNull(bid.getExt())){
                bid.setExt(new HashMap<>());
            }
            bid.getExt().put("ctype",video.getCtype());
            bid.getExt().put("dur",video.getDur());
            bid.getExt().put("curl",video.getCurl());
            bid.getExt().put("mime",video.getMime());
            bid.getExt().putAll(video.getExt());
        }
    }

    private void audioToBid(net.media.openrtb24.response.Bid bid, Audio audio, AdType adType) {
        if(nonNull(bid) && nonNull(audio)){
            bid.setAdm(audio.getAdm());
            bid.setApi(audio.getApi());
            if(isNull(bid.getExt())){
                bid.setExt(new HashMap<>());
            }
            bid.getExt().put("ctype",audio.getCtype());
            bid.getExt().put("dur",audio.getDur());
            bid.getExt().put("curl",audio.getCurl());
            bid.getExt().put("mime",audio.getMime());
            bid.getExt().putAll(audio.getExt());
        }
    }

    private Integer status;
    private List<String> feedback = null;
    private Integer init;
    private Integer lastmod;
    private Corr corr;
    private Map<String,Object> ext;

    private void auditToBid(net.media.openrtb24.response.Bid bid, Audit audit, AdType adType) {
        if(isNull(bid.getExt())){
            bid.setExt(new HashMap<>());
        }
        bid.getExt().put("status",audit.getStatus());
        bid.getExt().put("feedback",audit.getFeedback());
        bid.getExt().put("init",audit.getInit());
        bid.getExt().put("lastmod",audit.getLastmod());
        bid.getExt().put("corr",audit.getCorr());
        bid.getExt().putAll(audit.getExt());
    }




}
