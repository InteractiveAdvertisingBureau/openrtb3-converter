
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
**Endpoint** - http://host:9090/Converter?type=request&conversiontype=2xTo3x
**Json Body**
  ```
  {
 	"id": "80ce30c53c16e6ede735f123ef6e32361bfc7b22",
 	"at": 1,
 	"test": 1,
 	"tmax": 100,
 	"wseat": ["a"],
 	"bseat": null,
 	"regs": {
 		"coppa": 1,
 		"ext": {
 			"gdpr": 1,
 			"dummy": "value1"
 		}
 	},
 	"ext": {
 		"cattax": 2,
 		"dummy": "value1",
 		"restrictions": {
 			"ext": {
 				"dummy": "value1"
 			}
 		},
 		"source": {
 			"fd": 1,
 			"tid": "dummy",
 			"pchain": "dummy",
 			"ext": {
 				"ts": 1,
 				"ds": "ds",
 				"dsmap": "abcdefghij",
 				"cert": "abc.com/xyz/efg",
 				"digest": "digest:digest, abcde:efg",
 				"dummy": "value1"
 			}
 		}
 	},
 	"cur": ["USD"],
 	"allimps": 0,
 	"wlang": ["en"],
 	"bcat": ["bb"],
 	"badv": ["cc"],
 	"bapp": ["nn"],
 	"imp": [{
 		"id": "1",
 		"bidfloor": 0.03,
 		"bidfloorcur": "USD",
 		"pmp": {
 			"private_auction": 1,
 			"deals": [{
 				"id": "ww",
 				"bidfloor": 0.0,
 				"bidfloorcur": "USD",
 				"at": 1,
 				"wseat": ["dummy", "dummy1"],
 				"wadomain": ["ss.com", "abc.com"],
 				"ext": {
 					"dummy": "value1"
 				}
 			}],
 			"ext": {
 				"dummy": "value1"
 			}
 		},
 		"displaymanager": "dummy",
 		"displaymanagerver": "22",
 		"clickbrowser": 1,
 		"secure": 1,
 		"iframebuster": ["abc", "def"],
 		"instl": 0,
 		"tagid": "re",
 		"exp": 1,
 		"banner": {
 			"id": "dummy",
 			"format": [{
 					"w": 200,
 					"h": 20,
 					"wratio": 200,
 					"hratio": 30,
 					"wmin": 3,
 					"ext": {
 						"dummy": "value1"
 					}
 				},
 				{
 					"w": 33,
 					"h": 11,
 					"wratio": 200,
 					"hratio": 30,
 					"wmin": 3,
 					"ext": {
 						"dummy": "value1"
 					}
 				}
 			],
 			"h": 250,
 			"w": 300,
 			"wmax": 22,
 			"hmax": 11,
 			"wmin": 1,
 			"hmin": 9,
 			"btype": [3, 4],
 			"battr": [1, 2],
 			"topframe": 22,
 			"expdir": [3, 9],
 			"api": [3, 2],
 			"vcm": 22,
 			"mimes": ["application/x-shockwave-flash",
 				"image/jpg"
 			],
 			"pos": 0,
 			"ext": {
 				"qty": 1,
 				"seq": 1,
 				"context": 2,
 				"ptype": 1,
 				"ctype": [1],
 				"unit": 1,
 				"priv": 0,
 				"dummy": "value1"
 			}
 		},
 		"ext": {
 			"ssai": 1,
 			"reward": 1,
 			"admx": 1,
 			"sequence": 1,
 			"curlx": 0,
 			"ampren": 1,
 			"dt": 1,
 			"dlvy": 1,
 			"metric": [{
 				"type": "dummy",
 				"value": 1.0,
 				"vendor": "vendorDummy",
 				"ext": {
 					"dummy": "value1"
 				}
 			}],
 			"event": [{
 					"type": 1,
 					"method": [1],
 					"api": [1, 2],
 					"jstrk": ["abc", "Def"],
 					"wjs": 1,
 					"pxtrk": ["abc", "def"],
 					"wpx": 1,
 					"ext": {
 						"dummy": "value1"
 					}
 				},
 				{
 					"type": 1,
 					"method": [1],
 					"api": [1, 2],
 					"jstrk": ["abc", "Def"],
 					"wjs": 1,
 					"pxtrk": ["abc", "def"],
 					"wpx": 1,
 					"ext": {
 						"dummy": "value1"
 					}
 				}
 			],
 			"dummy": "value1"
 		}
 	}],
 	"site": {
 		"id": "102855",
 		"name": "abdk",
 		"sectioncat": ["dummy1", "dummy"],
 		"pagecat": ["dummy", "dummy1"],
 		"cat": ["IAB3-1", "IAB3-2"],
 		"ref": "value",
 		"search": "searchkey",
 		"mobile": 1,
 		"privacypolicy": 1,
 		"keywords": "key=value",
 		"domain": "www.foobar.com",
 		"page": "http://www.foobar.com/1234.html ",
 		"publisher": {
 			"id": "8953",
 			"name": "foobar.com",
 			"cat": ["IAB3-1", "IAB3-2"],
 			"domain": "foobar.com",
 			"ext": {
 				"cattax": 2,
 				"dummy": "value1"
 			}
 		},
 		"content": {
 			"id": "rfewa",
 			"episode": 1,
 			"title": "sf",
 			"series": "rf",
 			"season": "wed",
 			"genre": "sefw",
 			"album": "edwe",
 			"artist": "we",
 			"isrc": "ewde",
 			"producer": {
 				"id": "dummy",
 				"name": "edew",
 				"cat": ["IAB3-1", "IAB3-2"],
 				"domain": "abc.com",
 				"ext": {
 					"cattax": 2,
 					"dummy": "value1"
 				}
 			},
 			"url": "ewfe.com",
 			"cat": ["IAB3-1", "IAB3-2"],
 			"prodq": 1,
 			"videoquality": 2,
 			"context": 1,
 			"contentrating": "wqewq",
 			"userrating": "value",
 			"qagmediarating": 1,
 			"keywords": "efea",
 			"livestream": 1,
 			"sourcerelationship": 1,
 			"len": 2,
 			"language": "en",
 			"embeddable": 1,
 			"data": [{
 				"id": "1",
 				"name": "Abcdef",
 				"segment": [{
 					"id": "idDummy",
 					"name": "name123",
 					"value": "acde",
 					"ext": {
 						"dummy": "value1"
 					}
 				}],
 				"ext": {
 					"dummy": "value1"
 				}
 			}],
 			"ext": {
 				"cattax": 2,
 				"dummy": "value1"
 			}
 		},
 		"ext": {
 			"cattax": 2,
 			"dummy": "value1",
 			"amp": 2
 		}
 	},
 	"device": {
 		"ua": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/537.13 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2",
 		"ip": "123.145.167.10",
 		"geo": {
 			"lat": 22.22,
 			"lon": 22.2,
 			"type": 1,
 			"accuracy": 1,
 			"lastfix": 1,
 			"ipservice": 2,
 			"country": "IN",
 			"region": "dummy",
 			"regionfips104": "dummy",
 			"metro": "dummyval",
 			"city": "dummy",
 			"zip": "dummyval",
 			"utcoffset": 1,
 			"ext": {
 				"dummy": "value1"
 			}
 		},
 		"dnt": 1,
 		"lmt": 1,
 		"ipv6": "123.145.167.1.3.2",
 		"devicetype": 2,
 		"make": "dummy",
 		"model": "edew",
 		"os": "Android",
 		"osv": "hwv",
 		"hwv": "ww",
 		"h": 2,
 		"w": 2,
 		"ppi": 1,
 		"pxratio": 1.2,
 		"js": 1,
 		"geofetch": 2,
 		"flashver": "dummyval",
 		"language": "dummy",
 		"carrier": "dummy",
 		"mccmnc": "value",
 		"connectiontype": 2,
 		"ext": {
 			"xff": "1.2.3.4",
 			"iptr": 1,
 			"dummy": "value1"
 		}
 	},
 	"user": {
 		"id": "55816b39711f9b5acf3b90e313ed29e51665623f",
 		"buyeruid": "eewew",
 		"yob": 2,
 		"gender": "M",
 		"keywords": "key=value",
 		"customdata": "value",
 		"geo": {
 			"lat": 22.22,
 			"lon": 22.2,
 			"type": 1,
 			"accuracy": 1,
 			"lastfix": 1,
 			"ipservice": 2,
 			"country": "IN",
 			"region": "dummy",
 			"regionfips104": "dummy",
 			"metro": "dummyval",
 			"city": "dummy",
 			"zip": "dummyval",
 			"utcoffset": 1,
 			"ext": {
 				"dummy": "value1"
 			}
 		},
 		"data": [{
 			"id": "wwe",
 			"name": "dummyval",
 			"segment": [{
 				"id": "id1",
 				"name": "rewfe",
 				"value": "value",
 				"ext": {
 					"dummy": "value1"
 				}
 			}],
 			"ext": {
 				"dummy": "value1"
 			}
 		}],
 		"ext": {
 			"consent": "abcdef"
 		}
 	}
 }
```
#### Request 3.0 To 2.x
**Endpoint** - http://host:9090/Converter?type=request&conversiontype=3xTo2x
**Json Body**
```
{
	"openrtb": {
		"ver": "3.0",
		"domainSpec": "adcom",
		"domainVer": "1.0",
		"request": {
			"id": "80ce30c53c16e6ede735f123ef6e32361bfc7b22",
			"test": 1,
			"tmax": 100,
			"at": 1,
			"cur": ["USD"],
			"seat": ["a"],
			"wseat": 1,
			"cdata": "value",
			"source": {
				"tid": "dummy",
				"ts": 1,
				"ds": "ds",
				"dsmap": "abcdefghij",
				"cert": "abc.com/xyz/efg",
				"digest": "digest:digest, abcde:efg",
				"pchain": "dummy",
				"ext": {
					"dummy": "value1",
					"fd": 1
				}
			},
			"item": [{
				"id": "1",
				"qty": 1,
				"seq": 1,
				"flr": 0.03,
				"flrcur": "USD",
				"exp": 1,
				"dt": 1,
				"dlvy": 1,
				"metric": [{
					"type": "dummy",
					"value": 1.0,
					"vendor": "vendorDummy",
					"ext": {
						"dummy": "value1"
					}
				}],
				"deal": [{
					"id": "ww",
					"flr": 0.0,
					"flrcur": "USD",
					"at": 1,
					"wseat": ["dummy", "dummy1"],
					"wadomain": ["ss.com", "abc.com"],
					"ext": {
						"dummy": "value1"
					}
				}],
				"spec": {
					"placement": {
						"tagid": "re",
						"ssai": 1,
						"sdk": "dummy",
						"sdkver": "22",
						"reward": 1,
						"wlang": ["en"],
						"secure": 1,
						"admx": 1,
						"curlx": 0,
						"video": {
							"ptype": 1,
							"pos": 1,
							"delay": 1,
							"skip": 1,
							"comptype": [
								1,
								2
							],
							"skipmin": 1,
							"skipafter": 1,
							"playmethod": 1,
							"playend": 1,
							"clktype": 1,
							"mime": [
								"abc",
								"def"
							],
							"api": [
								2,
								3
							],
							"ctype": [
								2,
								3
							],
							"w": 1,
							"h": 1,
							"unit": 1,
							"mindur": 1,
							"maxdur": 1,
							"maxext": 1,
							"minbitr": 2,
							"maxbitr": 2,
							"delivery": [
								1,
								2
							],
							"maxseq": 1,
							"linear": 1,
							"boxing": 1,
							"comp": [{
								"id": "3.0",
								"vcm": 1,
								"display": {
									"pos": 0,
									"topframe": 22,
									"ptype": 1,
									"context": 2,
									"mime": ["application/x-shockwave-flash", "image/jpg"],
									"api": [3, 2],
									"ctype": [1],
									"w": 300,
									"h": 250,
									"unit": 1,
									"priv": 0,
									"displayfmt": [{
										"w": 200,
										"h": 20,
										"wratio": 200,
										"hratio": 30,
										"expdir": [3, 9],
										"ext": {
											"wmin": 3,
											"dummy": "value1"
										}
									}, {
										"w": 33,
										"h": 11,
										"wratio": 200,
										"hratio": 30,
										"expdir": [3, 9],
										"ext": {
											"wmin": 3,
											"dummy": "value1"
										}
									}],
									"ext": {
										"dummy": "value1",
										"btype": [3, 4]
									}
								},
								"ext": {
									"dummy": "entry"
								}
							}]
						}
					}
				},
				"ext": {
					"dummy": "value1",
					"sequence": 1,
					"pmp": {
						"ext": {
							"dummy": "value1"
						}
					}
				},
				"private": 1
			}],
			"context": {
				"site": {
					"id": "102855",
					"name": "abdk",
					"pub": {
						"id": "8953",
						"name": "foobar.com",
						"domain": "foobar.com",
						"cat": ["IAB3-1", "IAB3-2"],
						"cattax": 2,
						"ext": {
							"dummy": "value1"
						}
					},
					"content": {
						"id": "rfewa",
						"episode": 1,
						"title": "sf",
						"series": "rf",
						"season": "wed",
						"artist": "we",
						"genre": "sefw",
						"album": "edwe",
						"isrc": "ewde",
						"url": "ewfe.com",
						"cat": ["IAB3-1", "IAB3-2"],
						"cattax": 2,
						"prodq": 1,
						"context": 2,
						"rating": "wqewq",
						"urating": "value",
						"mrating": 1,
						"keywords": "efea",
						"live": 1,
						"srcrel": 1,
						"len": 2,
						"lang": "en",
						"embed": 1,
						"producer": {
							"id": "dummy",
							"name": "edew",
							"domain": "abc.com",
							"cat": ["IAB3-1", "IAB3-2"],
							"cattax": 2,
							"ext": {
								"dummy": "value1"
							}
						},
						"data": [{
							"id": "1",
							"name": "Abcdef",
							"segment": [{
								"id": "idDummy",
								"name": "name123",
								"value": "acde",
								"ext": {
									"dummy": "value1"
								}
							}],
							"ext": {
								"dummy": "value1"
							}
						}],
						"ext": {
							"dummy": "value1",
							"videoquality": 2
						}
					},
					"domain": "www.foobar.com",
					"cat": ["IAB3-1", "IAB3-2"],
					"sectcat": ["dummy1", "dummy"],
					"pagecat": ["dummy", "dummy1"],
					"cattax": 2,
					"privpolicy": 1,
					"keywords": "key=value",
					"page": "http://www.foobar.com/1234.html ",
					"ref": "value",
					"search": "searchkey",
					"mobile": 1,
					"amp": 2,
					"ext": {
						"dummy": "value1"
					}
				},
				"user": {
					"id": "55816b39711f9b5acf3b90e313ed29e51665623f",
					"buyeruid": "eewew",
					"yob": 2,
					"gender": "M",
					"keywords": "key=value",
					"consent": "abcdef",
					"geo": {
						"type": 1,
						"lat": 22.22,
						"lon": 22.2,
						"accur": 1,
						"lastfix": 1,
						"ipserv": 2,
						"country": "IN",
						"region": "dummy",
						"metro": "dummyval",
						"city": "dummy",
						"zip": "dummyval",
						"utcoffset": 1,
						"ext": {
							"dummy": "value1",
							"regionfips104": "dummy"
						}
					},
					"data": [{
						"id": "wwe",
						"name": "dummyval",
						"segment": [{
							"id": "id1",
							"name": "rewfe",
							"value": "value",
							"ext": {
								"dummy": "value1"
							}
						}],
						"ext": {
							"dummy": "value1"
						}
					}]
				},
				"device": {
					"type": 2,
					"ua": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/537.13 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2",
					"dnt": "1",
					"lmt": 1,
					"make": "dummy",
					"model": "edew",
					"os": 2,
					"osv": "hwv",
					"hwv": "ww",
					"h": 2,
					"w": 2,
					"ppi": 1,
					"pxratio": 1.2,
					"js": 1,
					"lang": "dummy",
					"ip": "123.145.167.10",
					"ipv6": "123.145.167.1.3.2",
					"xff": "1.2.3.4",
					"iptr": 1,
					"carrier": "dummy",
					"mccmnc": "value",
					"contype": 2,
					"geofetch": 2,
					"geo": {
						"type": 1,
						"lat": 22.22,
						"lon": 22.2,
						"accur": 1,
						"lastfix": 1,
						"ipserv": 2,
						"country": "IN",
						"region": "dummy",
						"metro": "dummyval",
						"city": "dummy",
						"zip": "dummyval",
						"utcoffset": 1,
						"ext": {
							"dummy": "value1",
							"regionfips104": "dummy"
						}
					},
					"ext": {
						"dummy": "value1",
						"flashver": "dummyval"
					}
				},
				"regs": {
					"coppa": 1,
					"gdpr": 1,
					"ext": {
						"dummy": "value1"
					}
				},
				"restrictions": {
					"bcat": ["bb"],
					"cattax": 2,
					"badv": ["cc"],
					"bapp": ["nn"],
					"battr": [1, 2],
					"ext": {
						"dummy": "value1"
					}
				}
			},
			"ext": {
				"dummy": "value1"
			},
			"package": 0
		}
	}
}
```
#### Response 2.x To 3.0
**Endpoint** - http://host:9090/Converter/?type=response&conversiontype=2xTo3x&adTypeMapping={"1":"NATIVE"}
**Json Body**
```
{
	"id": "0123456789ABCDEF",
	"seatbid": [{
		"bid": [{
			"id": "yaddayadda",
			"impid": "1",
			"price": 1.5,
			"adid": null,
			"nurl": "https://api.bttrack.com/win?ts=1546937303944&id=93b55876-71b1-42fd-8546-e9cec92fd05f&cid=63309&crid=671436&pid=1567915613&data=OuJifVtEKZqw3FQ5__rtW7OTLgwj1FiGGOvTa0VHQ2oeZTES-4JqBt5mggOnR0e8Keeyd9oogK-izuHRAKyiAtec9wBe21Z0F2fCw-FmcJx_T0n2dogLwv3ZpQw7MkGfj5wgYxfE3iiHyigch9d2FhkxG8QzYNun_o24PQkJ9Iu33KDimPUCE7WuZz76n6Xgh67B7tXIfzpI9ocLD5Mwm43S9ashLojm5BBoyU-QkGLJLO8-fbQiNJpXHccn4N6mmYE3fyvzVPYknIrvUA239AVIGBcpGRY-f5k06QYM8NUp0&iid=d4b96a9c-d1ee-48ed-9547-47bddfc341f2&price=${AUCTION_PRICE}&reqid=${AUCTION_ID}",
			"adm": {
				"native": {
					"ver": "1.1",
					"assets": [{
							"id": 601,
							"title": {
								"text": "Levi's� End of Season Sale"
							}
						},
						{
							"id": 401,
							"img": {
								"url": "https://cdn.bttrack.com/licrop/300/160/200798",
								"w": 300,
								"h": 160
							}
						},
						{
							"id": 502,
							"data": {
								"value": "Not only is there a sale, but the sale is on sale. Shop online at Levi's�"
							}
						},
						{
							"id": 512,
							"data": {
								"value": "CLICK HERE"
							}
						},
						{
							"id": 501,
							"data": {
								"value": "Levi's�"
							}
						}
					],
					"link": {
						"url": "https://bttrack.com/Click/Native?data=OuJifVtEKZqw3Hzm6d7tW6_q5tYX8B4nxbI52yTxSh54DSqfii_uHcyG7C8X0SkSWL8Hl1OPcCgoAPAxvLvBioln-P5yPWEW8VKEADHNA5xMzaz9pU13omjCl42YiCLL8wQU_GIfNiKHKtSjoyqoPE6x-quK6A_LsBjZDCklAnAepa8lpxRDTDfElvOoRxczEDEp8BixOa2cULtrrBM8t3PSmr5g43u_pq9O0Q_mpnTeDnB1INPYH7iPStWLmLq_jCe0_SfgBYMgKLEVgzhj62ssm1ArSC8tZuLIsxOFzTZ5JmFE4YThlnfsHMAuQzrmZWH4FIjthtS6ugKOFvAewk0Srntr_Ghqex920R_yh0rxjKkaKAgkXBgXuh0DC5pWp5r1A3NA3DDqtTY3_PerZH1D9xoL1vxtJpF4D6ekq_X1ZO1MC2BOWq973i9r6-CyMk3ah5e0Ot75axcxERxI6fC3aPmriS6wDTza0"
					},
					"imptrackers": [
						"https://bttrack.com/Pixel/Impression/?data=OuJifVtEKZqw3Hzm6f7yW7Pq5tRZH-Ho7nSrW9J7P-8bWZdSS04-npBvgoMMer7l98G9e8z46g8C4NBnXH1zCQ6G9M9QqAJBrOD3IW1ub8Eradv_NVvWRmOHwXnBiTmLBCMqVWTNkzWjbm7b2yxm2mdXt6Sy7Ioor6ZghyNuCQEzwIi5HLa0bUG69-Wb_jmPZzHh8MQCn7UjgwxA1jBG9e0h_L_5xppNOQeL93poXk6v2llovqgO0Onro_argcZ3BXiAwKzSMw_2vMvKALv31CwUmE6zOCWgv2KTw9CGR-Bp-dhjwys54NhcdCdt88Fkq_jyxqo9gwxgIOv53X68e4gmr_HkGpTvx6MFSIZPIZe0uN1ClHaJzprk81l0H5wbWH8SgEBbhSfXRUElEl2GDY5NIzTuyL3Y0Et5k0we54MkFU-QDv_b8gqabJN4H341EKqAClIyG1eQAPxUQx9zNP6mGMvv0imHjQA_DpiHYkZv5UErOq6JgokH-NHBhdMbmeW_BllEKQ2&type=img&price=${AUCTION_PRICE}",
						"https://bttrack.com/Pixel/DmpPixel/?data=OuJifVtEKZqw3Hzm6f7yW7Pq5tRZH-Ho7nSrW9J7P-8bWZdSS04-npBvgoMMer7l98G9e8z46g8C4NBnXH1zCQ6G9M9QqAJBrOD3IW1ub8Eradv_NVvWRmOHwXnBiTmLBCMqVWTNkzWjbm7b2yxm2mdXt6Sy7Ioor6ZghyNuCQEzwIi5HLa0bUG69-Wb_jmPZzHh8MQCn7UjgwxA1jBG9e0h_L_5xppNOQeL93poXk6v2llovqgO0Onro_argcZ3BXiAwKzSMw_2vMvKALv31CwUmE6zOCWgv2KTw9CGR-Bp-dhjwys54NhcdCdt88Fkq_jyxqo9gwxgIOv53X68e4gmr_HkGpTvx6MFSIZPIZe0uN1ClHaJzprk81l0H5wbWH8SgEBbhSfXRUElEl2GDY5NIzTuyL3Y0Et5k0we54MkFU-QDv_b8gqabJN4H341EKqAClIyG1eQAPxUQx9zNP6mGMvv0imHjQA_DpiHYkZv5UErOq6JgokH-NHBhdMbmeW_BllEKQ2&id=1&type=img",
						"https://bttrack.com/Pixel/DmpPixel/?data=OuJifVtEKZqw3Hzm6f7yW7Pq5tRZH-Ho7nSrW9J7P-8bWZdSS04-npBvgoMMer7l98G9e8z46g8C4NBnXH1zCQ6G9M9QqAJBrOD3IW1ub8Eradv_NVvWRmOHwXnBiTmLBCMqVWTNkzWjbm7b2yxm2mdXt6Sy7Ioor6ZghyNuCQEzwIi5HLa0bUG69-Wb_jmPZzHh8MQCn7UjgwxA1jBG9e0h_L_5xppNOQeL93poXk6v2llovqgO0Onro_argcZ3BXiAwKzSMw_2vMvKALv31CwUmE6zOCWgv2KTw9CGR-Bp-dhjwys54NhcdCdt88Fkq_jyxqo9gwxgIOv53X68e4gmr_HkGpTvx6MFSIZPIZe0uN1ClHaJzprk81l0H5wbWH8SgEBbhSfXRUElEl2GDY5NIzTuyL3Y0Et5k0we54MkFU-QDv_b8gqabJN4H341EKqAClIyG1eQAPxUQx9zNP6mGMvv0imHjQA_DpiHYkZv5UErOq6JgokH-NHBhdMbmeW_BllEKQ2&id=4&type=img",
						"https://bttrack.com/Pixel/DmpPixel/?data=OuJifVtEKZqw3Hzm6f7yW7Pq5tRZH-Ho7nSrW9J7P-8bWZdSS04-npBvgoMMer7l98G9e8z46g8C4NBnXH1zCQ6G9M9QqAJBrOD3IW1ub8Eradv_NVvWRmOHwXnBiTmLBCMqVWTNkzWjbm7b2yxm2mdXt6Sy7Ioor6ZghyNuCQEzwIi5HLa0bUG69-Wb_jmPZzHh8MQCn7UjgwxA1jBG9e0h_L_5xppNOQeL93poXk6v2llovqgO0Onro_argcZ3BXiAwKzSMw_2vMvKALv31CwUmE6zOCWgv2KTw9CGR-Bp-dhjwys54NhcdCdt88Fkq_jyxqo9gwxgIOv53X68e4gmr_HkGpTvx6MFSIZPIZe0uN1ClHaJzprk81l0H5wbWH8SgEBbhSfXRUElEl2GDY5NIzTuyL3Y0Et5k0we54MkFU-QDv_b8gqabJN4H341EKqAClIyG1eQAPxUQx9zNP6mGMvv0imHjQA_DpiHYkZv5UErOq6JgokH-NHBhdMbmeW_BllEKQ2&id=2&type=img",
						"https://bttrack.com/Pixel/DmpPixel/?data=OuJifVtEKZqw3Hzm6f7yW7Pq5tRZH-Ho7nSrW9J7P-8bWZdSS04-npBvgoMMer7l98G9e8z46g8C4NBnXH1zCQ6G9M9QqAJBrOD3IW1ub8Eradv_NVvWRmOHwXnBiTmLBCMqVWTNkzWjbm7b2yxm2mdXt6Sy7Ioor6ZghyNuCQEzwIi5HLa0bUG69-Wb_jmPZzHh8MQCn7UjgwxA1jBG9e0h_L_5xppNOQeL93poXk6v2llovqgO0Onro_argcZ3BXiAwKzSMw_2vMvKALv31CwUmE6zOCWgv2KTw9CGR-Bp-dhjwys54NhcdCdt88Fkq_jyxqo9gwxgIOv53X68e4gmr_HkGpTvx6MFSIZPIZe0uN1ClHaJzprk81l0H5wbWH8SgEBbhSfXRUElEl2GDY5NIzTuyL3Y0Et5k0we54MkFU-QDv_b8gqabJN4H341EKqAClIyG1eQAPxUQx9zNP6mGMvv0imHjQA_DpiHYkZv5UErOq6JgokH-NHBhdMbmeW_BllEKQ2&id=3&type=img",
						"https://bttrack.com/Pixel/DmpPixel/?data=OuJifVtEKZqw3Hzm6f7yW7Pq5tRZH-Ho7nSrW9J7P-8bWZdSS04-npBvgoMMer7l98G9e8z46g8C4NBnXH1zCQ6G9M9QqAJBrOD3IW1ub8Eradv_NVvWRmOHwXnBiTmLBCMqVWTNkzWjbm7b2yxm2mdXt6Sy7Ioor6ZghyNuCQEzwIi5HLa0bUG69-Wb_jmPZzHh8MQCn7UjgwxA1jBG9e0h_L_5xppNOQeL93poXk6v2llovqgO0Onro_argcZ3BXiAwKzSMw_2vMvKALv31CwUmE6zOCWgv2KTw9CGR-Bp-dhjwys54NhcdCdt88Fkq_jyxqo9gwxgIOv53X68e4gmr_HkGpTvx6MFSIZPIZe0uN1ClHaJzprk81l0H5wbWH8SgEBbhSfXRUElEl2GDY5NIzTuyL3Y0Et5k0we54MkFU-QDv_b8gqabJN4H341EKqAClIyG1eQAPxUQx9zNP6mGMvv0imHjQA_DpiHYkZv5UErOq6JgokH-NHBhdMbmeW_BllEKQ2&id=6&type=img"
					],
					"ext": {
						"viewabilitytrackers": [
							"https://bttrack.com/Pixel/Viewed/?data=OuJifVtEKZqw3HzmY94tWrPquodvsIcVXSlpUFGKaJkxmW61DM6eDe6mihfzK5UITbsbkruPceREaNoChGvf1JGUBNCENEppE10LSJmkRR8X4IpZV6BAilNAPiF7sMOCnHV5u1VWgsUUztNHa1tpTqTrIKQvNwslVTqWxI1xHZprFU6iIptn_ItJ2sBinhPqd5Mw_ZZs0_uxiEPYMfCqy2lrend9lA7DnqTgRgSBF6s6kCiMSi20jlyZ5ge8tZ3rf0iCO1krJrvXpBX34h4cnZ_ZVEvED4YX37Bwltaie1ehY0bGb4hKBEs6aWY-S1FQSe6ic18-pJEKVcC2GTSHdv3oBPxIy6mxJfQ_8iuWGee4-YyD7SUSngY2Ry9tWJlxSN0Vd-jv6awEHMFQKXTmK0yeOIO81gbGMECaVr0EoprgytOAtSt5m6Pvtv909kNCE22VFvMoS0A3y0LDEI4ki-81wlo5Ij0zcmuf-EQc0&method=3&percentage=1"
						]
					}
				}
			},
			"adomain": [
				"ford.com"
			],
			"iurl": "https://cdn.bttrack.com/licrop/300/160/200798",
			"cid": "campaign-xyz.123",
			"crid": "d0bcb39723af87c2bb00942afee5710e",
			"cat": [
				"HEALTH",
				"GAMES"
			],
			"attr": [
				6,
				1
			],
			"bundle": "bundle",
			"api": 1,
			"protocol": null,
			"qagmediarating": null,
			"dealid": "1234",
			"h": 50,
			"language": null,
			"w": 320,
			"wratio": 1,
			"hratio": 2,
			"exp": 1,
			"burl": "...",
			"lurl": "...",
			"tactic": "...",
			"ext": {
				"init": 1,
				"curl": "abc",
				"mime": "image/jpeg",
				"cattax": 1,
				"banner": {
					"img": "https://somebuyer.com/creative",
					"link": {
						"url": "https://somebuyer.com/click",
						"urlfb": "https://somebuyer.com",
						"trkr": [
							"2113",
							"21312"
						],
						"ext": {
							"link": "link"
						}
					},
					"ext": {
						"xyz": "111"
					}
				},
				"ext1": {
					"key1": "val1"
				},
				"secure": 1,
				"ext_3": "val_3",
				"ext2": {
					"key2": "val2"
				},
				"ext3": "vak3",
				"ctype": 3,
				"mrating": 1,
				"priv": "232",
				"event": [{
					"type": 1,
					"method": 1,
					"url": "https://somebuyer.com/pixel",
					"api": null,
					"cdata": null,
					"ext": null
				}],
				"lastMod": 1
			}
		}],
		"seat": "XYZ",
		"group": 2,
		"ext": {
			"key": "val",
			"key1": {
				"inside_key": "val"
			}
		}
	}],
	"bidid": "0011223344AABBCC",
	"cur": "USD",
	"customdata": "cust_data",
	"nbr": 1,
	"ext": {
		"ext": {
			"key": "val"
		},
		"cdata": "cdata"
	}
}
```
#### Response 3.0 To 2.x
**Endpoint** - http://host:9090/Converter?type=response&conversiontype=3xTo2x
**Json Body**
```
{
	"openrtb": {
		"ver": "3.0",
		"domainSpec": "3.0",
		"domainVer": "1.0",
		"response": {
			"id": "0123456789ABCDEF",
			"bidid": "0011223344AABBCC",
			"nbr": 1,
			"cur": "USD",
			"cdata": "cdata",
			"seatbid": [{
				"seat": "XYZ",
				"_package": 2,
				"bid": [{
					"id": "yaddayadda",
					"item": "1",
					"deal": "1234",
					"price": 1.5,
					"cid": "campaign-xyz.123",
					"tactic": "...",
					"purl": "http://www.example.com/",
					"burl": "http://www.example1.com/",
					"lurl": "http://www.example2.com/",
					"exp": 1,
					"mid": "123",
					"macro": [{
						"key": "macro1",
						"value": "duck"
					}],
					"media": {
						"ad": {
							"id": "d0bcb39723af87c2bb00942afee5710e",
							"adomain": ["ford.com"],
							"bundle": ["chocho"],
							"iurl": "Abcd.com",
							"cat": ["cat", "dog"],
							"cattax": 1,
							"lang": "english",
							"attr": [6, 1],
							"secure": 1,
							"mrating": 1,
							"init": 1,
							"lastmod": 1,
							"display": {
								"mime": "image/jpeg",
								"api": [1, 2],
								"ctype": 3,
								"w": 320,
								"h": 50,
								"wratio": 1,
								"hratio": 2,
								"priv": "232",
								"adm": null,
								"curl": null,
								"banner": {
									"img": "https:\/\/event.ad.cpe.dotomi.com\/event\/pub_imp?enc=eyJ1c2VyaWQiOiI0NTE3MDQ2OTg4ODA4NTY1OTciLCJwYXJ0bmVyVHhpZCI6IjAiLCJ0eGlkIjoiNzExOTkzNjI5NTM5NjUyMDAyIiwibmV0d29ya1JlcXVlc3RJZCI6IjcxMTk5MzYyOTUzOTY1Mjg2OSIsInNpZCI6MTE4OTc0LCJkaXZpc2lvbklkIjoxLCJ0aWQiOjgsIm1vYmlsZURhdGEiOiIwOyIsImJpZFByaWNlIjowLjA1MDAsInB1YkNvc3QiOjAuMDUwMCwiaXBTdHJpbmciOiI3My44MS4xMjEuMCIsInN1cHBseVR5cGUiOjQsImludGVncmF0aW9uVHlwZSI6NCwibWVkaWF0aW9uVHlwZSI6MzI3LCJwbGFjZW1lbnRJZCI6IjExNzU4NzgiLCJoZWFkZXJCaWQiOjEsImlzRGlyZWN0UHVibGlzaGVyIjowLCJoYXNDb25zZW50IjoxLCJvcGVyYXRpb24iOiJTMlNfSEVBREVSXzI0IiwiaXNDb3JlU2hpZWxkIjowfQ%3D%3D&cb=7234&wbp=${OPENRTB_PRICE}",
									"link": {
										"url": "https://somebuyer.com/click",
										"urlfb": "https://somebuyer.com",
										"trkr": ["2113", "21312"],
										"ext": {
											"link": "link"
										}
									},
									"ext": {
										"xyz": "111"
									}
								},
								"_native": null,
								"event": [{
									"type": 1,
									"method": 1,
									"url": "https://somebuyer.com/pixel",
									"api": [1, 2],
									"cdata": {
										"acct": "123"
									},
									"ext": null
								}],
								"ext": {
									"displayExt": "extext",
									"test": {
										"test1": "test1"
									}
								}
							},
							"video": null,
							"audio": null,
							"audit": null,
							"ext": {
								"ext2": "val2",
								"test2": {
									"test3": "test3"
								}
							}
						}
					},
					"ext": null
				}],
				"ext": {
					"ext3": "val3",
					"test4": {
						"test5": "test5"
					}
				}
			}],
			"ext": {
				"customdata": "customdata",
				"test6": {
					"test7": "test7"
				}
			}
		}
	}
}
```

## Conclusion
This api can be used to test the openrtb-converter and can the code can be used as a guide to use the converter. However, this module does not provide the support to override the individual converters, which is provided by openrtb-converter. 
The user just needs to pass the overriding conversion to converter map to the convert function. Please go through the converter document for details
