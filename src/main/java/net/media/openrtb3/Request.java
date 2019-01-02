package net.media.openrtb3;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class Request {
  @NotEmpty
  private String id;
  private Integer test = 0;
  private Integer tmax;
  private Integer at = 2;
  private List<String> cur = new ArrayList<String>(){{add("USD");}};
  private Set<String> seat;
  private Integer wseat = 1;
  private String cdata;
  @NotNull
  @Valid
  private Source source;
  @NotNull
  @Valid
  private List<Item> item;
  @JsonProperty("package")
  private Integer pack;
  @NotNull
  @Valid
  private Context context;
  private Map<String, Object> ext;
}
