package net.media.template;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.media.openrtb25.response.nativeresponse.NativeResponse;
import net.media.openrtb25.response.nativeresponse.NativeResponseBody;
import net.media.openrtb3.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class MacroMapper {
  public static BiMap<String,String> macrosTwoXToThreeX;

  static {
    macrosTwoXToThreeX = HashBiMap.create();
    macrosTwoXToThreeX.put("${AUCTION_ID}","${OPENRTB_ID}");
    macrosTwoXToThreeX.put("${AUCTION_BID_ID}","${OPENRTB_BID_ID}");
    macrosTwoXToThreeX.put("${AUCTION_IMP_ID}","${OPENRTB_ITEM_ID}");
    macrosTwoXToThreeX.put("${AUCTION_SEAT_ID}","${OPENRTB_SEAT_ID}");
    macrosTwoXToThreeX.put("${AUCTION_AD_ID}","${OPENRTB_MEDIA_ID}");
    macrosTwoXToThreeX.put("${AUCTION_PRICE}","${OPENRTB_PRICE}");
    macrosTwoXToThreeX.put("${AUCTION_CURRENCY}","${OPENRTB_CURRENCY}");
    macrosTwoXToThreeX.put("${AUCTION_MBR}","${OPENRTB_MBR}");
    macrosTwoXToThreeX.put("${AUCTION_LOSS}","${OPENRTB_LOSS}");
  }

  public static String getThreeXMacro(String macro){
    return macrosTwoXToThreeX.get(macro);
  }

  public static String getTwoXMacro(String macro){
    return macrosTwoXToThreeX.inverse().get(macro);
  }

  public static String macroBuilder(String macroName){
    return "${"+macroName+"}";
  }

  public static void macroReplaceThreeX(Bid bid){
    bid.setBurl(MacroMapper.macroReplaceThreeX(bid.getBurl()));
    bid.setLurl(MacroMapper.macroReplaceThreeX(bid.getLurl()));
    bid.setPurl(MacroMapper.macroReplaceThreeX(bid.getPurl()));

    if(nonNull(bid.getMedia()) && nonNull(bid.getMedia().getAd())) {
      MacroMapper.macroReplaceThreeX(bid.getMedia().getAd().getDisplay());
      MacroMapper.macroReplaceThreeX(bid.getMedia().getAd().getAudio());
      MacroMapper.macroReplaceThreeX(bid.getMedia().getAd().getVideo());
    }
  }

  public static void macroReplaceTwoX(net.media.openrtb25.response.Bid bid){
    bid.setBurl(MacroMapper.macroReplaceTwoX(bid.getBurl()));
    bid.setLurl(MacroMapper.macroReplaceTwoX(bid.getLurl()));
    bid.setNurl(MacroMapper.macroReplaceTwoX(bid.getNurl()));
    if(bid.getAdm() instanceof NativeResponse)
      MacroMapper.macroReplaceTwoX((NativeResponse) bid.getAdm());
    else if(bid.getAdm() instanceof String)
      MacroMapper.macroReplaceTwoX((String) bid.getAdm());

  }

  public static void macroReplaceThreeX(Display display){
    if(isNull(display))
      return ;
    if(display.getAdm() instanceof Banner)
      MacroMapper.macroReplaceThreeX((Banner) display.getAdm());
    else if(display.getAdm() instanceof Native)
      MacroMapper.macroReplaceThreeX((Native) display.getAdm());
    else if(display.getAdm() instanceof String)
      MacroMapper.macroReplaceThreeX((String) display.getAdm());

    MacroMapper.macroReplaceThreeX(display.getBanner());
    MacroMapper.macroReplaceThreeX(display.get_native());
    MacroMapper.macroReplaceThreeX(display.getCurl());
  }

  public static void macroReplaceThreeX(Banner banner){
    if(isNull(banner))
      return;
    MacroProcessor.getOpenRtbMacroProcessor(banner.getImg()).replace(MacroProcessor.getThreeXToken());
    if(nonNull(banner.getLink())) {
      MacroProcessor.getOpenRtbMacroProcessor(banner.getLink().getUrl()).replace(MacroProcessor.getThreeXToken());
      MacroProcessor.getOpenRtbMacroProcessor(banner.getLink().getUrlfb()).replace(MacroProcessor.getThreeXToken());
    }
  }

  public static void macroReplaceThreeX(Native _native){
    if(isNull(_native))
      return;
    if(nonNull(_native.getLink())) {
      MacroProcessor.getOpenRtbMacroProcessor(_native.getLink().getUrl()).replace(MacroProcessor.getThreeXToken());
      MacroProcessor.getOpenRtbMacroProcessor(_native.getLink().getUrlfb()).replace(MacroProcessor.getThreeXToken());
    }

    _native.getAsset().forEach(asset -> {
      if(nonNull(asset.getLink())) {
        MacroProcessor.getOpenRtbMacroProcessor(asset.getLink().getUrl()).replace(MacroProcessor.getThreeXToken());
        MacroProcessor.getOpenRtbMacroProcessor(asset.getLink().getUrlfb()).replace(MacroProcessor.getThreeXToken());
      }
    });
  }

  public static void macroReplaceThreeX(Video video){
    if(isNull(video))
      return;
    MacroProcessor.getOpenRtbMacroProcessor((String)video.getAdm()).replace(MacroProcessor.getThreeXToken());
    MacroProcessor.getOpenRtbMacroProcessor(video.getCurl()).replace(MacroProcessor.getThreeXToken());

  }

  public static void macroReplaceThreeX(Audio audio){
    if(isNull(audio))
      return;
    MacroProcessor.getOpenRtbMacroProcessor((String)audio.getAdm()).replace(MacroProcessor.getThreeXToken());
    MacroProcessor.getOpenRtbMacroProcessor(audio.getCurl()).replace(MacroProcessor.getThreeXToken());

  }

  public static String macroReplaceTwoX(String text){
    return MacroProcessor.getOpenRtbMacroProcessor(text).replace(MacroProcessor.getTwoXToken());
  }

  public static String macroReplaceThreeX(String text){
    return MacroProcessor.getOpenRtbMacroProcessor(text).replace(MacroProcessor.getThreeXToken());
  }

  public static void macroReplaceTwoX(NativeResponse  nativeResponse){
    if(isNull(nativeResponse))
      return;
    NativeResponseBody nativeResponseBody =  nativeResponse.getNativeResponseBody();
    nativeResponseBody.getAssets().forEach(assetResponse -> {
      MacroProcessor.getOpenRtbMacroProcessor(assetResponse.getLink().getUrl()).replace(MacroProcessor.getThreeXToken());
      MacroProcessor.getOpenRtbMacroProcessor(assetResponse.getLink().getFallback()).replace(MacroProcessor.getThreeXToken());
    });
  }

  public static String macroReplaceTemplate(String template, Banner banner){
    return MacroProcessor.getOpenRtbMacroProcessor(template).replace(MacroProcessor.getBannerFields(banner));
  }


}
