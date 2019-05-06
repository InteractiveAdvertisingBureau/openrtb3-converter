
# Converter - example

This module provides a tomcat jar that can be deployed anywhere and use openrtb converter to convert from ORTB v3.0 to ORTB v2.x and vice versa. 

# Installation
Package the code into a jar using 
> mvn clean install

This will create a jar at location **openrtb-converter/converter-api/target/original-converter-example##{{version}}.jar**. You can deploy this jar at any location. To deploy run the following command at the location where the jar is located
>java -jar original-converter-example##{{version}}.jar

This will start your server and your converter is ready to use
>Note : The server runs on 9090 port
## About

The calls made to the server are POST calls. The servlet accepts eight query params which are as follows - 

|  Query Param     |   Values          | Meaning
|------------------|-------------------|-----------
| type             |request/response  | Convert ORTB request or response
| conversionType |3xTo2x/2xTo3x| Convert from 2.x To 3.0 or 3.0 To 2.x
|nativeRequestAsString|true/false|Determines the type of native request in 2.x while converting from 3.x to 2.x|
|nativeResponseAsString|true/false|Determines the type of native response in 2.x while converting from 3.x to 2.x|
|adTypeMapping|Map|If in 2.x response, bidResponse.seatBid.bid.impId = imp1, then when converting to 3.0, this impression will be converted to Banner object. Aditionally, if in 3.0 response, openrtb.response.seatbid.bid.media.ad.display.banner is not null, then an appropriate bidResponse.seatBid.bid will be created with impId = imp1. Note that, support for multiple ad types for a single impression is not supported|
|disableCloning|true/false|Whether to clone object references or not while conversion. Only used for collections|
|validate|true/false|Determines whether the input request or response needs to be v alidated or not|
|openRtbVersion2_XVersion|TWO_DOT_THREE/TWO_DOT_FOUR/TWO_DOT_FIVE | Determines the minor version in 2.x spec|

## POST Body
All the post bodies are json strings
#### Request 2x To 3x
 - bidRequest - The bidRequest of ORTB 2.x
#### Request 3x To 2x
 - openRTB - The ortb request of ORTB 3.0
#### Response 2x To 3x
 - response - The response of ORTB 2.x 
#### Response 3x To 2x
 - openRTB - The ortb response of ORTB 3.0

## Examples

#### Request 2.x To 3.0
**Endpoint** - http://host:9090/Converter?type=request&conversiontype=2xTo3x <br>
**Json Body** - https://github.com/InteractiveAdvertisingBureau/openrtb3-converter/blob/master/converter-example/src/main/resources/examples/request25.json
#### Request 3.0 To 2.x
**Endpoint** - http://host:9090/Converter?type=request&conversiontype=3xTo2x <br>
**Json Body** - https://github.com/InteractiveAdvertisingBureau/openrtb3-converter/blob/master/converter-example/src/main/resources/examples/request30.json

#### Response 2.x To 3.0
**Endpoint** - http://host:9090/Converter?type=response&conversiontype=2xTo3x&adTypeMapping={"1":"NATIVE"} <br>
**Json Body** - https://github.com/InteractiveAdvertisingBureau/openrtb3-converter/blob/master/converter-example/src/main/resources/examples/response25.json

#### Response 3.0 To 2.x
**Endpoint** - http://host:9090/Converter?type=response&conversiontype=3xTo2x <br>
**Json Body** - https://github.com/InteractiveAdvertisingBureau/openrtb3-converter/blob/master/converter-example/src/main/resources/examples/response30.json


## Conclusion
This api can be used to test the openrtb-converter and can the code can be used as a guide to use the converter. However, this module does not provide the support to override the individual converters, which is provided by openrtb-converter. 
The user just needs to pass the overriding conversion to converter map to the convert function. Please go through the converter document for details
