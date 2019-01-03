package net.media.openrtb3;

import lombok.Data;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

/**
 * Created by rajat.go on 14/12/18.
 */
@Data
public class DataAsset {
    @NotNull
    private List<String> value = null;
    private Integer len;
    private Integer type;
    private Map<String,Object> ext;
}
