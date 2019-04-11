/*
 * Copyright © 2019 - present. MEDIA NET SOFTWARE SERVICES PVT. LTD.
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

import net.media.config.Config;

import java.util.Map;

/**
 * Created by rajat.go on 09/01/19.
 */

public class TestPojo {

  private String inputType;

  private String outputType;

  private Object inputJson;

  private Object outputJson;

  private Config config;

  private String purpose;

  private Map<String, String> outputEdits;

  private Config params;

  public String getInputType() {
    return this.inputType;
  }

  public String getOutputType() {
    return this.outputType;
  }

  public Object getInputJson() {
    return this.inputJson;
  }

  public Object getOutputJson() {
    return this.outputJson;
  }

  public Config getConfig() {
    return this.config;
  }

  public String getPurpose() {
    return this.purpose;
  }

  public Map<String, String> getOutputEdits() {
    return this.outputEdits;
  }

  public Config getParams() {
    return this.params;
  }

  public void setInputType(String inputType) {
    this.inputType = inputType;
  }

  public void setOutputType(String outputType) {
    this.outputType = outputType;
  }

  public void setInputJson(Object inputJson) {
    this.inputJson = inputJson;
  }

  public void setOutputJson(Object outputJson) {
    this.outputJson = outputJson;
  }

  public void setConfig(Config config) {
    this.config = config;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public void setOutputEdits(Map<String, String> outputEdits) {
    this.outputEdits = outputEdits;
  }

  public void setParams(Config params) {
    this.params = params;
  }
}
