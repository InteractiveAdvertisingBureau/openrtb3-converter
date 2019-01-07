package net.media.converters.request24toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb3.Restrictions;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BidRequestToRestrictionsConverter implements Converter<BidRequest, Restrictions> {
  @Override
  public Restrictions map(BidRequest source, Config config) {
    if ( source == null ) {
      return null;
    }

    Restrictions restrictions = new Restrictions();

    inhance( source, restrictions, config );

    return restrictions;
  }

  @Override
  public void inhance(BidRequest source, Restrictions target, Config config) {
    if(source == null)
      return;
    target.setBapp( source.getBapp() );
    target.setBcat( source.getBcat() );
    target.setBadv( source.getBadv() );
    if(source == null)
      return;
    if(source.getImp() == null)
      return;
    if(source.getImp().size() == 0)
      return;
    Set<Integer> battr = new HashSet<>();
    for(Imp imp : source.getImp()) {
      if(imp.getBanner() != null && imp.getBanner().getBattr() != null) {
        battr.addAll(imp.getBanner().getBattr());
      } else if(imp.getVideo() != null && imp.getVideo().getBattr() != null) {
        battr.addAll(imp.getVideo().getBattr());
      } else if(imp.getNat() != null && imp.getNat().getBattr() != null) {
        battr.addAll(imp.getNat().getBattr());
      }
    }
    if(battr.size()>0) {
      target.setBattr(battr);
    }
    if(source.getExt() == null)
      return;
    target.setCattax((Integer) source.getExt().get("cattax"));
    if(source.getExt().containsKey("restrictionsExt"))
      target.setExt((Map<String, Object>) source.getExt().get("restrictionsExt"));
  }
}
