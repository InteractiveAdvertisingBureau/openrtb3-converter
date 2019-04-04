package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.request.Imp;
import net.media.openrtb3.Restrictions;
import net.media.utils.Utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static net.media.utils.CommonConstants.DEFAULT_CATTAX_TWODOTX;

public class BidRequestToRestrictionsConverter implements Converter<BidRequest, Restrictions> {
  @Override
  public Restrictions map(BidRequest source, Config config) throws OpenRtbConverterException{
    if ( source == null ) {
      return null;
    }

    Restrictions restrictions = new Restrictions();

    enhance( source, restrictions, config );

    return restrictions;
  }

  @Override
  public void enhance(BidRequest source, Restrictions target, Config config) throws OpenRtbConverterException{
    if(source == null)
      return;
    target.setBapp( Utils.copyCollection(source.getBapp(), config) );
    target.setBcat( Utils.copyCollection(source.getBcat(), config) );
    target.setBadv( Utils.copyCollection(source.getBadv(), config) );
    if(source == null)
      return;
    if(source.getImp() == null)
      return;
    if(source.getImp().size() == 0)
      return;
    Collection<Integer> battr = new HashSet<>();
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
      target.setBattr(Utils.copyCollection(battr, config));
    }
    if(source.getExt() == null)
      return;
    try {
      if (source.getExt().containsKey("cattax")) {
        target.setCattax((Integer) source.getExt().get("cattax"));
        source.getExt().remove("cattax");
      } else {
        target.setCattax(DEFAULT_CATTAX_TWODOTX);
      }
      if (source.getExt().containsKey("restrictions")) {
        Restrictions restrictions = (Restrictions) source.getExt().get("restrictions");
        if (restrictions.getExt() != null) {
          target.setExt(restrictions.getExt());
        }
        source.getExt().remove("restrictions");
      }
    } catch (ClassCastException e) {
      throw new OpenRtbConverterException("error while typecasting ext for BidRequest", e);
    }
  }
}
