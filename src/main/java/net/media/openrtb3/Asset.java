package net.media.openrtb3;


@lombok.Data
public class Asset { //AssetResponse

  private Integer id;
  private Integer req;
  private TitleAsset titleAsset;
  private ImageAsset image;
  private VideoAsset videoAsset;
  private DataAsset data;
  private LinkAsset link;

}