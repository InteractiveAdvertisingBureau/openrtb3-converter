package net.media.converters.response24toresponse30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.response.nativeresponse.Link;
import net.media.openrtb3.LinkAsset;

/**
 * @author shiva.b
 */
public class LinkToLinkAssetConverter implements Converter<Link, LinkAsset> {

  @Override
  public LinkAsset map(Link source, Config config) {
    if (source == null) {
      return null;
    }
    LinkAsset linkAsset = new LinkAsset();
    inhance(source, linkAsset, config);
    return linkAsset;
  }

  @Override
  public void inhance(Link source, LinkAsset target, Config config) {
    if (source == null || target == null) {
      return;
    }
    target.setUrl(source.getUrl());
    target.setUrlfb(source.getFallback());
    target.setTrkr(source.getClicktrackers());
    target.setExt(source.getExt());
  }
}
