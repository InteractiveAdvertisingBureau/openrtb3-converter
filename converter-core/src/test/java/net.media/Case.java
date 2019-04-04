package net.media;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Case {
  private String inputFile;
  private String inputType;
  private Map<String, String> inputEdits;
  private JsonNode inputJson;
  private String outputFile;
  private String outputType;
  private Map<String, String> outputEdits;
  private JsonNode outputJson;
  private String purpose;
  private Map<String, String> params;
  private String status;

  public Case() {

  }
}
