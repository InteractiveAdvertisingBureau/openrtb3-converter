package net.media.openrtb24.request;


import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class BidRequest {

  //BidRequest parameters
  public static final Integer DEFAULT_TEST_VALUE = 0;
  public static final Integer DEFAULT_ALL_IMPS = 0;
  public static final Integer DEFAULT_AT = 2;

  @NotEmpty
  public String id;

  @NotEmpty
  @Valid
  public List<Imp> imp;

  @NotNull
  @Valid
  public Site site;

  @NotNull
  @Valid
  public App app;

  public Device device;

  @Valid
  public User user;

  public Set<String> badv;

  public Integer at = 2;

  public Integer test = DEFAULT_TEST_VALUE;

  public Set<String> wseat;

  public Set<String> bseat;

  public Integer tmax;

  @NotNull
  @Valid
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
  }

}
