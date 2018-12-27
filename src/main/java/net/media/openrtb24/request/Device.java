package net.media.openrtb24.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class Device extends AbstractExtensible<Device.DeviceReqExt> {
  private String ua;

  private Geo geo;

  private Integer dnt;

  private Integer lmt;

  private String ip;

  private String ipv6;

  private String os;

  private Integer devicetype;

  private String make;

  private String model;

  private String osv;

  private String hwv;

  private Integer h;

  private Integer w;

  private Integer ppi;

  private Float pxratio;

  private Integer js;

  private Integer geofetch;

  private String flashver;

  private String language;

  private String carrier;

  private Integer connectiontype;

  private String ifa;

  private String didsha1;

  private String didmd5;

  private String dpidsha1;

  private String dpidmd5;

  private String macsha1;

  private String macmd5;

  private Map<String, Object> ext;

  public Device() {
    setReqExt(new DeviceReqExt());
  }

  public Device clone() {
    Device device=new Device();
    device.setOs(os);
    device.setUa(ua);
    device.setGeo(geo);
    device.setDnt(dnt);
    device.setLmt(lmt);
    device.setIp(ip);
    device.setIpv6(ipv6);
    device.setDevicetype(devicetype);
    device.setMake(make);
    device.setModel(model);
    device.setOsv(osv);
    device.setHwv(hwv);
    device.setH(h);
    device.setW(w);
    device.setPpi(ppi);
    device.setPxratio(pxratio);
    device.setJs(js);
    device.setGeofetch(geofetch);
    device.setFlashver(flashver);
    device.setLanguage(language);
    device.setCarrier(carrier);
    device.setConnectiontype(connectiontype);
    device.setIfa(ifa);
    device.setDidmd5(didmd5);
    device.setDpidmd5(dpidmd5);
    device.setDpidsha1(dpidsha1);
    device.setDidsha1(didsha1);
    device.setMacsha1(macsha1);
    device.setMacmd5(macmd5);
    device.setExt(ext);
    return  device;
  }

  public static class DeviceReqExt extends ReqExt {
  }
}
