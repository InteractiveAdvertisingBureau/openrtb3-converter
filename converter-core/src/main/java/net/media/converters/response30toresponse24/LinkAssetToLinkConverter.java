package net.media.converters.response30toresponse24;

import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.nativeresponse.AssetResponse;
import net.media.openrtb24.response.nativeresponse.Link;
import net.media.openrtb3.LinkAsset;
import net.media.utils.Utils;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class LinkAssetToLinkConverter implements Converter<LinkAsset,Link> {

  public Link map(LinkAsset source, Config config) throws OpenRtbConverterException {
    if(isNull(source) || isNull(config))
      return  null;
    Link link = new Link();
    inhance(source,link,config);
    return link;
  }

  public void inhance(LinkAsset source, Link target, Config config) throws OpenRtbConverterException {
    if(isNull(source) || isNull(target) || isNull(config))
      return;

    target.setUrl(source.getUrl());
    target.setFallback(source.getUrlfb());
    target.setClicktrackers(source.getTrkr());
    target.setExt(Utils.copyMap(source.getExt(),config));

  }
}
