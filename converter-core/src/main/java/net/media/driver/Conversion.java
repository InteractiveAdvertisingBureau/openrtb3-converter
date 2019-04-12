/*
 * Copyright © 2019 - present. MEDIA.NET ADVERTISING FZ-LLC
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

package net.media.driver;

/** @author shiva.b */
public class Conversion<U, V> {

  private Class<U> source;

  private Class<V> target;

  @java.beans.ConstructorProperties({"source", "target"})
  public Conversion(Class<U> source, Class<V> target) {
    this.source = source;
    this.target = target;
  }

  public Class getSource() {
    return this.source;
  }

  public void setSource(Class<U> source) {
    this.source = source;
  }

  public Class getTarget() {
    return this.target;
  }

  public void setTarget(Class<V> target) {
    this.target = target;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Conversion)) return false;
    final Conversion other = (Conversion) o;
    if (!other.canEqual(this)) return false;
    final Object this$source = this.getSource();
    final Object other$source = other.getSource();
    if (this$source == null ? other$source != null : !this$source.equals(other$source))
      return false;
    final Object this$target = this.getTarget();
    final Object other$target = other.getTarget();
    if (this$target == null ? other$target != null : !this$target.equals(other$target))
      return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $source = this.getSource();
    result = result * PRIME + ($source == null ? 43 : $source.hashCode());
    final Object $target = this.getTarget();
    result = result * PRIME + ($target == null ? 43 : $target.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Conversion;
  }

  public String toString() {
    return "net.media.driver.Conversion(source="
        + this.getSource()
        + ", target="
        + this.getTarget()
        + ")";
  }
}
