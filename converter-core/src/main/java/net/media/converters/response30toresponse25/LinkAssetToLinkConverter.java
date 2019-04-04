package net.media.converters.response30toresponse25;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.nativeresponse.Link;
import net.media.openrtb3.LinkAsset;
import net.media.utils.Provider;
import net.media.utils.Utils;

import static java.util.Objects.isNull;

public class LinkAssetToLinkConverter implements Converter<LinkAsset,Link> {

  public Link map(LinkAsset source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if(isNull(source) || isNull(config))
      return  null;
    Link link = new Link();
    enhance(source,link,config, converterProvider);
    return link;
  }

  public void enhance(LinkAsset source, Link target, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if(isNull(source) || isNull(target) || isNull(config))
      return;

    target.setUrl(source.getUrl());
    target.setFallback(source.getUrlfb());
    target.setClicktrackers(source.getTrkr());
    target.setExt(Utils.copyMap(source.getExt(),config));

  }
}
