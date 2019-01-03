package net.media.openrtb24.request;


import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
public class BidRequest extends AbstractExtensible<BidRequest.BidReqExt> {

  //BidRequest parameters
  public static final Integer DEFAULT_TEST_VALUE = 0;
  public static final Integer DEFAULT_ALL_IMPS = 0;
  public static final Integer DEFAULT_AT = 2;

  public String id;

  @NotEmpty
  @NotBlank
  @Valid
  public List<Imp> imp;

  public Site site;

  public App app;

  public Device device;

  public User user;

  public Set<String> badv;

  public Integer at = 2;

  public Integer test = DEFAULT_TEST_VALUE;

  public Set<String> wseat;

  public Set<String> bseat;

  public Integer tmax;

  public Source source;

  public Set<String> bcat;

  public Integer allimps = DEFAULT_ALL_IMPS;

  public List<String> cur;

  public List<String> wlang;

  public List<String> bapp;

  public Regs regs;

  private Map<String, Object> ext;

  private transient Integer gdpr;

  private transient Integer gdprconsent;

  private transient String gdprstring;

  private transient String googleConsents;

  public BidRequest(){
    setReqExt(new BidReqExt());
  }

  public BidRequest(BidRequest bidRequest) {
    this.id = bidRequest.id;
    this.imp = null;
    this.site = bidRequest.site;
    this.app = bidRequest.app;
    this.device = bidRequest.device;
    this.user = bidRequest.user;
    this.badv = bidRequest.badv;
    this.at = bidRequest.at;
    this.test = bidRequest.test;
    this.wseat = bidRequest.wseat;
    this.tmax = bidRequest.tmax;
    this.bcat = bidRequest.bcat;
    this.allimps = bidRequest.allimps;
    this.cur = bidRequest.cur;
    this.bapp = bidRequest.bapp;
    this.regs = bidRequest.regs;
    this.ext = bidRequest.ext;
    setReqExt(bidRequest.getReqExt());
  }

  @EqualsAndHashCode(callSuper = true)
  @Data
  @NoArgsConstructor
  public static class BidReqExt extends ReqExt {
    private String prid;
    private String vid;
    private String sid;
    private int sd;
    private String version;
    private String kwrf;
    private int rt;
    private int as;
    private String callback;
    private String itype;
    private Integer ptype;
    private Integer is_ebda;
    private String ugd;
    private String taginfo;
  }
}
