package net.media.openrtb24.response;

import com.medianet.rtb.mowgli.commons.MarkupFactory;
import com.medianet.rtb.mowgli.commons.bidders.ImpressionEncapsula;
import com.medianet.rtb.mowgli.commons.dto.adexchange.ErrorCode;
import com.medianet.rtb.mowgli.commons.dto.openrtb.BidderResponse;
import com.medianet.rtb.mowgli.commons.enums.MarkupFormat;
import com.medianet.rtb.mowgli.commons.spi.AdMarkup;

import net.media.rtb.mowgli.utils.LoggingFacade;

import org.slf4j.event.Level;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.nonNull;

/**
 * Created by vishnu on 30/5/16.
 */
@Slf4j
@Data
@NoArgsConstructor
@ToString(exclude = {"impressionEncapsula"})
public class Bid {

  private String id;
  private String impid;
  private double price;
  private String adid;
  private String nurl;
  private Object adm;
  private List<String> adomain;
  private String bundle;
  private String iurl;
  private String cid;
  private String crid;
  private Set<String> cat;
  private List<Integer> attr;
  private Integer api;
  private Integer protocol;
  private Integer qagmediarating;
  private String dealid;
  private Integer h;
  private Integer w;
  private Integer exp;
  private Map ext;
  private double ogbid;
  private double grossBid;
  private boolean noBid = false;
  private String admToken;
  private String admUrl;
  private ErrorCode errorCode;
  private Long expireAt;
  transient private boolean cachedBid;
  transient private AdMarkup adMarkup;
  transient private MarkupFormat markupFormat;
  transient private MarkupFactory markupFactory;
  transient private Boolean htps = false;
  transient private String serializedAdmCache;
  transient private Integer originalH;
  transient private Integer originalW;
  transient private String currency;
  transient private boolean sendAdm = false;
  //TODO : Remove transient when system starts supporting multiple seatBids in the complete flow.
  transient private String seat;
  transient private String encryptedPrice;
  transient private ImpressionEncapsula impressionEncapsula;

  public boolean isMarkupNull() {
    return Objects.isNull(adMarkup);
  }

  public AdMarkup getDeserializedMarkup() {
    if (adMarkup != null)
      return adMarkup;
    if (Objects.nonNull(adm)) {
      adMarkup = markupFactory.createAdMarkup(adm, markupFormat, true);
      return adMarkup;
    }
    return null;
  }

  public String getAdmAsString() {
    if (Objects.isNull(serializedAdmCache) && adm instanceof String) {
      serializedAdmCache = (String) adm;
    }
    return serializedAdmCache;
  }

  public void setAdmAsString(String adm) {
    this.serializedAdmCache = adm;
    this.adm = adm;
  }

  public void setAdMarkup(AdMarkup adMarkup) {
    this.adMarkup = adMarkup;
  }

  public Object getAdmAsObject() {
    return this.adm;
  }

  public Integer getAttl(BidderResponse response) {
    try {
      return nonNull(this.ext) ? (Integer) this.ext.get("attl"): null;
    } catch (Exception e){
      LoggingFacade.getLoggingEncapsula().log(log, "Bid.getAttll",
        Level.ERROR, response.getLoggingKey(), e);
      return null;
    }
  }

}
