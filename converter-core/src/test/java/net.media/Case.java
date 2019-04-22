/*
 * Copyright  2019 - present. MEDIA.NET ADVERTISING FZ-LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.media;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

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
  private Map<String, Object> params;
  private Map<String, Object> config;
  private Map<String,Object> overRidingMap;


  public Case() {}

  public Map<String, Object> getConfig() {
    return config;
  }

  public void setConfig(Map<String, Object> config) {
    this.config = config;
  }

  public String getInputFile() {
    return this.inputFile;
  }

  public void setInputFile(String inputFile) {
    this.inputFile = inputFile;
  }

  public String getInputType() {
    return this.inputType;
  }

  public void setInputType(String inputType) {
    this.inputType = inputType;
  }

  public Map<String, String> getInputEdits() {
    return this.inputEdits;
  }

  public void setInputEdits(Map<String, String> inputEdits) {
    this.inputEdits = inputEdits;
  }

  public JsonNode getInputJson() {
    return this.inputJson;
  }

  public void setInputJson(JsonNode inputJson) {
    this.inputJson = inputJson;
  }

  public String getOutputFile() {
    return this.outputFile;
  }

  public void setOutputFile(String outputFile) {
    this.outputFile = outputFile;
  }

  public String getOutputType() {
    return this.outputType;
  }

  public void setOutputType(String outputType) {
    this.outputType = outputType;
  }

  public Map<String, String> getOutputEdits() {
    return this.outputEdits;
  }

  public void setOutputEdits(Map<String, String> outputEdits) {
    this.outputEdits = outputEdits;
  }

  public JsonNode getOutputJson() {
    return this.outputJson;
  }

  public void setOutputJson(JsonNode outputJson) {
    this.outputJson = outputJson;
  }

  public String getPurpose() {
    return this.purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public Map<String, Object> getParams() {
    return this.params;
  }

  public void setParams(Map<String, Object> params) {
    this.params = params;
  }

  public Map<String, Object> getOverRidingMap() {
    return overRidingMap;
  }

  public void setOverRidingMap(Map<String, Object> overRidingMap) {
    this.overRidingMap = overRidingMap;
  }
}
