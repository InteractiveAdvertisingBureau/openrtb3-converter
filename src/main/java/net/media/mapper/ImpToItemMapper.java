package net.media.mapper;

import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.request.Imp;
import net.media.openrtb3.Deal;
import net.media.openrtb3.Item;

import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 25/12/18.
 */

@Mapper(uses = {DisplayConverter.class, VideoConverter.class,
  AudioConverter.class})
public interface ImpToItemMapper {

  @Mappings({
    @Mapping(source = "imp.id", target = "id"),
    @Mapping(source = "imp.bidfloor", target = "flr"),
    @Mapping(source = "imp.bidfloorcur", target = "flrcur"),
    @Mapping(source = "imp.pmp.private_auction", target = "priv"),
    @Mapping(source = "imp.pmp.deals", target = "deal"),
    @Mapping(source = "imp.tagId", target = "spec.placement.tagid"),
    @Mapping(source = "imp.displaymanager", target = "spec.placement.sdk"),
    @Mapping(source = "imp.displaymanagerver", target = "spec.placement.sdkver"),
    @Mapping(source = "imp.banner", target = "spec.placement.display"),
    @Mapping(source = "imp.ext", target = "ext")
  })
  Item impToItem(Imp imp);

  @AfterMapping
  default void impToItemAfterMapping(Imp imp, @MappingTarget Item item) {
    if (nonNull(imp.getExt()) && !imp.getExt().isEmpty() && nonNull(item.getSpec()) && nonNull
      (item.getSpec().getPlacement())) {
      item.getSpec().getPlacement().setSsai((Integer) imp.getExt().get("ssai"));
      item.getExt().remove("ssai");
      item.getSpec().getPlacement().setReward((Integer) imp.getExt().get("reward"));
      item.getExt().remove("reward");
      item.getSpec().getPlacement().setAdmx((Integer) imp.getExt().get("admx"));
      item.getExt().remove("admx");
      item.getSpec().getPlacement().setCurlx((Integer) imp.getExt().get("curlx"));
      item.getExt().remove("curlx");
    }
  }

  @Mappings({
    @Mapping(source = "imp.id", target = "id"),
    @Mapping(source = "imp.bidfloor", target = "flr"),
    @Mapping(source = "imp.bidfloorcur", target = "flrcur"),
    @Mapping(source = "imp.pmp.private_auction", target = "priv"),
    @Mapping(source = "imp.pmp.deals", target = "deal"),
    @Mapping(source = "imp.video.sequence", target = "seq"),
    @Mapping(source = "imp.tagId", target = "spec.placement.tagid"),
    @Mapping(source = "imp.displaymanager", target = "spec.placement.sdk"),
    @Mapping(source = "imp.displaymanagerver", target = "spec.placement.sdkver"),
    @Mapping(source = "bidRequest.wlang", target = "spec.placement.wlang"),
    @Mapping(source = "imp.banner", target = "spec.placement.display"),
    @Mapping(source = "imp.video", target = "spec.placement.video"),
    @Mapping(source = "imp.audio", target = "spec.placement.audio"),
    @Mapping(source = "imp.ext", target = "ext")
  })
  Item impToItem(Imp imp, BidRequest bidRequest);

  @AfterMapping
  default void impToItemAfterMapping(Imp imp, BidRequest bidRequest, @MappingTarget Item item) {
    if (nonNull(imp) && nonNull(imp.getExt()) && !imp.getExt().isEmpty()) {
      if (nonNull(item)) {
        item.setQty((Integer) imp.getExt().get("qty"));
        item.getExt().remove("qty");
        item.setDt((Integer) imp.getExt().get("dt"));
        item.getExt().remove("dt");
        item.setDlvy((Integer) imp.getExt().get("dlvy"));
        item.getExt().remove("dlvy");
      }
      if (nonNull(item) && nonNull(item.getSpec()) && nonNull(item.getSpec().getPlacement())) {
        item.getSpec().getPlacement().setSsai((Integer) imp.getExt().get("ssai"));
        item.getExt().remove("ssai");
        item.getSpec().getPlacement().setReward((Integer) imp.getExt().get("reward"));
        item.getExt().remove("reward");
        item.getSpec().getPlacement().setAdmx((Integer) imp.getExt().get("admx"));
        item.getExt().remove("admx");
        item.getSpec().getPlacement().setCurlx((Integer) imp.getExt().get("curlx"));
        item.getExt().remove("curlx");
      }
    }
  }

  @InheritInverseConfiguration
  @Mappings({})
  Imp itemToImp(Item item);

  default void itemToImpAfterMapping(Item item, @MappingTarget Imp imp) {
    if (nonNull(item)) {
      if (nonNull(imp)) {
        if (isNull(imp.getExt())) {
          imp.setExt(new HashMap<>());
        }
        imp.getExt().put("qty", item.getQty());
        imp.getExt().put("dt", item.getDt());
        imp.getExt().put("dlvy", item.getDlvy());
      }
    }
    if (nonNull(item) && nonNull(item.getSpec()) && nonNull(item.getSpec().getPlacement())) {
      if (nonNull(imp)) {
        if (isNull(imp.getExt())) {
          imp.setExt(new HashMap<>());
        }
        imp.getExt().put("ssai", item.getSpec().getPlacement().getSsai());
        imp.getExt().put("reward", item.getSpec().getPlacement().getReward());
        imp.getExt().put("admx", item.getSpec().getPlacement().getAdmx());
        imp.getExt().put("curlx", item.getSpec().getPlacement().getCurlx());
      }
    }
  }

  @Mappings({
    @Mapping(source = "deal.bidFloor", target = "flr"),
    @Mapping(source = "deal.bidFloorCur", target = "flrcur")
  })
  Deal map(net.media.openrtb24.request.Deal deal);

  @InheritInverseConfiguration
  net.media.openrtb24.request.Deal map(Deal deal);
}
