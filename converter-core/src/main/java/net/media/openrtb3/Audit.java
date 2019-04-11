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

package net.media.openrtb3;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Audit {

  private Integer status;
  private Collection<String> feedback = null;
  private Integer init;
  private Integer lastmod;
  private Corr corr;
  private Map<String,Object> ext;

  public Audit() {
  }

  public Integer getStatus() {
    return this.status;
  }

  public Collection<String> getFeedback() {
    return this.feedback;
  }

  public Integer getInit() {
    return this.init;
  }

  public Integer getLastmod() {
    return this.lastmod;
  }

  public Corr getCorr() {
    return this.corr;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public void setFeedback(Collection<String> feedback) {
    this.feedback = feedback;
  }

  public void setInit(Integer init) {
    this.init = init;
  }

  public void setLastmod(Integer lastmod) {
    this.lastmod = lastmod;
  }

  public void setCorr(Corr corr) {
    this.corr = corr;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Audit)) return false;
    final Audit other = (Audit) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$status = this.getStatus();
    final Object other$status = other.getStatus();
    if (this$status == null ? other$status != null : !this$status.equals(other$status))
      return false;
    final Object this$feedback = this.getFeedback();
    final Object other$feedback = other.getFeedback();
    if (this$feedback == null ? other$feedback != null : !this$feedback.equals(other$feedback))
      return false;
    final Object this$init = this.getInit();
    final Object other$init = other.getInit();
    if (this$init == null ? other$init != null : !this$init.equals(other$init)) return false;
    final Object this$lastmod = this.getLastmod();
    final Object other$lastmod = other.getLastmod();
    if (this$lastmod == null ? other$lastmod != null : !this$lastmod.equals(other$lastmod))
      return false;
    final Object this$corr = this.getCorr();
    final Object other$corr = other.getCorr();
    if (this$corr == null ? other$corr != null : !this$corr.equals(other$corr)) return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    if (this$ext == null ? other$ext != null : !this$ext.equals(other$ext)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $status = this.getStatus();
    result = result * PRIME + ($status == null ? 43 : $status.hashCode());
    final Object $feedback = this.getFeedback();
    result = result * PRIME + ($feedback == null ? 43 : $feedback.hashCode());
    final Object $init = this.getInit();
    result = result * PRIME + ($init == null ? 43 : $init.hashCode());
    final Object $lastmod = this.getLastmod();
    result = result * PRIME + ($lastmod == null ? 43 : $lastmod.hashCode());
    final Object $corr = this.getCorr();
    result = result * PRIME + ($corr == null ? 43 : $corr.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Audit;
  }

  public String toString() {
    return "net.media.openrtb3.Audit(status=" + this.getStatus() + ", feedback=" + this.getFeedback() + ", init=" + this.getInit() + ", lastmod=" + this.getLastmod() + ", corr=" + this.getCorr() + ", ext=" + this.getExt() + ")";
  }
}