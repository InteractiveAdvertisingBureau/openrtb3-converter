package net.media.openrtb3;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Created by rajat.go on 14/12/18.
 */
public class DataAsset {
    @NotNull
    private List<String> value = null;
    private Integer len;
    private Integer type;
    private String ext;
}
