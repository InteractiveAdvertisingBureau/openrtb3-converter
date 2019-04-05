package net.media;

import net.media.config.Config;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Created by rajat.go on 09/01/19.
 */

@Getter
@Setter
public class TestPojo {

  private String inputType;

  private String outputType;

  private Object inputJson;

  private Object outputJson;

  private Config config;

  private String purpose;

  private Map<String, String> outputEdits;

  private Config params;
}
