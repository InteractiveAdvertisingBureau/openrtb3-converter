## The new structure of OpenRTB 3.0

Programmatic advertising has seen consistent growth over the last few years. However, due to varied requirements of different partners and the increasing complexity in the supply chain, it was felt that the underlying structure of oRTB was not scalable enough and needed to be changed.

OpenRTB 3.0 thus introduced a layered approach where the commerce transaction (e.g., auction parameters, deals, bids, etc.) was differentiated from the object specifications (e.g ads, placements, users, devices, sites, publishers, etc). The commerce transaction is supposed to be the focus of oRTB while the objects being transacted was added to AdCOM (Advertising Common Object Model).

This has two benefits:
 - The AdCOM layer can be reused in other specifications such as OpenDirect, AdManagementAPI and custom specifications 
 - The object layer can be revised independent of the oRTB spec.

## Problem:

However, structural changes also mean that oRTB 3.0 is not backward compatible. This becomes problematic when different partners in the supply chain are on different versions of oRTB.

## Solution: the Library

This is the problem which media.net wants to solve with the open source library which can be used to convert requests and responses from OpenRTB 3.0 to OpenRTB 2.x and vice-versa.

## How is the conversion done?

The conversion is done by analyzing the objects and fields for requests and responses across oRTB 3.0 and oRTB2.x. The objects and their fields from one specification are mapped to the corresponding or closest objects and fields of the other specification. In case where a direct or close mapping does not exist, extension sub-objects of the respective objects are used.

## Specification versions supported

The library allows conversion from OpenRTB v 2.3, 2.4, 2.5 to OpenRTB v3.0 and vice-versa for both requests and responses.

AdCOM 1.0 is supported as the Layer 4 object.

OpenRTB Dynamic Native Ads API Specification Version 1.1  is used for transacting native ad objects for OpenRTB 2.x.

## Handling Default values 

Default values allow information to be interchanged between two neighbouring partners in the supply chain even without the fields being included in the request or response. 

However, the above works only when the partners are on the same standard with similar fields and default values being present. For cases where the partners are on different oRTB standards, a conversion is necessary. 

When converting a request or response from oRTB version A to version B:
 - if the field has a default value in A, but does not have a default value in B, the mapped field is sent with the default value in A.
 - if the field has a default value in B but not in A, no field is sent.


## Category Taxonomy 

When converting from 3.0 to 2.x, cattax would be sent in the ext sub-object of the respective object . If the cattax field is not specified, cattax: 2 is sent.

When converting from 2.x to 3.0, since only taxonomy 1.0 is supported, cattax would be sent with value 1.

## Native Requests and Responses

OpenRTB Dynamic Native Ads API Specification Version 1.1 is used for converting native objects from 2.x to 3.0 and back.

### Key points
 - Support for both JSON encoded and native object tree is provided. The user can choose the input and output format.
 - For request: In 2.x, native object is present in bidreqeust.item.native.request.native in either string or json object format. The JSON encoded string is parsed and the sub objects extracted. They are then mapped to appropriate fields in ortb 3.0 native object tree.
 - For request: Converting from 3.0 to 2.x works in a similar way. The native object tree of 3.0 is parsed and based on user input can be converted into object tree inside bidreqeust.item.native.request.native or a JSON encoded string.
 - For response: For converting from 2.x to 3.0, the native object is extracted from seatbid.bid.adm available either in JSON encoded format or as object tree. They are then mapped to appropriate fields in oRTB 3.0 native object tree.
 - For response: For converting, from 3.0 to 2.x, the native object tree in 3.0 is parsed and based on user input can be either converted to a JSON encoded string or a native object tree and sent inside seatbid.bid.adm.

## Handling banner in Response

For converting from oRTB 3.0 to 2.x, banner objects if separately present, are added in seatbid.bid.ext object in response in 2.x.

## Handling curl, purl, nurl in Response

For converting from oRTB 3.0 to 2.x, curl is added to the ext sub-object of the respective object and purl is added to the nurl of the respective object.
For converting from oRTB 2.x  to 3.0, nurl is mapped to both curl and purl fields of the respective object.

## Validation support

The library also supports validation of the incoming object (request or response) against a given oRTB specification.

## Support for 3.0 specific fields in 2.x

To allow partners still on OpenRTB 2.3/2.4/2.5 to use the additional fields in 3.0, the library supports these fields in respective ext sub-objects. The object and field mappings which would be used are mentioned in the mapping available in wiki.

## Support for Custom Mappings

Custom mapping between objects and fields is also supported where the custom config can be used to map any object or field in 2.x to any field in 3.0.

## Handling multiple AdTypes in Response

In oRTB 2.x, the response structure does not have object tree specified for different ad types, while 3.0 has the objects for different ad types specified under item.media.ad.

While converting a response from 2.x to 3.0, to support multi-impression conversion with different ads types, the user can use the config and pass the ad type associated with each impression as key-values. In case no config is present, the impressions are considered as ad type banner.

## Assumption

Support for conversion is provided where each item or impression is mapped to only one ad type. However multiple impressions where different impressions are mapped to different ad types are still supported for conversions.

## Documentation

Details about how to use the library are available [here](https://github.com/media-net/openrtb3-converter/wiki/OpenRTB-3.0-Converter---Usage-Guidelines).

The mappings which serve as the standard for conversion between OpenRTB 3.0 and OpenRTB 2.5 are available [here](MAPPINGS.md) and here [PDF](openrtb-3-converter-mappings.pdf).
