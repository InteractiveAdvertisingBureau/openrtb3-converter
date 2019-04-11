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

/** Created by sourav.p on . */
public class OutputTestPojo {
  private String inputFile;

  private String inputType;

  private String outputType;

  private String status;

  private String exception;

  public void setInputFile(String file) {
    this.inputFile = file;
  }

  public void setInputType(String inputType) {
    this.inputType = inputType;
  }

  public void setOutputType(String outputType) {
    this.outputType = outputType;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setException(String exception) {
    this.exception = exception;
  }
}
