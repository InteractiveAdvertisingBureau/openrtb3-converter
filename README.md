Build status: [![build_status](https://travis-ci.org/media-net/openrtb3-converter.svg?branch=master)](https://travis-ci.org/media-net/openrtb3-converter)

## The new structure of OpenRTB 3.0

Programmatic advertising has seen consistent growth over the last few years. However, due to varied requirements of different partners and the increasing complexity in the supply chain, it was felt that the underlying structure of oRTB was not scalable enough and needed to be changed.

OpenRTB 3.0 thus introduced a layered approach where the commerce transaction (e.g., auction parameters, deals, bids, etc.) was differentiated from the object specifications (e.g ads, placements, users, devices, sites, publishers, etc). The commerce transaction is supposed to be the focus of oRTB while the objects being transacted was added to AdCOM (Advertising Common Object Model).

This has two benefits:
1.) The AdCOM layer can be reused in other specifications such as OpenDirect, AdManagementAPI and custom specifications 
2.) The object layer can be revised independent of the oRTB spec.


## Problem:

However, structural changes also mean that oRTB 3.0 is not backward compatible. This becomes problematic when different partners in the supply chain are on different versions of oRTB.

## Solution: the Library

This is the problem which media.net wants to solve with the open source library which can be used to convert requests and responses from OpenRTB 3.0 to OpenRTB 2.x and vice-versa.

How the convertor fits in the supply chain when an exchange and a DSP/Exchange are on different versions of OpenRTB:

![Supply chain](https://github.com/media-net/openrtb3-converter/blob/master/convert.png)


## How is the conversion done?

The conversion is done by analyzing the objects and fields for requests and responses across oRTB 3.0 and oRTB2.x. The objects and their fields from one specification are mapped to the corresponding or closest objects and fields of the other specification. In case where a direct or close mapping does not exist, extension sub-objects of the respective objects are used.

## Specification versions supported

The library allows conversion from OpenRTB v 2.3, 2.4, 2.5 to OpenRTB v3.0 and vice-versa for both requests and responses.

AdCOM 1.0 is supported as the Layer 4 object.

OpenRTB Dynamic Native Ads API Specification Version 1.1  is used for transacting native ad objects for OpenRTB 2.x.

## Documentation	

 Details about how to use the library are available [here](https://github.com/media-net/openrtb3-converter/wiki/1.-Home).	

 The mappings which serve as the standard for conversion between OpenRTB 3.0 and OpenRTB 2.5 are available [here](MAPPINGS.md) and here [PDF](https://github.com/media-net/openrtb3-converter/raw/master/openrtb-3-converter-mappings.pdf).

