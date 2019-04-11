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

package net.media.converters.response30toresponse25;

import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.response.Bid;
import net.media.openrtb3.Audit;
import net.media.utils.Provider;

import java.util.HashMap;

import static java.util.Objects.isNull;

public class AuditToBidConverter implements Converter<Audit,Bid> {


  public Bid map(Audit source, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if(isNull(source) || isNull(config))
      return  null;
    Bid  bid = new Bid();
    enhance(source,bid,config, converterProvider);
    return bid;
  }

  public  void enhance(Audit source, Bid target, Config config, Provider converterProvider) throws OpenRtbConverterException {
    if(isNull(source) || isNull(target) || isNull(config))
      return ;
    if(isNull(target.getExt())){
      target.setExt(new HashMap<>());
    }
    target.getExt().put("status",source.getStatus());
    target.getExt().put("feedback",source.getFeedback());
    Audit audit = new Audit();
    audit.setInit(source.getInit());
    target.getExt().put("audit", audit);
    target.getExt().put("lastmod",source.getLastmod());
    target.getExt().put("corr",source.getCorr());
    target.getExt().putAll(source.getExt());
  }
}
