package net.media.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Generated;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Content;
import net.media.openrtb24.request.Data;
import net.media.openrtb24.request.Device.DeviceBuilder;
import net.media.openrtb24.request.Geo;
import net.media.openrtb24.request.Imp;
import net.media.openrtb24.request.Publisher;
import net.media.openrtb24.request.Segment;
import net.media.openrtb24.request.Source;
import net.media.openrtb24.request.User;
import net.media.openrtb3.App;
import net.media.openrtb3.Context;
import net.media.openrtb3.Device;
import net.media.openrtb3.Dooh;
import net.media.openrtb3.Item;
import net.media.openrtb3.Producer;
import net.media.openrtb3.Regs;
import net.media.openrtb3.Request;
import net.media.openrtb3.Restrictions;
import net.media.openrtb3.Site;

import org.mapstruct.MappingTarget;

@Generated(
  value = "org.mapstruct.ap.MappingProcessor",
  date = "2018-12-27T21:38:53+0530",
  comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 1.8.0_91 (Oracle Corporation)"
)
public class RequestConverter {

  private final ImpItemConverter impItemConverter = new ImpItemConverter();
  private BiMap<String, Integer> osMap;

  RequestConverter() {
    osMap = HashBiMap.create();
    osMap.put("other not listed\n", 0);
    osMap.put("3ds system software", 1);
    osMap.put("android", 2);
    osMap.put("apple tv software", 3);
    osMap.put("asha", 4);
    osMap.put("bada", 5);
    osMap.put("blackberry", 6);
    osMap.put("brew", 7);
    osMap.put("chromeos", 8);
    osMap.put("darwin", 9);
    osMap.put("fireos", 10);
    osMap.put("firefoxos", 11);
    osMap.put("helenos", 12);
    osMap.put("ios", 13);
    osMap.put("linux", 14);
    osMap.put("macos", 15);
    osMap.put("meego", 16);
    osMap.put("morphos", 17);
    osMap.put("netbsd", 18);
    osMap.put("nucleusplus", 19);
    osMap.put("ps vita system software", 20);
    osMap.put("ps3 system software", 21);
    osMap.put("ps4 software", 22);
    osMap.put("psp system software", 23);
    osMap.put("symbian", 24);
    osMap.put("tizen", 25);
    osMap.put("watchos", 26);
    osMap.put("webos", 27);
    osMap.put("windows", 28);
  }

  public Request mapRequestToBidRequest(BidRequest bidRequest) {
    if ( bidRequest == null ) {
      return null;
    }

    Request request = new Request();

    request.setContext( bidRequestToContext( bidRequest ) );
    request.setItem( impListToItemList( bidRequest.getImp(), bidRequest ) );
    request.setPack( bidRequest.getAllimps() );
    String customdata = bidRequestUserCustomdata( bidRequest );
    if ( customdata != null ) {
      request.setCdata( customdata );
    }
    request.setId( bidRequest.getId() );
    request.setTest( bidRequest.getTest() );
    request.setTmax( bidRequest.getTmax() );
    request.setAt( bidRequest.getAt() );
    List<String> list1 = bidRequest.getCur();
    if ( list1 != null ) {
      request.setCur( new ArrayList<String>( list1 ) );
    }
    request.setSource( mapRtb24SourcetoRtb3Source( bidRequest.getSource() ) );
    Map<String, Object> map = bidRequest.getExt();
    if ( map != null ) {
      request.setExt( new HashMap<String, Object>( map ) );
    }

    mapWseatAndExt( request, bidRequest );

    return request;
  }


  public net.media.openrtb3.Source mapRtb24SourcetoRtb3Source(Source source) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.Source source1 = new net.media.openrtb3.Source();

    source1.setTid( source.getTid() );
    source1.setPchain( source.getPchain() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      source1.setExt( new HashMap<String, Object>( map ) );
    }

    mapSourceExt( source1, source );

    return source1;
  }


  public Site mapRtb24SitetoRtb3Site(net.media.openrtb24.request.Site site) {
    if ( site == null ) {
      return null;
    }

    Site site1 = new Site();

    site1.setPrivpolicy( site.getPrivacypolicy() );
    site1.setSectcat( stringListToStringArray( site.getSectioncat() ) );
    site1.setPub( publisherToPublisher( site.getPublisher() ) );
    site1.setId( site.getId() );
    site1.setName( site.getName() );
    site1.setContent( mapRtb24ContenttoRtb3Content( site.getContent() ) );
    site1.setDomain( site.getDomain() );
    List<String> list = site.getCat();
    if ( list != null ) {
      site1.setCat( new ArrayList<String>( list ) );
    }
    site1.setPagecat( stringSetToStringArray( site.getPagecat() ) );
    site1.setKeywords( site.getKeywords() );
    site1.setPage( site.getPage() );
    site1.setRef( site.getRef() );
    site1.setSearch( site.getSearch() );
    site1.setMobile( site.getMobile() );
    Map<String, Object> map = site.getExt();
    if ( map != null ) {
      site1.setExt( new HashMap<String, Object>( map ) );
    }

    mapSiteExt( site1, site );

    return site1;
  }


  public App mapRtb24ApptoRtb3App(net.media.openrtb24.request.App app) {
    if ( app == null ) {
      return null;
    }

    App app1 = new App();

    app1.setPrivpolicy( app.getPrivacypolicy() );
    app1.setSectcat( stringListToStringArray( app.getSectioncat() ) );
    app1.setPub( publisherToPublisher( app.getPublisher() ) );
    app1.setId( app.getId() );
    app1.setName( app.getName() );
    app1.setContent( mapRtb24ContenttoRtb3Content( app.getContent() ) );
    app1.setDomain( app.getDomain() );
    app1.setCat( stringListToStringArray1( app.getCat() ) );
    app1.setPagecat( stringSetToStringArray( app.getPagecat() ) );
    app1.setKeywords( app.getKeywords() );
    app1.setBundle( app.getBundle() );
    app1.setStoreurl( app.getStoreurl() );
    app1.setVer( app.getVer() );
    app1.setPaid( app.getPaid() );
    Map<String, Object> map = app.getExt();
    if ( map != null ) {
      app1.setExt( new HashMap<String, Object>( map ) );
    }

    mapAppExt( app1, app );

    return app1;
  }


  public net.media.openrtb3.Content mapRtb24ContenttoRtb3Content(Content content) {
    if ( content == null ) {
      return null;
    }

    net.media.openrtb3.Content content1 = new net.media.openrtb3.Content();

    content1.setMrating( content.getQagmediarating() );
    content1.setSrcrel( content.getSourcerelationship() );
    content1.setRating( content.getContentrating() );
    content1.setEmbed( content.getEmbeddable() );
    content1.setUrating( content.getUserrating() );
    content1.setLang( content.getLanguage() );
    content1.setLive( content.getLivestream() );
    content1.setId( content.getId() );
    content1.setEpisode( content.getEpisode() );
    content1.setTitle( content.getTitle() );
    content1.setSeries( content.getSeries() );
    content1.setSeason( content.getSeason() );
    content1.setArtist( content.getArtist() );
    content1.setGenre( content.getGenre() );
    content1.setAlbum( content.getAlbum() );
    content1.setIsrc( content.getIsrc() );
    content1.setUrl( content.getUrl() );
    content1.setCat( stringListToStringArray1( content.getCat() ) );
    content1.setProdq( content.getProdq() );
    content1.setContext( content.getContext() );
    content1.setKeywords( content.getKeywords() );
    content1.setLen( content.getLen() );
    content1.setProducer( mapRtb24ProducertoRtb3Producer( content.getProducer() ) );
    content1.setData( dataListToDataList( content.getData() ) );
    Map<String, Object> map = content.getExt();
    if ( map != null ) {
      content1.setExt( new HashMap<String, Object>( map ) );
    }

    mapContentExt( content1, content );

    return content1;
  }


  public Producer mapRtb24ProducertoRtb3Producer(net.media.openrtb24.request.Producer producer) {
    if ( producer == null ) {
      return null;
    }

    Producer producer1 = new Producer();

    producer1.setId( producer.getId() );
    producer1.setName( producer.getName() );
    producer1.setDomain( producer.getDomain() );
    producer1.setCat( stringListToStringArray1( producer.getCat() ) );
    Map<String, Object> map = producer.getExt();
    if ( map != null ) {
      producer1.setExt( new HashMap<String, Object>( map ) );
    }

    mapProducerExt( producer1, producer );

    return producer1;
  }


  public net.media.openrtb3.User mapRtb24UsertoRtb3User(User user) {
    if ( user == null ) {
      return null;
    }

    net.media.openrtb3.User user1 = new net.media.openrtb3.User();

    user1.setId( user.getId() );
    user1.setBuyeruid( user.getBuyeruid() );
    user1.setYob( user.getYob() );
    user1.setGender( user.getGender() );
    user1.setKeywords( user.getKeywords() );
    user1.setGeo( mapRtb24GeotoRtb3Geo( user.getGeo() ) );
    user1.setData( dataListToDataArray( user.getData() ) );
    Map<String, Object> map = user.getExt();
    if ( map != null ) {
      user1.setExt( new HashMap<String, Object>( map ) );
    }

    mapUserExt( user1, user );

    return user1;
  }


  public Regs mapRtb24RegstoRtb3Regs(net.media.openrtb24.request.Regs regs) {
    if ( regs == null ) {
      return null;
    }

    Regs regs1 = new Regs();

    regs1.setCoppa( regs.getCoppa() );
    Map<String, Object> map = regs.getExt();
    if ( map != null ) {
      regs1.setExt( new HashMap<String, Object>( map ) );
    }

    mapRegsExt( regs1, regs );

    return regs1;
  }


  public net.media.openrtb3.Geo mapRtb24GeotoRtb3Geo(Geo geo) {
    if ( geo == null ) {
      return null;
    }

    net.media.openrtb3.Geo geo1 = new net.media.openrtb3.Geo();

    geo1.setIpserv( geo.getIpservice() );
    geo1.setAccur( geo.getAccuracy() );
    geo1.setType( geo.getType() );
    geo1.setLat( geo.getLat() );
    geo1.setLon( geo.getLon() );
    geo1.setLastfix( geo.getLastfix() );
    geo1.setCountry( geo.getCountry() );
    geo1.setRegion( geo.getRegion() );
    geo1.setMetro( geo.getMetro() );
    geo1.setCity( geo.getCity() );
    geo1.setUtcoffset( geo.getUtcoffset() );
    geo1.setZip( geo.getZip() );
    Map<String, Object> map = geo.getExt();
    if ( map != null ) {
      geo1.setExt( new HashMap<String, Object>( map ) );
    }

    if(geo.getRegionfips104() != null) {
      if(geo1.getExt() == null) {
        geo1.setExt(new HashMap<>());
      }
      geo1.getExt().put("regionfips104", geo.getRegionfips104());
    }

    return geo1;
  }


  public Device mapRtb24DevicetoRtb3Device(net.media.openrtb24.request.Device device) {
    if ( device == null ) {
      return null;
    }

    Device device1 = new Device();

    device1.setContype( device.getConnectiontype() );
    device1.setType( device.getDevicetype() );
    device1.setLang( device.getLanguage() );
    device1.setUa( device.getUa() );
    device1.setIfa( device.getIfa() );
    if ( device.getDnt() != null ) {
      device1.setDnt( String.valueOf( device.getDnt() ) );
    }
    device1.setLmt( device.getLmt() );
    device1.setMake( device.getMake() );
    device1.setModel( device.getModel() );
    if ( device.getOs() != null ) {
      if(osMap.containsKey(device.getOs().trim().toLowerCase()))
        device1.setOs(osMap.get(device.getOs().trim().toLowerCase()));
      else
        device1.setOs(0);
    }
    device1.setOsv( device.getOsv() );
    device1.setHwv( device.getHwv() );
    device1.setH( device.getH() );
    device1.setW( device.getW() );
    device1.setPpi( device.getPpi() );
    device1.setPxratio( device.getPxratio() );
    device1.setJs( device.getJs() );
    device1.setIp( device.getIp() );
    device1.setIpv6( device.getIpv6() );
    device1.setCarrier( device.getCarrier() );
    device1.setGeofetch( device.getGeofetch() );
    device1.setGeo( mapRtb24GeotoRtb3Geo( device.getGeo() ) );
    Map<String, Object> map = device.getExt();
    if ( map != null ) {
      device1.setExt( new HashMap<String, Object>( map ) );
    }

    mapDeviceExt( device1, device );

    return device1;
  }


  public Restrictions mapRtb3Restriction(BidRequest bidRequest) {
    if ( bidRequest == null ) {
      return null;
    }

    Restrictions restrictions = new Restrictions();

    restrictions.setBapp( stringListToStringArray( bidRequest.getBapp() ) );
    restrictions.setBcat( stringSetToStringArray1( bidRequest.getBcat() ) );
    restrictions.setBadv( stringSetToStringArray1( bidRequest.getBadv() ) );

    updateBattrRestriction( restrictions, bidRequest );

    return restrictions;
  }


  public BidRequest mapRtb3RequestToRtb24BidRequest(Request request) {
    if ( request == null ) {
      return null;
    }

    BidRequest bidRequest = new BidRequest();

    if ( request.getContext() != null ) {
      if ( bidRequest.getUser() == null ) {
        bidRequest.setUser( new User() );
      }
      contextToUser( request.getContext(), bidRequest.getUser() );
    }
    else {
      bidRequest.setUser( null );
    }
    if ( bidRequest.getUser() == null ) {
      bidRequest.setUser( new User() );
    }
    requestToUser( request, bidRequest.getUser() );
    App app = requestContextApp( request );
    if ( app != null ) {
      bidRequest.setApp( mapRtb24ApptoRtb3App( app ) );
    }
    Map<String, Object> map = request.getExt();
    if ( map != null ) {
      bidRequest.setExt( new HashMap<String, Object>( map ) );
    }
    bidRequest.setAllimps( request.getPack() );
    Regs regs = requestContextRegs( request );
    if ( regs != null ) {
      bidRequest.setRegs( mapRtb3RegstoRtb24Regs( regs ) );
    }
    bidRequest.setImp( itemListToImpList( request.getItem(), request ) );
    String[] bapp = requestContextRestrictionsBapp( request );
    bidRequest.setBapp( stringArrayToStringList( bapp ) );
    Site site = requestContextSite( request );
    if ( site != null ) {
      bidRequest.setSite( mapRtb3SitetoRtb24Site( site ) );
    }
    String[] bcat = requestContextRestrictionsBcat( request );
    bidRequest.setBcat( stringArrayToStringSet( bcat ) );
    Device device = requestContextDevice( request );
    if ( device != null ) {
      bidRequest.setDevice( mapRtb3DevicetoRtb24Device( device ) );
    }
    String[] badv = requestContextRestrictionsBadv( request );
    bidRequest.setBadv( stringArrayToStringSet( badv ) );
    bidRequest.setId( request.getId() );
    bidRequest.setAt( request.getAt() );
    bidRequest.setTest( request.getTest() );
    bidRequest.setTmax( request.getTmax() );
    bidRequest.setSource( mapRtb3SourcetoRtb24Source( request.getSource() ) );
    List<String> list1 = request.getCur();
    if ( list1 != null ) {
      bidRequest.setCur( new ArrayList<String>( list1 ) );
    }

    updateRtb3ToRtb24BidRequest( bidRequest, request );

    return bidRequest;
  }


  public net.media.openrtb24.request.Device mapRtb3DevicetoRtb24Device(Device device) {
    if ( device == null ) {
      return null;
    }

    DeviceBuilder device1 = net.media.openrtb24.request.Device.builder();

    device1.language( device.getLang() );
    device1.connectiontype( device.getContype() );
    device1.devicetype( device.getType() );
    device1.ua( device.getUa() );
    device1.geo( mapRtb3GeotoRtb24Geo( device.getGeo() ) );
    if ( device.getDnt() != null ) {
      device1.dnt( Integer.parseInt( device.getDnt() ) );
    }
    device1.lmt( device.getLmt() );
    device1.ip( device.getIp() );
    device1.ipv6( device.getIpv6() );
    if ( device.getOs() != null ) {
      if( osMap.inverse().containsKey( device.getOs() ) )
        device1.os( osMap.inverse().get( device.getOs() ) );
    }
    device1.make( device.getMake() );
    device1.model( device.getModel() );
    device1.osv( device.getOsv() );
    device1.hwv( device.getHwv() );
    device1.h( device.getH() );
    device1.w( device.getW() );
    device1.ppi( device.getPpi() );
    device1.pxratio( device.getPxratio() );
    device1.js( device.getJs() );
    device1.geofetch( device.getGeofetch() );
    device1.carrier( device.getCarrier() );
    device1.ifa( device.getIfa() );
    Map<String, Object> map = device.getExt();
    if ( map != null ) {
      device1.ext( new HashMap<String, Object>( map ) );
    }

    return device1.build();
  }


  public net.media.openrtb24.request.Regs mapRtb3RegstoRtb24Regs(Regs regs) {
    if ( regs == null ) {
      return null;
    }

    net.media.openrtb24.request.Regs regs1 = new net.media.openrtb24.request.Regs();

    regs1.setCoppa( regs.getCoppa() );
    Map<String, Object> map = regs.getExt();
    if ( map != null ) {
      regs1.setExt( new HashMap<String, Object>( map ) );
    }

    mapRegsTo24( regs1, regs );

    return regs1;
  }


  public Geo mapRtb3GeotoRtb24Geo(net.media.openrtb3.Geo geo) {
    if ( geo == null ) {
      return null;
    }

    Geo geo1 = new Geo();

    geo1.setIpservice( geo.getIpserv() );
    geo1.setAccuracy( geo.getAccur() );
    geo1.setType( geo.getType() );
    geo1.setRegion( geo.getRegion() );
    geo1.setMetro( geo.getMetro() );
    geo1.setCity( geo.getCity() );
    geo1.setZip( geo.getZip() );
    geo1.setCountry( geo.getCountry() );
    geo1.setLat( geo.getLat() );
    geo1.setLon( geo.getLon() );
    geo1.setUtcoffset( geo.getUtcoffset() );
    geo1.setLastfix( geo.getLastfix() );
    Map<String, Object> map = geo.getExt();
    if ( map != null ) {
      geo1.setExt( new HashMap<String, Object>( map ) );
    }

    mapGeoTo24( geo1, geo );

    return geo1;
  }


  public Source mapRtb3SourcetoRtb24Source(net.media.openrtb3.Source source) {
    if ( source == null ) {
      return null;
    }

    Source source1 = new Source();

    source1.setTid( source.getTid() );
    source1.setPchain( source.getPchain() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      source1.setExt( new HashMap<String, Object>( map ) );
    }

    mapSourceTo24( source1, source );

    return source1;
  }


  public net.media.openrtb24.request.Site mapRtb3SitetoRtb24Site(Site site) {
    if ( site == null ) {
      return null;
    }

    net.media.openrtb24.request.Site site1 = new net.media.openrtb24.request.Site();

    site1.setSectioncat( stringArrayToStringList( site.getSectcat() ) );
    site1.setPrivacypolicy( site.getPrivpolicy() );
    site1.setPublisher( publisherToPublisher1( site.getPub() ) );
    site1.setId( site.getId() );
    site1.setName( site.getName() );
    site1.setDomain( site.getDomain() );
    List<String> list1 = site.getCat();
    if ( list1 != null ) {
      site1.setCat( new ArrayList<String>( list1 ) );
    }
    site1.setPagecat( stringArrayToStringSet1( site.getPagecat() ) );
    site1.setPage( site.getPage() );
    site1.setRef( site.getRef() );
    site1.setSearch( site.getSearch() );
    site1.setMobile( site.getMobile() );
    site1.setContent( mapRtb3ContenttoRtb24Content( site.getContent() ) );
    site1.setKeywords( site.getKeywords() );
    Map<String, Object> map = site.getExt();
    if ( map != null ) {
      site1.setExt( new HashMap<String, Object>( map ) );
    }

    mapSiteTo24( site1, site );

    return site1;
  }


  public net.media.openrtb24.request.App mapRtb24ApptoRtb3App(App app) {
    if ( app == null ) {
      return null;
    }

    net.media.openrtb24.request.App app1 = new net.media.openrtb24.request.App();

    app1.setSectioncat( stringArrayToStringList( app.getSectcat() ) );
    app1.setPrivacypolicy( app.getPrivpolicy() );
    app1.setPublisher( publisherToPublisher1( app.getPub() ) );
    app1.setId( app.getId() );
    app1.setName( app.getName() );
    app1.setBundle( app.getBundle() );
    app1.setDomain( app.getDomain() );
    app1.setStoreurl( app.getStoreurl() );
    app1.setCat( stringArrayToStringList1( app.getCat() ) );
    app1.setPagecat( stringArrayToStringSet1( app.getPagecat() ) );
    app1.setVer( app.getVer() );
    app1.setPaid( app.getPaid() );
    app1.setContent( mapRtb3ContenttoRtb24Content( app.getContent() ) );
    app1.setKeywords( app.getKeywords() );
    Map<String, Object> map = app.getExt();
    if ( map != null ) {
      app1.setExt( new HashMap<String, Object>( map ) );
    }

    mapAppTo24( app1, app );

    return app1;
  }


  public Content mapRtb3ContenttoRtb24Content(net.media.openrtb3.Content content) {
    if ( content == null ) {
      return null;
    }

    Content content1 = new Content();

    content1.setContentrating( content.getRating() );
    content1.setSourcerelationship( content.getSrcrel() );
    content1.setUserrating( content.getUrating() );
    content1.setLanguage( content.getLang() );
    content1.setQagmediarating( content.getMrating() );
    content1.setEmbeddable( content.getEmbed() );
    content1.setLivestream( content.getLive() );
    content1.setId( content.getId() );
    content1.setEpisode( content.getEpisode() );
    content1.setTitle( content.getTitle() );
    content1.setSeries( content.getSeries() );
    content1.setSeason( content.getSeason() );
    content1.setArtist( content.getArtist() );
    content1.setGenre( content.getGenre() );
    content1.setAlbum( content.getAlbum() );
    content1.setIsrc( content.getIsrc() );
    content1.setProducer( mapRtb3ProducertoRtb24Producer( content.getProducer() ) );
    content1.setUrl( content.getUrl() );
    content1.setCat( stringArrayToStringList1( content.getCat() ) );
    content1.setProdq( content.getProdq() );
    content1.setContext( content.getContext() );
    content1.setKeywords( content.getKeywords() );
    content1.setLen( content.getLen() );
    content1.setData( dataListToDataList1( content.getData() ) );
    Map<String, Object> map = content.getExt();
    if ( map != null ) {
      content1.setExt( new HashMap<String, Object>( map ) );
    }

    mapContentTo24( content1, content );

    return content1;
  }


  public net.media.openrtb24.request.Producer mapRtb3ProducertoRtb24Producer(Producer producer) {
    if ( producer == null ) {
      return null;
    }

    net.media.openrtb24.request.Producer producer1 = new net.media.openrtb24.request.Producer();

    producer1.setId( producer.getId() );
    producer1.setName( producer.getName() );
    producer1.setCat( stringArrayToStringList1( producer.getCat() ) );
    producer1.setDomain( producer.getDomain() );
    Map<String, Object> map = producer.getExt();
    if ( map != null ) {
      producer1.setExt( new HashMap<String, Object>( map ) );
    }

    mapProducerTo24( producer1, producer );

    return producer1;
  }


  public User mapRtb3UsertoRtb24User(net.media.openrtb3.User user) {
    if ( user == null ) {
      return null;
    }

    User user1 = new User();

    user1.setId( user.getId() );
    user1.setBuyeruid( user.getBuyeruid() );
    user1.setYob( user.getYob() );
    user1.setGender( user.getGender() );
    user1.setGeo( mapRtb3GeotoRtb24Geo( user.getGeo() ) );
    user1.setKeywords( user.getKeywords() );
    user1.setData( dataArrayToDataList( user.getData() ) );
    Map<String, Object> map = user.getExt();
    if ( map != null ) {
      user1.setExt( new HashMap<String, Object>( map ) );
    }

    mapUser24( user1, user );

    return user1;
  }

  protected Context bidRequestToContext(BidRequest bidRequest) {
    if ( bidRequest == null ) {
      return null;
    }

    Context context = new Context();

    context.setRegs( mapRtb24RegstoRtb3Regs( bidRequest.getRegs() ) );
    context.setSite( mapRtb24SitetoRtb3Site( bidRequest.getSite() ) );
    context.setUser( mapRtb24UsertoRtb3User( bidRequest.getUser() ) );
    context.setRestrictions( mapRtb3Restriction( bidRequest ) );
    context.setApp( mapRtb24ApptoRtb3App( bidRequest.getApp() ) );
    context.setDevice( mapRtb24DevicetoRtb3Device( bidRequest.getDevice() ) );

    return context;
  }

  protected Item impToItem(Imp imp) {
    if ( imp == null ) {
      return null;
    }

    Item item = new Item();

    item.setId( imp.getId() );
    item.setExp( imp.getExp() );
    Map<String, Object> map = imp.getExt();
    if ( map != null ) {
      item.setExt( new HashMap<String, Object>( map ) );
    }

    return item;
  }

  protected List<Item> impListToItemList(List<Imp> list, BidRequest bidRequest) {
    if ( list == null ) {
      return null;
    }

    List<Item> list1 = new ArrayList<Item>( list.size() );
    for ( Imp imp : list ) {
      list1.add( impItemConverter.impToItem( imp, bidRequest ) );
    }

    return list1;
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

  protected String[] stringListToStringArray(List<String> list) {
    if ( list == null ) {
      return null;
    }

    String[] stringTmp = new String[list.size()];
    int i = 0;
    for ( String string : list ) {
      stringTmp[i] = string;
      i++;
    }

    return stringTmp;
  }

  protected String[] stringListToStringArray1(List<String> list) {
    if ( list == null ) {
      return null;
    }

    String[] stringTmp = new String[list.size()];
    int i = 0;
    for ( String string : list ) {
      stringTmp[i] = string;
      i++;
    }

    return stringTmp;
  }

  protected net.media.openrtb3.Publisher publisherToPublisher(Publisher publisher) {
    if ( publisher == null ) {
      return null;
    }

    net.media.openrtb3.Publisher publisher1 = new net.media.openrtb3.Publisher();

    publisher1.setId( publisher.getId() );
    publisher1.setName( publisher.getName() );
    publisher1.setDomain( publisher.getDomain() );
    publisher1.setCat( stringListToStringArray1( publisher.getCat() ) );
    Map<String, Object> map = publisher.getExt();
    if ( map != null ) {
      publisher1.setExt( new HashMap<String, Object>( map ) );
    }

    return publisher1;
  }

  protected String[] stringSetToStringArray(Set<String> set) {
    if ( set == null ) {
      return null;
    }

    String[] stringTmp = new String[set.size()];
    int i = 0;
    for ( String string : set ) {
      stringTmp[i] = string;
      i++;
    }

    return stringTmp;
  }

  protected net.media.openrtb3.Segment segmentToSegment(Segment segment) {
    if ( segment == null ) {
      return null;
    }

    net.media.openrtb3.Segment segment1 = new net.media.openrtb3.Segment();

    segment1.setId( segment.getId() );
    segment1.setName( segment.getName() );
    segment1.setValue( segment.getValue() );
    Map<String, Object> map = segment.getExt();
    if ( map != null ) {
      segment1.setExt( new HashMap<String, Object>( map ) );
    }

    return segment1;
  }

  protected List<net.media.openrtb3.Segment> segmentListToSegmentList(List<Segment> list) {
    if ( list == null ) {
      return null;
    }

    List<net.media.openrtb3.Segment> list1 = new ArrayList<net.media.openrtb3.Segment>( list.size() );
    for ( Segment segment : list ) {
      list1.add( segmentToSegment( segment ) );
    }

    return list1;
  }

  protected net.media.openrtb3.Data dataToData(Data data) {
    if ( data == null ) {
      return null;
    }

    net.media.openrtb3.Data data1 = new net.media.openrtb3.Data();

    data1.setId( data.getId() );
    data1.setName( data.getName() );
    data1.setSegment( segmentListToSegmentList( data.getSegment() ) );
    Map<String, Object> map = data.getExt();
    if ( map != null ) {
      data1.setExt( new HashMap<String, Object>( map ) );
    }

    return data1;
  }

  protected List<net.media.openrtb3.Data> dataListToDataList(List<Data> list) {
    if ( list == null ) {
      return null;
    }

    List<net.media.openrtb3.Data> list1 = new ArrayList<net.media.openrtb3.Data>( list.size() );
    for ( Data data : list ) {
      list1.add( dataToData( data ) );
    }

    return list1;
  }

  protected net.media.openrtb3.Data[] dataListToDataArray(List<Data> list) {
    if ( list == null ) {
      return null;
    }

    net.media.openrtb3.Data[] dataTmp = new net.media.openrtb3.Data[list.size()];
    int i = 0;
    for ( Data data : list ) {
      dataTmp[i] = dataToData( data );
      i++;
    }

    return dataTmp;
  }

  protected String[] stringSetToStringArray1(Set<String> set) {
    if ( set == null ) {
      return null;
    }

    String[] stringTmp = new String[set.size()];
    int i = 0;
    for ( String string : set ) {
      stringTmp[i] = string;
      i++;
    }

    return stringTmp;
  }

  protected void contextToUser(Context context, User mappingTarget) {
    if ( context == null ) {
      return;
    }
  }

  protected void requestToUser(Request request, User mappingTarget) {
    if ( request == null ) {
      return;
    }

    mappingTarget.setCustomdata( request.getCdata() );
  }

  private App requestContextApp(Request request) {
    if ( request == null ) {
      return null;
    }
    Context context = request.getContext();
    if ( context == null ) {
      return null;
    }
    App app = context.getApp();
    if ( app == null ) {
      return null;
    }
    return app;
  }

  private Regs requestContextRegs(Request request) {
    if ( request == null ) {
      return null;
    }
    Context context = request.getContext();
    if ( context == null ) {
      return null;
    }
    Regs regs = context.getRegs();
    if ( regs == null ) {
      return null;
    }
    return regs;
  }

  private String[] requestContextRestrictionsBapp(Request request) {
    if ( request == null ) {
      return null;
    }
    Context context = request.getContext();
    if ( context == null ) {
      return null;
    }
    Restrictions restrictions = context.getRestrictions();
    if ( restrictions == null ) {
      return null;
    }
    String[] bapp = restrictions.getBapp();
    if ( bapp == null ) {
      return null;
    }
    return bapp;
  }

  protected List<String> stringArrayToStringList(String[] stringArray) {
    if ( stringArray == null ) {
      return null;
    }

    List<String> list = new ArrayList<String>( stringArray.length );
    for ( String string : stringArray ) {
      list.add( string );
    }

    return list;
  }

  private Site requestContextSite(Request request) {
    if ( request == null ) {
      return null;
    }
    Context context = request.getContext();
    if ( context == null ) {
      return null;
    }
    Site site = context.getSite();
    if ( site == null ) {
      return null;
    }
    return site;
  }

  private String[] requestContextRestrictionsBcat(Request request) {
    if ( request == null ) {
      return null;
    }
    Context context = request.getContext();
    if ( context == null ) {
      return null;
    }
    Restrictions restrictions = context.getRestrictions();
    if ( restrictions == null ) {
      return null;
    }
    String[] bcat = restrictions.getBcat();
    if ( bcat == null ) {
      return null;
    }
    return bcat;
  }

  protected Set<String> stringArrayToStringSet(String[] stringArray) {
    if ( stringArray == null ) {
      return null;
    }

    Set<String> set = new HashSet<String>( Math.max( (int) ( stringArray.length / .75f ) + 1, 16 ) );
    for ( String string : stringArray ) {
      set.add( string );
    }

    return set;
  }

  private Device requestContextDevice(Request request) {
    if ( request == null ) {
      return null;
    }
    Context context = request.getContext();
    if ( context == null ) {
      return null;
    }
    Device device = context.getDevice();
    if ( device == null ) {
      return null;
    }
    return device;
  }

  private String[] requestContextRestrictionsBadv(Request request) {
    if ( request == null ) {
      return null;
    }
    Context context = request.getContext();
    if ( context == null ) {
      return null;
    }
    Restrictions restrictions = context.getRestrictions();
    if ( restrictions == null ) {
      return null;
    }
    String[] badv = restrictions.getBadv();
    if ( badv == null ) {
      return null;
    }
    return badv;
  }

  protected List<String> stringArrayToStringList1(String[] stringArray) {
    if ( stringArray == null ) {
      return null;
    }

    List<String> list = new ArrayList<String>( stringArray.length );
    for ( String string : stringArray ) {
      list.add( string );
    }

    return list;
  }

  protected Publisher publisherToPublisher1(net.media.openrtb3.Publisher publisher) {
    if ( publisher == null ) {
      return null;
    }

    Publisher publisher1 = new Publisher();

    publisher1.setId( publisher.getId() );
    publisher1.setName( publisher.getName() );
    publisher1.setCat( stringArrayToStringList1( publisher.getCat() ) );
    publisher1.setDomain( publisher.getDomain() );
    Map<String, Object> map = publisher.getExt();
    if ( map != null ) {
      publisher1.setExt( new HashMap<String, Object>( map ) );
    }

    return publisher1;
  }

  protected Set<String> stringArrayToStringSet1(String[] stringArray) {
    if ( stringArray == null ) {
      return null;
    }

    Set<String> set = new HashSet<String>( Math.max( (int) ( stringArray.length / .75f ) + 1, 16 ) );
    for ( String string : stringArray ) {
      set.add( string );
    }

    return set;
  }

  protected Segment segmentToSegment1(net.media.openrtb3.Segment segment) {
    if ( segment == null ) {
      return null;
    }

    Segment segment1 = new Segment();

    segment1.setId( segment.getId() );
    segment1.setName( segment.getName() );
    segment1.setValue( segment.getValue() );
    Map<String, Object> map = segment.getExt();
    if ( map != null ) {
      segment1.setExt( new HashMap<String, Object>( map ) );
    }

    return segment1;
  }

  protected List<Segment> segmentListToSegmentList1(List<net.media.openrtb3.Segment> list) {
    if ( list == null ) {
      return null;
    }

    List<Segment> list1 = new ArrayList<Segment>( list.size() );
    for ( net.media.openrtb3.Segment segment : list ) {
      list1.add( segmentToSegment1( segment ) );
    }

    return list1;
  }

  protected Data dataToData1(net.media.openrtb3.Data data) {
    if ( data == null ) {
      return null;
    }

    Data data1 = new Data();

    data1.setId( data.getId() );
    data1.setName( data.getName() );
    data1.setSegment( segmentListToSegmentList1( data.getSegment() ) );
    Map<String, Object> map = data.getExt();
    if ( map != null ) {
      data1.setExt( new HashMap<String, Object>( map ) );
    }

    return data1;
  }

  protected List<Data> dataListToDataList1(List<net.media.openrtb3.Data> list) {
    if ( list == null ) {
      return null;
    }

    List<Data> list1 = new ArrayList<Data>( list.size() );
    for ( net.media.openrtb3.Data data : list ) {
      list1.add( dataToData1( data ) );
    }

    return list1;
  }

  protected List<Data> dataArrayToDataList(net.media.openrtb3.Data[] dataArray) {
    if ( dataArray == null ) {
      return null;
    }

    List<Data> list = new ArrayList<Data>( dataArray.length );
    for ( net.media.openrtb3.Data data : dataArray ) {
      list.add( dataToData1( data ) );
    }

    return list;
  }

  protected void mapWseatAndExt(Request target, BidRequest source) {
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

  protected void mapSourceExt(@MappingTarget net.media.openrtb3.Source target, net.media
    .openrtb24.request.Source source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setTs((Integer) source.getExt().get("ts"));
    target.setDs((String) source.getExt().get("ds"));
    target.setDsmap((String) source.getExt().get("dsMap"));
    target.setCert((String) source.getExt().get("cert"));
    target.setDigest((String) source.getExt().get("digest"));
    target.getExt().remove("ts");
    target.getExt().remove("ds");
    target.getExt().remove("dsMap");
    target.getExt().remove("cert");
    target.getExt().remove("digest");

    if(source.getFd() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("fd", source.getFd());
    }
  }

  protected void mapSiteExt(Site target, net.media.openrtb24.request.Site source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setCattax((Integer) source.getExt().get("cattax"));
    target.setAmp((Integer) source.getExt().get("amp"));
    target.getExt().remove("cattax");
    target.getExt().remove("amp");
  }

  protected void mapAppExt(App target, net.media.openrtb24.request.App source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setCattax((Integer) source.getExt().get("cattax"));
    target.setStoreid((String) source.getExt().get("storeid"));
    target.getExt().remove("cattax");
    target.getExt().remove("storeid");
  }

  protected void mapContentExt(net.media.openrtb3.Content target, net.media
    .openrtb24.request.Content source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setCattax((Integer) source.getExt().get("cattax"));
    target.getExt().remove("cattax");
  }

  protected void mapProducerExt(Producer target, net.media.openrtb24.request.Producer
    source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setCattax((Integer) source.getExt().get("cattax"));
    target.getExt().remove("cattax");
  }

  protected void mapUserExt(net.media.openrtb3.User target, net.media.openrtb24.request
    .User source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setConsent((String) source.getExt().get("consent"));
    target.getExt().remove("consent");
  }

  protected void mapRegsExt(Regs target, net.media.openrtb24.request.Regs source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setGdpr((Integer) source.getExt().get("gdpr"));
    target.getExt().remove("gdpr");
  }

  protected void mapDeviceExt(Device target, net.media.openrtb24.request.Device source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    target.setXff((String) source.getExt().get("xff"));
    target.getExt().remove("xff");
    target.setIptr((Integer) source.getExt().get("iptr"));
    target.getExt().remove("iptr");
    target.setIptr((Integer) source.getExt().get("mccmncsim"));
    target.getExt().remove("mccmncsim");
  }

  protected void updateBattrRestriction(Restrictions restrictions, BidRequest bidRequest) {
    if(bidRequest == null)
      return;
    if(bidRequest.getImp() == null)
      return;
    if(bidRequest.getImp().size() == 0)
      return;
    Set<Integer> battr = new HashSet<>();
    for(Imp imp : bidRequest.getImp()) {
      if(imp.getBanner() != null && imp.getBanner().getBattr() != null) {
        battr.addAll(imp.getBanner().getBattr());
      } else if(imp.getVideo() != null && imp.getVideo().getBattr() != null) {
        battr.addAll(imp.getVideo().getBattr());
      } else if(imp.getNat() != null && imp.getNat().getBattr() != null) {
        battr.addAll(imp.getNat().getBattr());
      }
    }
    if(battr.size()>0) {
      restrictions.setBattr(battr);
    }
    if(bidRequest.getExt() == null)
      return;
    restrictions.setCattax((Integer) bidRequest.getExt().get("cattax"));
    if(bidRequest.getExt().containsKey("restrictionsExt"))
      restrictions.setExt((Map<String, Object>) bidRequest.getExt().get("restrictionsExt"));
  }

  protected void updateRtb3ToRtb24BidRequest(BidRequest target, Request source) {
    if(source == null)
      return;

    if(source.getWseat() == null)
      return;

    if (source.getWseat() == 0){
      target.setBseat(source.getSeat());
    } else {
      target.setWseat(source.getSeat());
    }

    if(source.getContext() == null)
      return;

    if(source.getContext().getRestrictions() == null)
      return;

    if(source.getContext().getRestrictions().getCattax() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cattax", source.getContext().getRestrictions().getCattax());
    }

    if(source.getContext().getRestrictions().getExt() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("restrictionsExt", source.getContext().getRestrictions().getExt());
    }

    if(target.getImp() == null)
      return;
    for(Imp imp : target.getImp()) {
      if(imp.getBanner() != null)
        imp.getBanner().setBattr(source.getContext().getRestrictions().getBattr());
      if(imp.getVideo() != null)
        imp.getVideo().setBattr(source.getContext().getRestrictions().getBattr());
      if(imp.getNat() != null)
        imp.getNat().setBattr(source.getContext().getRestrictions().getBattr());
    }

    if(source.getContext().getDooh() == null)
      return;

    if(target.getExt() == null)
      target.setExt(new HashMap<>());
    target.getExt().put("dooh", source.getContext().getDooh());
  }

  protected void mapDeviceTo24(net.media.openrtb24.request.Device target, Device source) {
    if(source == null)
      return;

    if(source.getXff() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("xff", source.getXff());
    }

    if(source.getIptr() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("iptr", source.getXff());
    }

    if(source.getIptr() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("mccmncsim", source.getMccmncsim());
    }

    if(source.getExt() == null)
      return;

    if(source.getExt().containsKey("didsha1")) {
      target.setDidsha1((String) source.getExt().get("didsha1"));
      source.getExt().remove("didsha1");
    }

    if(source.getExt().containsKey("didmd5")) {
      target.setDidmd5((String) source.getExt().get("didmd5"));
      source.getExt().remove("didmd5");
    }

    if(source.getExt().containsKey("dpidsha1")) {
      target.setDpidsha1((String) source.getExt().get("dpidsha1"));
      source.getExt().remove("dpidsha1");
    }

    if(source.getExt().containsKey("dpidmd5")) {
      target.setDpidmd5((String) source.getExt().get("dpidmd5"));
      source.getExt().remove("dpidmd5");
    }

    if(source.getExt().containsKey("macsha1")) {
      target.setMacsha1((String) source.getExt().get("macsha1"));
      source.getExt().remove("macsha1");
    }

    if(source.getExt().containsKey("macmd5")) {
      target.setMacmd5((String) source.getExt().get("macmd5"));
      source.getExt().remove("macmd5");
    }
  }

  protected void mapRegsTo24(net.media.openrtb24.request.Regs target, Regs source) {
    if(source == null)
      return;
    if(source.getGdpr() == null)
      return;
    if(target.getExt() == null)
      target.setExt(new HashMap<>());
    target.getExt().put("gdpr", source.getGdpr());
  }

  protected void mapGeoTo24(net.media.openrtb24.request.Geo target, net.media.openrtb3.Geo
    source) {
    if(source == null)
      return;
    if(source.getExt() == null)
      return;
    if(source.getExt().containsKey("regionfips104")) {
      target.setRegionfips104((String) source.getExt().get("regionfips104"));
      source.getExt().remove("regionfips104");
    }
  }

  protected void mapSourceTo24(net.media.openrtb24.request.Source target, net.media
    .openrtb3.Source source) {
    if(source == null)
      return;
    if(source.getTs() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("ts", source.getTs());
    }
    if(source.getDs() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("ds", source.getDs());
    }
    if(source.getDsmap() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("dsMap", source.getDsmap());
    }
    if(source.getCert() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cert", source.getCert());
    }
    if(source.getDigest() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("digest", source.getDigest());
    }

    if(source.getExt() == null)
      return;
    if(source.getExt().containsKey("fd")) {
      target.setFd((Integer) source.getExt().get("fd"));
      target.getExt().remove("fd");
    }
  }

  protected void mapSiteTo24(net.media.openrtb24.request.Site target, Site source) {
    if(source == null)
      return;
    if(source.getCattax() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cattax", source.getCattax());
    }
    if(source.getAmp() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("amp", source.getAmp());
    }
  }

  protected void mapAppTo24(net.media.openrtb24.request.App target, App source) {
    if(source == null)
      return;
    if(source.getCattax() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cattax", source.getCattax());
    }
    if(source.getStoreid() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("amp", source.getStoreid());
    }
  }

  protected void mapContentTo24(net.media.openrtb24.request.Content target, net.media
    .openrtb3.Content source) {
    if(source == null)
      return;
    if(source.getCattax() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cattax", source.getCattax());
    }
  }

  protected void mapProducerTo24(net.media.openrtb24.request.Producer target, Producer
    source) {
    if(source == null)
      return;
    if(source.getCattax() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("cattax", source.getCattax());
    }
  }

  protected void mapUser24(net.media.openrtb24.request.User target, net.media
    .openrtb3.User source) {
    if(source == null)
      return;
    if(source.getConsent() != null) {
      if(target.getExt() == null)
        target.setExt(new HashMap<>());
      target.getExt().put("consent", source.getConsent());
    }
  }

  protected List<Imp> itemListToImpList(List<Item> list, Request request) {
    if ( list == null ) {
      return null;
    }

    List<Imp> list1 = new ArrayList<Imp>( list.size() );
    for ( Item item : list ) {
      list1.add( impItemConverter.itemToImp( item, request ) );
    }

    return list1;
  }


}
