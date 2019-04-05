Introduction
============

Programmatic advertising has seen consistent growth over the last few years. However, due to varied requirements of different partners and the increasing complexity in the supply chain, it was felt that the underlying structure of OpenRTB was not scalable enough and needed to be changed.

OpenRTB 3.0 thus introduced a layered approach where the commerce transaction (e.g., auction parameters, deals, bids, etc.) was differentiated from the object specifications (e.g ads, placements, users, devices, sites, publishers, etc). The commerce transaction is supposed to be the focus of OpenRTB while the objects being transacted was added to AdCOM (Advertising Common Object Model).

This has two benefits:

-   The AdCOM layer can be reused in other specifications such as OpenDirect,
    AdManagementAPI and custom specifications
-   The Object layer can be revised independent of the OpenRTB spec.

However, structural changes also mean that OpenRTB 3.0 is not backward compatible. This becomes problematic when different partners in the supply chain are on different versions of OpenRTB.

This is the problem which this documentation tries to address by presenting a standard mapping between objects and fields across OpenRTB 3.0, OpenRTB 2.5, OpenRTB 2.4 and OpenRTB 2.3. This standard has been used in the converter library developed by media.net and which is being released as an open-source project.

Request Mappings: ORTB 3.0 and ORTB 2.5/ 2.4/ 2.3
==================================================

These mappings are used for converting requests between OpenRTB 3.0 and OpenRTB
2.5/2.4/2.3.

Object: OpenRTB
---------------

| **OpenRTB 3.0**                            | **OpenRTB 2.5/2.4/2.3** |
|--------------------------------------------|-------------------------|
| openrtb.ver                                | \-                      |
| openrtb.domainspec                         | \-                      |
| openrtb.domainver                          | \-                      |
| [openrtb.request](#object-openrtb-request) | \-                      |

Object: OpenRTB \> Request
--------------------------

*Object Tree – openrtb.request.\<parameter_name\>*

| **OpenRTB 3.0**                            | **OpenRTB 2.5/2.4/2.3**                                                                                   |
|--------------------------------------------|-----------------------------------------------------------------------------------------------------------|
| id                                         | bidRequest.id                                                                                             |
| test                                       | bidRequest.test                                                                                           |
| tmax                                       | bidRequest.tmax                                                                                           |
| at                                         | bidRequest.at                                                                                             |
| cur                                        | bidRequest.cur                                                                                            |
| package                                    | bidRequest.allimps                                                                                        |
| seat                                       | bidRequest.bseat (bidRequest.ext.bseat for ORTB 2.4 and 2.3) or bidRequest.wseat depending on wseat value |
| wseat                                      | NA (0 to activate bseat 1 to activate wseat)                                                              |
| cdata                                      | bidRequest.user.customdata                                                                                |
| ext                                        | bidRequest.ext                                                                                            |
| [source](#object-openrtb-request-source)   |                                                                                                           |
| [item](#object-openrtb-request-item)       |                                                                                                           |
| [context](#object-openrtb-request-context) |                                                                                                           |

### Object: OpenRTB \> Request \> Source

*Object Tree - openrtb.request.source.\<parameter_name\>*

| **OpenRTB 3.0**    | **OpenRTB 2.5/2.4/2.3**                                                          |
|--------------------|----------------------------------------------------------------------------------|
| tid                | bidRequest.source.tid *(bidRequest.ext.source.tid for ORTB v2.4 and v2.3)*       |
| ts                 | bidRequest.source.ext.ts                                                         |
| ds                 | bidRequest.source.ext.ds                                                         |
| dsmap              | bidRequest.source.ext.dsmap                                                      |
| cert               | bidRequest.source.ext.cert                                                       |
| digest             | bidRequest.source.ext.digest                                                     |
| pchain             | bidRequest.source.pchain *(bidRequest.ext.source.pchain for ORTB v2.4 and v2.3)* |
| [ext](#Source_Ext) | bidRequest.source.ext *(bidRequest.ext.source.ext for ORTB v2.4 and v2.3)*       |

#### Object: OpenRTB \> Request \> Source \> Ext

*Object Tree: openrtb.request.source.ext.\<parameter_name\>*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                                                  |
|-----------------|--------------------------------------------------------------------------|
| fd              | bidrequest.source.fd *(bidrequest.ext.source.fd for ORTB v2.4 and v2.3)* |

### Object: OpenRTB \> Request \> Item

*Object Tree - openrtb.request.item.\<parameter_name\>*

| **OpenRTB 3.0**                           | **OpenRTB 2.5/2.4/2.3**                                                                                                                 |
|-------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| id                                        | bidRequest.imp.id                                                                                                                       |
| qty                                       | bidRequest.imp.banner.ext.qty, bidRequest.imp.video.ext.qty, bidRequest.imp.audio.ext.qty, bidRequest.imp.native.request.native.plcmtcnt                                                             |
| seq                                       | bidRequest.imp.native.request.native.seq, bidRequest.imp.video.sequence, bidRequest.imp.audio.sequence, bidRequest.imp.banner.ext.seq   |
| flr                                       | bidRequest.imp.bidfloor                                                                                                                 |
| flrcur                                    | bidRequest.imp.bidfloorcur                                                                                                              |
| exp                                       | bidRequest.imp.exp *bidRequest.imp.ext.exp (for ORTB v2.3)*                                                                             |
| dt                                        | bidRequest.imp.ext.dt                                                                                                                   |
| dlvy                                      | bidrequest.imp.ext.dlvy                                                                                                                 |
| ext                                       | bidrequest.imp.ext                                                                                                                      |
| private                                   | bidRequest.imp.pmp.private_auction                                                                                                      |
| [metric](#_Object:_OpenRTB_>_4)           | bidRequest.imp.metric *bidRequest.imp.ext.metric (for ORTB v2.4 and v2.3)*                                                              |
| [deal](#object-openrtb-request-item-deal) |                                 -                                                                                                        |
| [spec](#_Object:_OpenRTB_>_6)             |                                                                                     -                                                  |

#### Object: OpenRTB \> Request \> Item \> Metric

*Object Tree - openrtb.request.item.metric.\<parameter_name\>*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                                                                  |
|-----------------|------------------------------------------------------------------------------------------|
| type            | bidRequest.imp.metric.type *(bidRequest.imp.ext.metric.type for ORTB v2.4 and v2.3)*     |
| value           | bidRequest.imp.metric.value *(bidRequest.imp.ext.metric.value for ORTB v2.4 and v2.3)*   |
| vendor          | bidRequest.imp.metric.vendor *(bidRequest.imp.ext.metric.vendor for ORTB v2.4 and v2.3)* |
| ext             | bidRequest.imp.metric.ext *(bidRequest.imp.ext.metric.ext for ORTB v2.4 and v2.3)*       |

#### Object: OpenRTB \> Request \> Item \> Deal

*Object Tree - openrtb.request.item.deal.\<parameter_name\>*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**             |
|-----------------|-------------------------------------|
| id              | bidRequest.imp.pmp.deal.id          |
| flr             | bidRequest.imp.pmp.deal.bidfloor    |
| flrcur          | bidRequest.imp.pmp.deal.bidfloorcur |
| at              | bidRequest.imp.pmp.deal.at          |
| wseat           | bidRequest.imp.pmp.deal.wseat       |
| wadomain        | bidRequest.imp.pmp.deal.wadomain    |
| ext             | bidRequest.imp.pmp.deal.ext         |

#### Object: OpenRTB \> Request \> Item \> Spec

*Object Tree - openrtb.request.item.spec.placement.\<parameter_name\>*

| **OpenRTB 3.0**                    | **OpenRTB 2.5/2.4/2.3**                                          |
|------------------------------------|------------------------------------------------------------------|
| tagid                              | bidRequest.imp.tagid                                             |
| ssai                               | bidRequest.imp.ext.ssai                                          |
| sdk                                | bidRequest.imp.displaymanager                                    |
| sdkver                             | bidRequest.imp.displaymanagerver                                 |
| reward                             | bidRequest.imp.ext.reward                                        |
| wlang                              | bidRequest.wlang *(bidRequest.ext.wlang for ORTB v2.4 and v2.3)* |
| secure                             | bidRequest.imp.secure                                            |
| admx                               | bidRequest.imp.ext.admx                                          |
| curlx                              | bidRequest.imp.ext.curlx                                         |
| **ext**                            |      -                                                            |
| [display](#Item_Spec_Plac_Display) |-                                                                  |
| [video](#Item_Spec_Plac_Video)     | -                                                                 |
| [audio](#Item_Spec_Plac_Audio)     |-                                                                  |

*Object: OpenRTB \> Request \> Item \> Spec \> Placement \> Display*

*Object Tree - openrtb.request.item.spec.placement.display.\<parameter_name\>*

| **OpenRTB 3.0**                        | **OpenRTB 2.5/2.4/2.3**                                                       |
|----------------------------------------|-------------------------------------------------------------------------------|
| pos                                    | bidRequest.imp.banner.pos                                                     |
| instl                                  | bidRequest.imp.instl                                                          |
| topframe                               | bidRequest.imp.banner.topframe                                                |
| ifrbust                                | bidRequest.imp.iframebuster                                                   |
| clktype                                | bidRequest.imp.clickbrowser *bidRequest.imp.ext.clickbrowser (for ORTB v2.3)* |
| ampren                                 | bidRequest.imp.ext.ampren                                                     |
| ptype                                  | bidRequest.imp.banner.ext.ptype/ bidRequest.native.request.native.plcmttype   |
| context                                | bidRequest.imp.banner.ext.context/ bidrequst.native.request.native.context    |
| mime                                   | bidRequest.imp.banner.mimes                                                   |
| api                                    | bidRequest.imp.[banner or native].api                                           |
| ctype                                  | bidRequest.imp.banner/native.ext.ctype                                        |
| w                                      | bidRequest.imp.banner.w                                                       |
| h                                      | bidRequest.imp.banner.h                                                       |
| unit                                   | bidRequest.imp.banner.ext.unit                                                |
| Priv                                   | bidRequest.imp.banner/native.ext.priv                                         |
| [displayfmt](#Item_Spec_Plac_Dis_DFMT) |                                                                               |
| [nativefmt](#Item_Spec_Plac_Dis_NFMT)  |                                                                               |
| [event](#Item_Spec_Plac_Dis_Event)     |                                                                               |
| [ext](#Item_Spec_Plac_Dis_Ext)         | bidRequest.imp.banner.ext, bidrequest.imp.native.ext                          |

*Object: OpenRTB \> Request \> Item \> Spec \> Placement \> Display \> DisplayFMT*

*Object Tree -
openrtb.request.item.spec.placement.display.displayfmt.\<parameter_name\>*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                                                                                                                                |
|-----------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| w               | bidRequest.imp.banner.format.w *(bidRequest.imp.banner.ext.format.w for ORTB v2.3)*                                                                    |
| h               | bidRequest.imp.banner.format.h *(bidRequest.imp.banner.ext.format.h for ORTB v2.3)*                                                                    |
| wratio          | bidRequest.imp.banner.format.wratio (bidRequest.imp.banner.format.ext.wratio *for ORTB v2.4*  bidRequest.imp.banner.ext.format.wratio *for ORTB v2.3)* |
| hratio          | bidRequest.imp.banner.format.hratio (bidRequest.imp.banner.format.ext.hratio *for ORTB v2.4*  bidRequest.imp.banner.ext.format.hratio *for ORTB v2.3)* |
| expdir          | bidRequest.imp.banner.expdir                                                                                                                           |
| ext             | bidRequest.imp.banner.format.ext *(bidRequest.imp.banner.ext.format.ext for ORTB v2.3)*                                                                |

*Object: OpenRTB \> Request \> Item \> Spec \> Placement \> Display \> NativeFMT*

*Object Tree -
openrtb.request.item.spec.placement.display.nativefmt.\<parameter_name\>*

| **OpenRTB 3.0**                         | **OpenRTB 2.5/2.4/2.3** |
|-----------------------------------------|-------------------------|
| [ext](#Item_Spec_Plac_Dis_NFMT_Ext)     |    -                     |
| [asset](#Item_Spec_Plac_Dis_NFMT_Asset) |   -                      |

*Object: OpenRTB \> Request \> Item \> Spec \> Placement \> Display \> NativeFMT \> Ext*

*Object Tree -
openrtb.request.item.spec.placement.display.nativefmt.ext.\<parameter_name\>*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                             |
|-----------------|-----------------------------------------------------|
| Adunit          | bidrequest.imp.native.request.native.adunit         |
| contextsubtype  | bidrequest.imp.native.request.native.contextsubtype |
| layout          | bidrequest.imp.native.request.native.layout         |
| ver             | bidrequest.imp.native.request.native.ver            |

*Object: OpenRTB \> Request \> Item \> Spec \> Placement \> Display \> NativeFMT \> Asset*

*Object Tree -
openrtb.request.item.spec.placement.display.nativefmt.asset.\<parameter_name\>*

| **OpenRTB 3.0**                               | **OpenRTB 2.5/2.4/2.3**                             |
|-----------------------------------------------|-----------------------------------------------------|
| id                                            | bidRequest.imp.native.request.native.asset.id       |
| req                                           | bidRequest.imp.native.request.native.asset.required |
| [title](#Item_Spec_Plac_Dis_NFMT_Asset_Title) | bidRequest.imp.native.request.native.asset.title    |
| [img](#Item_Spec_Plac_Dis_NFMT_Asset_Img)     | bidRequest.Imp.native.request.native.asset.img      |
| [video](#Item_Spec_Plac_Dis_NFMT_Asset_Video) |                                                     |
| [data](#Item_Spec_Plac_Dis_NFMT_Asset_Data)   | bidRequest.imp.native.request.native.asset.data     |
| ext                                           | \-                                                  |

*Object: OpenRTB \> Request \> Item \> Spec \> Placement \> Display \> NativeFMT \> Asset \> Title*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                              |
|-----------------|------------------------------------------------------|
| len             | bidRequest.imp.native.request.native.asset.title.len |
| ext             | bidRequest.imp.native.request.native.asset.title.ext |

*Object: OpenRTB \> Request \> Item \> Spec \> Placement \> Display \> NativeFMT \> Asset \> Img*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                                   |
|-----------------|-----------------------------------------------------------|
| type            | bidRequest.Imp.native.request.native.asset.img.type       |
| mime            | bidRequest.Imp.native.request.native.asset.img.mimes      |
| w               | bidRequest.Imp.native.request.native.asset.img.w          |
| h               | bidRequest.Imp.native.request.native.asset.img.h          |
| wmin            | bidRequest.Imp.native.request.native.asset.img.wmin       |
| hmin            | bidRequest.Imp.native.request.native.asset.img.hmin       |
| wratio          | bidRequest.Imp.native.request.native.asset.img.ext.wratio |
| hratio          | bidRequest.Imp.native.request.native.asset.img.ext.hratio |
| ext             | bidRequest.Imp.native.request.native.asset.img.ext        |

*Object: OpenRTB \> Request \> Item \> Spec \> Placement \> Display \> NativeFMT \> Asset \> Video*

| **OpenRTB 3.0**                                   | **OpenRTB 2.5/2.4/2.3**                                                |
|---------------------------------------------------|------------------------------------------------------------------------|
| ptype                                             | bidRequest.imp.native.request.native.asset.video.ext.ptype             |
| pos                                               | bidRequest.imp.native.request.native.asset.video.ext.pos               |
| delay                                             | bidRequest.imp.native.request.native.asset.video.ext.startdelay        |
| skip                                              | bidRequest.imp.native.request.native.asset.video.ext.skip              |
| skipmin                                           | bidRequest.imp.native.request.native.asset.video.ext.skipmin           |
| skipafter                                         | bidRequest.imp.native.request.native.asset.video.ext.skipafter         |
| playmethod                                        | bidRequest.imp.native.request.native.asset.video.ext.playbackmethod[0] |
| playend                                           | bidRequest.imp.native.request.native.asset.video.ext.playbackend       |
| clktype                                           | bidRequest.imp.native.request.native.asset.ext.clickbrowser            |
| mime                                              | bidRequest.imp.native.request.native.asset.video.mimes                 |
| api                                               | bidRequest.imp.native.request.native.asset.video.ext.api               |
| ctype                                             | bidRequest.imp.video.native.request.native.asset.protocols             |
| w                                                 | bidRequest.imp.native.request.native.asset.video.ext.w                 |
| h                                                 | bidRequest.imp.native.request.native.asset.video.ext.h                 |
| unit                                              | bidRequest.imp.native.request.native.asset.video.ext.unit              |
| mindur                                            | bidRequest.imp.native.request.native.asset.video.minduration           |
| maxdur                                            | bidRequest.imp.native.request.native.asset.video.maxduration           |
| maxext                                            | bidRequest.imp.native.request.native.asset.video.ext.maxextended       |
| minbitr                                           | bidRequest.imp.native.request.native.asset.video.ext.minbitrate        |
| maxbitr                                           | bidRequest.imp.native.request.native.asset.video.ext.maxbitrate        |
| delivery                                          | bidRequest.imp.native.request.native.asset.video.ext.delivery          |
| maxseq                                            | bidRequest.imp.native.request.native.asset.video.ext.ext.maxseq        |
| linear                                            | bidRequest.imp.native.request.native.asset.video.ext.linearity         |
| boxing                                            | bidRequest.imp.native.request.native.asset.video.ext.boxingallowed     |
| [comp](#Item_Spec_Plac_Dis_NFMT_Asset_Video_Comp) |                                                                        |
| ext                                               | bidRequest.imp.native.request.native.asset.video.ext                   |

*Object: OpenRTB \> Request \> Item \> Spec \> Placement \> Display \> NativeFMT \> Asset \> Video \> Comp*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                                                                |
|-----------------|----------------------------------------------------------------------------------------|
| id              | bidRequest.imp.native.request.native.asset.video.ext.banner.id                         |
| vcm             | bidRequest.imp.native.request.asset.video.ext.banner.vcm                               |
| display         | similar to display placement                                                           |
| ext             | bidRequest.imp.native.request.native.asset.video.ext.banner.ext (only forward mapping) |
| comptype        | bidRequest.imp.native.request.native.asset.video.ext.companiontype                     |

*Object: OpenRTB \> Request \> Item \> Spec \> Placement \> Display \> NativeFMT \> Asset \> Data*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                              |
|-----------------|------------------------------------------------------|
| type            | bidRequest.imp.native.request.native.asset.data.type |
| len             | bidRequest.imp.native.request.asset.native.data.len  |
| ext             | bidRequest.imp.native.request.asset.native.data.ext  |

*Object: OpenRTB \> Request \> Item \> Spec \> Placement \> Display \> Event*

*Object Tree -
openrtb.request.item.spec.placement.display.event.\<parameter_name\>*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**         |
|-----------------|---------------------------------|
| type            | bidRequest.imp.ext.event.type   |
| method          | bidRequest.imp.ext.event.method |
| api             | bidRequest.imp.ext.event.api    |
| jstrk           | bidRequest.imp.ext.event.jstrk  |
| wjs             | bidRequest.imp.ext.event.wjs    |
| pxtrk           | bidRequest.imp.ext.event.pxtrk  |
| wpx             | bidRequest.imp.ext.event.wpx    |
| ext             | bidRequest.imp.ext.event.ext    |

*Object: OpenRTB \> Request \> Item \> Spec \> Placement \> Display \> Ext*

*Object Tree -
openrtb.request.item.spec.placement.display.ext.\<parameter_name\>*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**     |
|-----------------|-----------------------------|
| btype           | bidRequest.imp.banner.btype |
| id              | bidRequest.imp.banner.id    |
| hmin            | bidRequest.imp.banner.hmin  |
| wmin            | bidRequest.imp.banner.wmin  |
| hmax            | bidRequest.imp.banner.hmax  |
| wmax            | bidRequest.imp.banner.wmax  |

*Object: OpenRTB \> Request \> Item \> Spec \> Placement \> Video*

*Object Tree - openrtb.request.item.spec.placement.video.\<parameter_name\>*

| **OpenRTB 3.0**                    | **OpenRTB 2.5/2.4/2.3**                                                                          |
|------------------------------------|--------------------------------------------------------------------------------------------------|
| ptype                              | bidRequest.imp.video.placement *(bidRequest.imp.video.ext.placement for ORTB v2.4 and v2.3)*     |
| pos                                | bidRequest.imp.video.pos                                                                         |
| delay                              | bidRequest.imp.video.startdelay                                                                  |
| skip                               | bidRequest.imp.video.skip *(bidRequest.imp.video.ext.skip for ORTB v2.3)*                        |
| skipmin                            | bidRequest.imp.video.skipmin *(bidRequest.imp.video.ext.skipmin for ORTB v2.3)*                  |
| skipafter                          | bidRequest.imp.video.skipafter *(bidRequest.imp.video.ext.skipafter for ORTB v2.3)*              |
| playmethod                         | bidRequest.imp.video.playbackmethod[0]                                                           |
| playend                            | bidRequest.imp.video.playbackend *(bidRequest.imp.video.ext.playbackend for ORTB v2.4 and v2.3)* |
| clktype                            | bidRequest.imp.clickbrowser *(bidRequest.imp.ext.clickbrowser for ORTB v2.3)*                    |
| mime                               | bidRequest.imp.video.mimes                                                                       |
| api                                | bidRequest.imp.video.api                                                                         |
| ctype                              | bidRequest.imp.video.protocols                                                                   |
| w                                  | bidRequest.imp.video.w                                                                           |
| h                                  | bidRequest.imp.video.h                                                                           |
| unit                               | bidRequest.imp.video.ext.unit. Units of size used for w and h attributes, default 1              |
| mindur                             | bidRequest.imp.video.minduration                                                                 |
| maxdur                             | bidRequest.imp.video.maxduration                                                                 |
| maxext                             | bidRequest.imp.video.maxextended                                                                 |
| minbitr                            | bidRequest.imp.video.minbitrate                                                                  |
| maxbitr                            | bidRequest.imp.video.maxbitrate                                                                  |
| delivery                           | bidRequest.imp.video.delivery                                                                    |
| maxseq                             | bidRequest.imp.video.ext.maxseq                                                                  |
| linear                             | bidRequest.imp.video.linearity                                                                   |
| boxing                             | bidRequest.imp.video.boxingallowed                                                               |
| [comp](#Item_Spec_Plac_Video_Comp) |   -                                                                                               |

*Object: OpenRTB \> Request \> Item \> Spec \> Placement \> Video \> Comp*

*Object Tree -
openrtb.request.item.spec.placement.video.comp.\<parameter_name\>*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                                |
|-----------------|--------------------------------------------------------|
| id              | bidRequest.imp.video.banner.id                         |
| vcm             | bidRequest.imp.video.banner.vcm                        |
| display         | similar to display placement                           |
| ext             | bidRequest.imp.video.banner.ext (only forward mapping) |
| comptype        | bidRequest.imp.video.companiontype                     |

*Object: OpenRTB \> Request \> Item \> Spec \> Placement \> Audio*

*Object Tree - openrtb.request.item.spec.placement.audio.\<parameter_name\>*

| **OpenRTB 3.0**                    | **OpenRTB 2.5/2.4/2.3**             |
|------------------------------------|-------------------------------------|
| delay                              | bidRequest.imp.audio.startdelay     |
| skip                               | bidRequest.imp.audio.ext.delay      |
| skipmin                            | bidRequest.imp.audio.ext.skipmin    |
| skipafter                          | bidRequest.imp.audio.ext.skipafter  |
| playmethod                         | bidRequest.imp.audio.ext.playmethod |
| playend                            | bidRequest.imp.audio.ext.playend    |
| feed                               | bidRequest.imp.audio.feed           |
| nvol                               | bidRequest.imp.audio.nvol           |
| mime                               | bidRequest.imp.audio.mimes          |
| api                                | bidRequest.imp.audio.api            |
| ctype                              | bidRequest.imp.audio.protocols      |
| mindur                             | bidRequest.imp.audio.minduration    |
| maxdur                             | bidRequest.imp.audio.maxduration    |
| maxext                             | bidRequest.imp.audio.maxextended    |
| minbitr                            | bidRequest.imp.audio.minbitrate     |
| maxbitr                            | bidRequest.imp.audio.maxbitrate     |
| delivery                           | bidRequest.imp.audio.delivery       |
| maxseq                             | bidRequest.imp.audio.maxseq         |
| comptype                           | bidRequest.imp.audio.companiontype  |
| ext                                | bidRequest.imp.audio.ext            |
| [comp](#Item_Spec_Plac_Audio_Comp) | -                                    |

*Object: OpenRTB \> Request \> Item \> Spec \> Placement \> Audio \> Comp*

Object Tree - openrtb.request.item.spec.placement.audio.comp.\<parameter_name\>

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                                |
|-----------------|--------------------------------------------------------|
| id              | bidRequest.imp.audio.banner.id                         |
| vcm             | bidRequest.imp.audio.banner.vcm                        |
| display         | Similar to displayplacement                            |
| ext             | bidRequest.imp.audio.banner.ext (only forward mapping) |

### Object: OpenRTB \> Request \> Context

*Object Tree - openrtb.request.context.\<parameter_name\>*

The Context object include the following sub objects:

-   [Site](#object-openrtb-request-context-site)
-   [User](#object-openrtb-request-context-user)
-   [Device](#object-openrtb-request-context-device)
-   [Regs](#object-openrtb-request-context-regs)
-   [Restrictions](#_Object:_OpenRTB_>_11)
-   [App](#object-openrtb-request-context-app)
-   [Dooh](#_Object:_OpenRTB_>_13)

#### Object: OpenRTB \> Request \> Context \> Site

*Object Tree - openrtb.request.context.site.\<parameter_name\>*

| **OpenRTB 3.0**                  | **OpenRTB 2.5/2.4/2.3**       |
|----------------------------------|-------------------------------|
| id                               | bidRequest.site.id            |
| name                             | bidRequest.site.name          |
| [pub](#Context_Site_Pub)         |                               |
| [content](#Context_Site_Content) |                               |
| domain                           | bidRequest.site.domain        |
| cat                              | bidRequest.site.cat           |
| sectcat                          | bidRequest.site.pagecat       |
| pagecat                          | bidRequest.site.pagecat       |
| cattax                           | bidRequest.site.ext.cattax    |
| privpolicy                       | bidRequest.site.privacypolicy |
| keywords                         | bidRequest.site.keywords      |
| page                             | bidRequest.site.page          |
| ref                              | bidRequest.site.ref           |
| search                           | bidRequest.site.search        |
| mobile                           | bidRequest.site.mobile        |
| amp                              | bidRequest.site.ext.amp       |
| ext                              | -                              |

*Object: OpenRTB \> Request \> Context \> Site \> Pub*

*Object Tree - openrtb.request.context.site.pub.\<parameter_name\>*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**              |
|-----------------|--------------------------------------|
| id              | bidRequest.site.publisher.id         |
| name            | bidRequest.site.publisher.name       |
| domain          | bidRequest.site.publisher.domain     |
| cat             | bidRequest.site.publisher.cat        |
| cattax          | bidRequest.site.publisher.ext.cattax |
| ext             | bidRequest.site.publisher.ext        |

*Object: OpenRTB \> Request \> Context \> Site \> Content*

*Object Tree - openrtb.request.context.site.content.\<parameter_name\>*

| **OpenRTB 3.0**                            | **OpenRTB 2.5/2.4/2.3**                                                             |
|--------------------------------------------|-------------------------------------------------------------------------------------|
| id                                         | bidRequest.site.content.id                                                          |
| episode                                    | bidRequest.site.content.episode                                                     |
| title                                      | bidRequest.site.content.title                                                       |
| series                                     | bidRequest.site.content.series                                                      |
| season                                     | bidRequest.site.content.season                                                      |
| artist                                     | bidRequest.site.content.artist *(bidRequest.site.content.ext.artist for ORTB v2.3)* |
| genre                                      | bidRequest.site.content.genre *(bidRequest.site.content.ext.genre for ORTB v2.3)*   |
| album                                      | bidRequest.site.content.album *(bidRequest.site.content.ext.album for ORTB v2.3)*   |
| isrc                                       | bidRequest.site.content.isrc *(bidRequest.site.content.ext.isrc for ORTB v2.3)*     |
| url                                        | bidRequest.site.content.url                                                         |
| cat                                        | bidRequest.site.content.cat                                                         |
| cattax                                     | bidRequest.site.content.ext.cattax                                                  |
| prodq                                      | bidRequest.site.content.prodq *(bidRequest.site.content.ext.prodq for ORTB v2.3)*   |
| context                                    | bidRequest.site.content.context                                                     |
| rating                                     | bidRequest.site.content.contentrating                                               |
| urating                                    | bidRequest.site.content.qagmediarating                                              |
| mrating                                    | bidRequest.site.content.userrating                                                  |
| keywords                                   | bidRequest.site.content.keywords                                                    |
| live                                       | bidRequest.site.content.livestream                                                  |
| srcrel                                     | bidRequest.site.content.sourcerelationship                                          |
| len                                        | bidRequest.site.content.len                                                         |
| lang                                       | bidRequest.site.content.language                                                    |
| Embed                                      | bidRequest.site.content.embeddable                                                  |
| [producer](#Context_Site_Content_Producer) |                                                                                     |
| [data](#Context_Site_Content_Data)         | bidrequest.site.content.data *(bidrequest.site.content.ext.data for ORTB v2.3)*     |
| [ext](#Context_Site_Content_Ext)           |     -                                                                                |

*Object: OpenRTB \> Request \> Context \> Site \> Content \> Producer*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                     |
|-----------------|---------------------------------------------|
| id              | bidRequest.site.content.producer.id         |
| name            | bidRequest.site.content.producer.name       |
| domain          | bidRequest.site.content.producer.domain     |
| cat             | bidRequest.site.content.producer.cat        |
| cattax          | bidRequest.site.content.producer.ext.cattax |
| ext             | bidRequest.site.content.producer.ext        |

*Object: OpenRTB \> Request \> Context \> Site \> Content \> Data*

| **OpenRTB 3.0**                           | **OpenRTB 2.5/2.4/2.3**              |
|-------------------------------------------|--------------------------------------|
| id                                        | bidRequest.site.content.data.id      |
| name                                      | bidRequest.site.content.data.name    |
| [segment](#Context_Site_Content_Data_Seg) | bidRequest.site.content.data.segment |
| ext                                       | bidRequest.site.content.data.ext     |

*Object: OpenRTB \> Request \> Context \> Site \> Content \> Data \> Segment*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                    |
|-----------------|--------------------------------------------|
| id              | bidRequest.site.content.data.segment.id    |
| name            | bidRequest.site.content.data.segment.name  |
| value           | bidRequest.site.content.data.segment.value |
| ext             | bidRequest.site.content.data.segment.ext   |

*Object: OpenRTB \> Request \> Context \> Site \> Content \> Ext*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**              |
|-----------------|--------------------------------------|
| videoquality    | bidRequest.site.content.videoquality |

#### Object: OpenRTB \> Request \> Context \> User

*Object Tree - openrtb.request.context.user.\<parameter_name\>*

| **OpenRTB 3.0**            | **OpenRTB 2.5/2.4/2.3**     |
|----------------------------|-----------------------------|
| id                         | bidRequest.user.id          |
| buyeruid                   | bidRequest.user.buyeruid    |
| yob                        | bidRequest.user.yob         |
| gender                     | bidRequest.user.gender      |
| keywords                   | bidRequest.user.keywords    |
| consent                    | bidRequest.user.ext.consent |
| geo                        | bidRequest.user.geo         |
| [data](#Context_User_Data) | bidRequest.user.data        |

*Object: OpenRTB \> Request \> Context \> User \> Data*

| **OpenRTB 3.0**                   | **OpenRTB 2.5/2.4/2.3**      |
|-----------------------------------|------------------------------|
| id                                | bidRequest.user.data.id      |
| name                              | bidRequest.user.data.name    |
| [segment](#Context_User_Data_Seg) | bidRequest.user.data.segment |
| ext                               | bidRequest.user.data.ext     |

*Object: OpenRTB \> Request \> Context \> User \> Data \> Segment*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**            |
|-----------------|------------------------------------|
| id              | bidRequest.user.data.segment.id    |
| name            | bidRequest.user.data.segment.name  |
| value           | bidRequest.user.data.segment.value |
| ext             | bidRequest.user.data.segment.ext   |

#### Object: OpenRTB \> Request \> Context \> Device

*Object Tree - openrtb.request.context.device.\<parameter_name\>*

| **OpenRTB 3.0**            | **OpenRTB 2.5/2.4/2.3**                                                          |
|----------------------------|----------------------------------------------------------------------------------|
| type                       | bidRequest.device.devicetype                                                     |
| ua                         | bidRequest.device.ua                                                             |
| ifa                        | bidRequest.device.ifa                                                            |
| dnt                        | bidRequest.device.dnt                                                            |
| lmt                        | bidRequest.device.lmt                                                            |
| make                       | bidRequest.device.make                                                           |
| model                      | bidRequest.device.model                                                          |
| os                         | bidRequest.device.os                                                             |
| osv                        | bidRequest.device.osv                                                            |
| hwv                        | bidRequest.device.hwv                                                            |
| h                          | bidRequest.device.h                                                              |
| w                          | bidRequest.device.w                                                              |
| ppi                        | bidRequest.device.ppi                                                            |
| pxratio                    | bidRequest.device.pxratio                                                        |
| js                         | bidRequest.device.js                                                             |
| lang                       | bidRequest.device.language                                                       |
| ip                         | bidRequest.device.ip                                                             |
| ipv6                       | bidRequest.device.ipv6                                                           |
| xff                        | bidRequest.device.ext.xff                                                        |
| iptr                       | bidRequest.device.ext.iptr                                                       |
| carrier                    | bidRequest.device.carrier                                                        |
| mccmnc                     | bidRequest.device.mccmnc *(bidRequest.device.ext.mccmnc for ORTB v2.4 and v2.3)* |
| mccmncsim                  | bidRequest.device.ext.mccmncsim                                                  |
| contype                    | bidRequest.device.connectiontype                                                 |
| geofetch                   | bidRequest.device.geofetch *(bidRequest.device.ext.geofetch for ORTB v2.3)*      |
| [geo](#Context_Device_Geo) | bidRequest.device.geo                                                            |
| ext                        |    -                                                                              |

*Object: OpenRTB \> Request \> Context \> Device \> Geo*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                                                           |
|-----------------|-----------------------------------------------------------------------------------|
| type            | bidRequest.user.geo.type                                                          |
| lat             | bidRequest.user.geo.lat                                                           |
| lon             | bidRequest.user.geo.lon                                                           |
| accur           | bidRequest.user.geo.accuracy *(bidRequest.user.geo.ext.accuracy for ORTB v2.3)*   |
| lastfix         | bidRequest.user.geo.lastfix *(bidRequest.user.geo.ext.lastfix for ORTB v2.3)*     |
| ipserv          | bidRequest.user.geo.ipservice *(bidRequest.user.geo.ext.ipservice for ORTB v2.3)* |
| country         | bidRequest.user.geo.country                                                       |
| region          | bidRequest.user.geo.region                                                        |
| metro           | bidRequest.user.geo.metro                                                         |
| city            | bidRequest.user.geo.city                                                          |
| zip             | bidRequest.user.geo.zip                                                           |
| utcoffset       | bidRequest.user.geo.utcoffset                                                     |
| ext             | bidRequest.user.geo.ext                                                           |

#### Object: OpenRTB \> Request \> Context \> Regs

*Object Tree - openrtb.request.context.regs.\<parameter_name\>*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**  |
|-----------------|--------------------------|
| coppa           | bidRequest.regs.coppa    |
| gdpr            | bidRequest.regs.ext.gdpr |
| ext             | bidRequest.regs.ext      |

#### Object: OpenRTB \> Request \> Context \> Restrictions

*Object Tree - openrtb.request.context.restrictions.\<parameter_name\>*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                                 |
|-----------------|---------------------------------------------------------|
| bcat            | bidrequest.bcat                                         |
| cattax          | bidRequest.ext.cattax                                   |
| badv            | bidrequest.badv                                         |
| bapp            | bidrequest.bapp *(bidRequest.ext.bapp for ORTB v2.3)*   |
| battr           | union of bidRequest.imp[\*].(video/banner/native).battr |
| ext             | bidRequest.ext                                          |

#### Object: OpenRTB \> Request \> Context \> App

*Object Tree - openrtb.request.context.app.\<parameter_name\>*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**           |
|-----------------|-----------------------------------|
| id              | bidRequest.app.id                 |
| name            | bidRequest.app.name               |
| pub             | bidRequest.app.publisher          |
| content         | bidRequest.app.content            |
| domain          | bidRequest.app.domain             |
| cat             | bidRequest.app.cat                |
| sectcat         | bidRequest.app.sectioncat         |
| pagecat         | bidRequest.app.pagecat            |
| cattax          | bidRequest.app.ext.cattax         |
| privpolicy      | bidRequest.app.privacypolicy      |
| keywords        | bidRequest.app.keywords           |
| bundle          | bidRequest.app.bundle             |
| storeid         | bidRequest.app.ext.cattax.storeid |
| storeurl        | bidRequest.app.storeurl           |
| ver             | bidRequest.app.ver                |
| paid            | bidRequest.app.paid               |
| ext             | bidRequest.app.ext                |

#### Object: OpenRTB \> Request \> Context \> Dooh

*Object Tree - openrtb.request.context.dooh.\<parameter_name\>*

| **OpenRTB 3.0**       | **OpenRTB 2.5/2.4/2.3**                   |
|-----------------------|-------------------------------------------|
| venue                 | bidRequest.ext.dooh.venue                 |
| venuefixed            | bidRequest.ext.dooh.venuefixed            |
| venuefixedetime       | bidRequest.ext.dooh.venuefixedetime       |
| venuefixedetimedpi    | bidRequest.ext.dooh.venuefixedetimedpi    |
| venuefixedetimedpiext | bidRequest.ext.dooh.venuefixedetimedpiext |
| id                    | bidRequest.ext.dooh.id                    |
| name                  | bidRequest.ext.dooh.name                  |
| pub                   | bidRequest.ext.dooh.pub                   |

Response Mappings: ORTB 3.0 vs ORTB 2.5/ 2.4/ 2.3
=================================================

These mappings are used for converting responses between OpenRTB 3.0 and OpenRTB 2.5/2.4/2.3.

Object: OpenRTB
---------------

| **OpenRTB 3.0**                              | **OpenRTB 2.5/2.4/2.3** |
|----------------------------------------------|-------------------------|
| openrtb.ver                                  | \-                      |
| openrtb.domainspec                           | \-                      |
| openrtb.domainver                            | \-                      |
| [openrtb.response](#object-openrtb-response) | \-                      |

Object: OpenRTB \> Response
---------------------------

*Object Tree – openrtb.response.\<parameter_name\>*

| **OpenRTB 3.0**                   | **OpenRTB 2.5/2.4/2.3** |
|-----------------------------------|-------------------------|
| id                                | bidResponse.id          |
| bidid                             | bidResponse.bidId       |
| nbr                               | bidResponse.nbr         |
| cur                               | bidResponse.cur         |
| cdata                             | bidResponse.customdata  |
| [seatbid](#_Object:_OpenRTB_>_14) |                         |
| ext                               | bidresponse.ext         |

### Object: OpenRTB \> Response \> SeatBid

*Object Tree – openrtb.response.seatbid.\<parameter_name\>*

| **OpenRTB 3.0**              | **OpenRTB 2.5/2.4/2.3/2.4/2.3** |
|------------------------------|---------------------------------|
| seat                         | bidResponse.seatbid.seat        |
| package                      | bidResponse.seatbid.group       |
| [bid](#Response_SeatBid_Bid) |                                 |
| ext                          | bidresponse.seatbid.ext         |

*Object: OpenRTB \> Response \> SeatBid \> Bid*

*Object Tree – openrtb.response.seatbid.bid.\<parameter_name\>*

| **OpenRTB 3.0**                      | **OpenRTB 2.5/2.4/2.3/2.4/2.3**                                                            |
|--------------------------------------|--------------------------------------------------------------------------------------------|
| id                                   | bidResponse.seatbid.bid.id                                                                 |
| item                                 | bidResponse.seatbid.bid.impid                                                              |
| price                                | bidResponse.seatbid.bid.price                                                              |
| deal                                 | bidResponse.seatbid.bid.dealid                                                             |
| cid                                  | bidResponse.seatbid.bid.cid                                                                |
| tactic                               | bidResponse.seatbid.bid.tactic (bidResponse.seatbid.bid.ext.tactic for ORTB v2.4 and v2.3) |
| purl                                 | bidResponse.seatbid.bid.nurl                                                               |
| burl                                 | bidResponse.seatbid.bid.burl (bidResponse.seatbid.bid.ext.burl for OTB v2.4 and v2.3)      |
| lurl                                 | bidResponse.seatbid.bid.lurl (bidResponse.seatbid.bid.ext.burl for OTB v2.4 and v2.3       |
| exp                                  | bidResponse.seatbid.bid.exp                                                                |
| mid                                  | bidResponse.seatBid.bid.adid (bidResponse.seatBid.bid.ext.adid for ORTB v2.3)              |
| [macro](#Response_SeatBid_Bid_Macro) | bidResponse.seatbid.bid.ext.macro                                                          |
| [media](#Response_SeatBid_Bid_Media) |                                                                                            |
| [ext](#Response_SeatBid_Bid_Ext)     | bidresponse.seatbid.bid.ext                                                                |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Macro*

*Object Tree – openrtb.response.seatbid.bid.macro.\<parameter_name\>*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3/2.4/2.3**         |
|-----------------|-----------------------------------------|
| key             | bidResponse.seatbid.bid.ext.macro.key   |
| value           | bidResponse.seatbid.bid.ext.macro.value |
| ext             | bidResponse.seatbid.bid.ext.macro.ext   |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Media*

*Object Tree – openrtb.response.seatbid.bid.media.\<parameter_name\>*

| **OpenRTB 3.0**                      | **OpenRTB 2.5/2.4/2.3/2.4/2.3** |
|--------------------------------------|---------------------------------|
| [ad](#Response_SeatBid_Bid_Media_ad) |   -                              |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Ext*

*Object Tree – openrtb.response.seatbid.bid.ext.\<parameter_name\>*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3/2.4/2.3**  |
|-----------------|----------------------------------|
| protocol        | bidresponse.seatbid.bid.protocol |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Media \> Ad*

| **OpenRTB 3.0**                                   | **OpenRTB 2.5/2.4/2.3**                                                                        |
|---------------------------------------------------|------------------------------------------------------------------------------------------------|
| id                                                | bidResponse.seatbid.bid.crid                                                                   |
| adomain                                           | bidResponse.seatbid.bid.adomain                                                                |
| bundle                                            | bidResponse.seatbid.bid.bundle                                                                 |
| iurl                                              | bidResponse.seatbid.bid.iurl                                                                   |
| cat                                               | bidResponse.seatbid.bid.cat                                                                    |
| cattax                                            | bidResponse.seatBid.bid.ext.cattax                                                             |
| lang                                              | bidResponse.seatbid.bid.language (bidResponse.seatbid.bid.ext.language for ORTB v2.4 and v2.3) |
| attr                                              | bidResponse.seatbid.bid.attr                                                                   |
| secure                                            | bidResponse.seatbid.bid.ext.secure                                                             |
| mrating                                           | bidResponse.seatbid.bid.qagmediarating (bidResponse.seatbid.bid.qagmediarating for ORTB v2.3)  |
| init                                              | bidResponse.seatbid.bid.ext.init                                                               |
| lastmod                                           | bidResponse.seatbid.bid.ext.lastmod                                                            |
| [display](#Response_SeatBid_Bid_Media_Ad_Display) |                                                                                                |
| [video](#Response_SeatBid_Bid_Media_Ad_Video)     |                                                                                                |
| [audio](#Response_SeatBid_Bid_Media_Ad_Audio)     |                                                                                                |
| [audit](#Response_SeatBid_Bid_Media_Ad_Audit)     |                                                                                                |
| ext                                               | bidresponse.seatbid.bid.ext                                                                    |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Media \> Ad \> Display*

| **OpenRTB 3.0**                                     | **OpenRTB 2.5/2.4/2.3**                                                                    |
|-----------------------------------------------------|--------------------------------------------------------------------------------------------|
| mime                                                | bidResponse.seatbid.bid.ext.mime                                                           |
| api                                                 | bidResponse.seatbid.bid.api (bidResponse.seatbid.bid.ext.api for ORTB v2.3)                |
| ctype                                               | bidResponse.seatbid.bid.ext.ctype                                                          |
| w                                                   | bidResponse.seatbid.bid.w                                                                  |
| h                                                   | bidResponse.seatbid.bid.h                                                                  |
| wratio                                              | bidResponse.seatbid.bid.wratio (bidResponse.seatbid.bid.ext.wratio for ORTB v2.4 and v2.3) |
| hratio                                              | bidResponse.seatbid.bid.hratio (bidResponse.seatbid.bid.ext.hratio for ORTB v2.4 and v2.3) |
| priv                                                | bidResponse.seatbid.bid.ext.priv                                                           |
| adm                                                 | bidResponse.seatbid.bid.adm                                                                |
| curl                                                | bidResponse.seatbid.bid.ext.curl                                                           |
| [banner](#Response_SeatBid_Bid_Media_Ad_Display_Ba) |                                                                                            |
| [native](#Response_SeatBid_Bid_Media_Ad_Display_Na) | bidResponse.seatbid.bid.adm                                                                |
| event                                               | bidResponse.seatbid.bid.ext.event.                                                         |
| ext                                                 | bidResponse.seatbid.bid.ext                                                                |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Media \> Ad \> Display \> Banner*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                 |
|-----------------|-----------------------------------------|
| img             | bidResponse.seatbid.bid.ext.banner.img  |
| link            | bidResponse.seatbid.bid.ext.banner.link |
| ext             | bidResponse.seatbid.bid.ext.banner.ext  |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Media \> Ad \> Display \> Native*

| **OpenRTB 3.0**                              | **OpenRTB 2.5/2.4/2.3**                |
|----------------------------------------------|----------------------------------------|
| [ext](#Response_Media_Ad_Display_Na_Ext)     | bidresponse.seatbid.bid.adm.native.ext |
| [link](#Response_Media_Ad_Display_Na_Link)   |-                                        |
| [asset](#Response_Media_Ad_Display_Na_Asset) |   -                                     |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Media \> Ad \> Display \> Native \> Ext*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                        |
|-----------------|------------------------------------------------|
| imptrackers     | bidresponse.seatbid.bid.adm.native.imptrackers |
| jstracker       | bidresponse.seatbid.bid.adm.native.jstrackers  |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Media \> Ad \> Display \> Native \> Link*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                               |
|-----------------|-------------------------------------------------------|
| url             | bidresponse.seatbid.bid.adm.native.link.url           |
| urlfb           | bidresponse.seatbid.bid.adm.native.link.fallback      |
| trkr            | bidresponse.seatbid.bid.adm.native.link.clicktrackers |
| ext             | bidresponse.seatbid.bid.adm.native.link.ext           |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Media \> Ad \> Display \> Native \> Asset*

| **OpenRTB 3.0**                                  | **OpenRTB 2.5/2.4/2.3**                           |
|--------------------------------------------------|---------------------------------------------------|
| id                                               | bidresponse.seatbid.bid.adm.native.asset.id       |
| req                                              | bidresponse.seatbid.bid.adm.native.asset.required |
| [title](#Response_Media_Ad_Display_Na_Asset_Tit) |                                                   |
| [image](#Response_Media_Ad_Display_Na_Asset_Img) |                                                   |
| [video](#Response_Media_Ad_Display_Na_Asset_Vid) |                                                   |
| [data](#Response_Media_Ad_Display_Na_Asset_Data) |                                                   |
| [link](#Response_Media_Ad_Display_Na_Asset_Link) |                                                   |
| ext                                              | bidresponse.seatbid.bid.adm.native.asset.ext      |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Media \> Ad \> Display \> Native \> Asset \> Title*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                                |
|-----------------|--------------------------------------------------------|
| text            | bidresponse.seatbid.bid.adm.native.asset.title.text    |
| len             | bidresponse.seatbid.bid.adm.native.asset.title.ext.len |
| ext             | bidresponse.seatbid.bid.adm.native.asset.title.ext     |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Media \> Ad \> Display \> Native \> Asset \> Image*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                               |
|-----------------|-------------------------------------------------------|
| url             | bidresponse.seatbid.bid.adm.native.asset.img.url      |
| w               | bidresponse.seatbid.bid.adm.native.asset.img.w        |
| h               | bidresponse.seatbid.bid.adm.native.asset.img.h        |
| type            | bidresponse.seatbid.bid.adm.native.asset.img.ext.type |
| ext             | bidresponse.seatbid.bid.adm.native.asset.img.ext      |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Media \> Ad \> Display \> Native \> Asset \> Video*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                                 |
|-----------------|---------------------------------------------------------|
| adm             | bidresponse.seatbid.bid.adm.native.asset.video.vasttag  |
| curl            | bidresponse.seatbid.bid.adm.native.asset.video.ext.curl |
| ext             | bidresponse.seatbid.bid.adm.native.asset.video.ext      |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Media \> Ad \> Display \> Native \> Asset \> Data*

| **OpenRTB 3.0**                                  | **OpenRTB 2.5/2.4/2.3**                                |
|--------------------------------------------------|--------------------------------------------------------|
| value                                            | bidresponse.seatbid.bid.adm.native.asset.data.value    |
| len                                              | bidresponse.seatbid.bid.adm.native.asset.data.ext.len  |
| type                                             | bidresponse.seatbid.bid.adm.native.asset.data.ext.type |
| [ext](#Response_Media_Ad_Display_Na_Asset_DataL) | bidresponse.seatbid.bid.adm.native.asset.data.ext      |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Media \> Ad \> Display \> Native \> Asset \> Data \> Ext*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                             |
|-----------------|-----------------------------------------------------|
| label           | bidresponse.seatbid.bid.adm.native.asset.data.label |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Media \> Ad \> Display \> Native \> Asset \> Link*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                                     |
|-----------------|-------------------------------------------------------------|
| url             | bidresponse.seatbid.bid.adm.native.asset.link.url           |
| urlfb           | bidresponse.seatbid.bid.adm.native.asset.link.fallback      |
| trkr            | bidresponse.seatbid.bid.adm.native.asset.link.clicktrackers |
| ext             | bidresponse.seatbid.bid.adm.native.asset.link.ext           |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Media \> Ad \> Display \> Event*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                  |
|-----------------|------------------------------------------|
| type            | bidResponse.seatbid.bid.ext.event.type   |
| method          | bidResponse.seatbid.bid.ext.event.method |
| api             | bidResponse.seatbid.bid.ext.event.api    |
| url             | bidResponse.seatbid.bid.ext.event.url    |
| cdata           | bidResponse.seatbid.bid.ext.event.cdata  |
| ext             | bidResponse.seatbid.bid.ext.event.ext    |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Media \> Ad \> Video*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                                                     |
|-----------------|-----------------------------------------------------------------------------|
| mime            | bidResponse.seatbid.bid.ext.mime                                            |
| api             | bidResponse.seatbid.bid.api (bidResponse.seatbid.bid.ext.api for ORTB v2.3) |
| ctype           | bidResponse.seatbid.bid.ext.ctype                                           |
| dur             | bidResponse.seatbid.bid.ext.dur                                             |
| adm             | bidResponse.seatbid.bid.adm                                                 |
| curl            | bidResponse.seatbid.bid.ext.curl                                            |
| ext             | bidresponse.seatbid.bid.ext                                                 |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Media \> Ad \> Audio*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                                                     |
|-----------------|-----------------------------------------------------------------------------|
| mime            | bidresponse.seatbid.bid.ext.mime                                            |
| api             | bidresponse.seatbid.bid.api (bidresponse.seatbid.bid.ext.api for ORTB v2.3) |
| ctype           | bidresponse.seatbid.bid.ext.ctype                                           |
| dur             | bidresponse.seatbid.bid.ext.dur                                             |
| adm             | bidresponse.seatbid.bid.adm                                                 |
| curl            | bidresponse.seatbid.bid.ext.curl                                            |
| ext             | bidresponse.seatbid.bid.ext                                                 |

*Object: OpenRTB \> Response \> SeatBid \> Bid \> Media \> Ad \> Audit*

| **OpenRTB 3.0** | **OpenRTB 2.5/2.4/2.3**                |
|-----------------|----------------------------------------|
| status          | bidresponse.seatbid.bid.ext.status     |
| feedback        | bidresponse.seatbid.bid.ext.feedback   |
| init            | bidresponse.seatbid.bid.ext.audit.init |
| lastmod         | bidresponse.seatbid.bid.ext.lastmod    |
| **corr**        | bidresponse.seatbid.bid.ext.corr       |
| ext             | bidresponse.seatbid.bid.ext            |
