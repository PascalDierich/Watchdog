# Google+  Api info

### Search for list of users:

#### Authorization:
__No Authorization required__

#### Parameter:
 * query -> string
 * maxResults -> integer (1 <= maxResults <= 50)

#### Response:
    {
        "kind": "plus#peopleFeed",
        "etag": "\"__string__\"",
        "selfLink": "__string__",
        "title": "Google+ People Search Results",
        "nextPageToken": "__string__",
        "items": [
            {
                "kind": "plus#person",
                "etag": "\"__string__\"",
                "objectType": "person",
                "id": "__string__",
                "displayName": "__string__",
                "url": "https://plus.google.com/__string__",
                "image": {
                    "url": "__string__"
                }
            },
        ]
    }

### Search for list of Activities:

#### Authorization:
__No Authorization required__

#### Parameter:
 * userId -> string
 * collection -> 'public'
 * maxResults -> integer (1 <= maxResults <= 100)

#### Response:
    {
        "kind": "plus#activityFeed",
        "etag": etag,
        "nextPageToken": string,
        "selfLink": string,
        "nextLink": string,
        "title": string,
        "updated": datetime,
        "id": string,
        "items": [
             "kind": "plus#activity",
             "etag": etag,
             "title": string,
             "published": datetime,
             "updated": datetime,
             "id": string,
             "url": string,
             "actor": {
                 "id": string,
                 "displayName": string,
                 "name": {
                      "familyName": string,
                      "givenName": string
                 },
                 "url": string,
                 "image": {
                     "url": string
                 }
             },
             "verb": string,
             "object": {
                "objectType": string,
                "id": string,
                "actor": {
                    "id": string,
                    "displayName": string,
                    "url": string,
                    "image": {
                         "url": string
                    }
                },
                "content": string,
                "originalContent": string,
                "url": string,
                "replies": {
                     "totalItems": unsigned integer,
                     "selfLink": string
                },
                "plusoners": {
                     "totalItems": unsigned integer,
                     "selfLink": string
                },
                "resharers": {
                     "totalItems": unsigned integer,
                     "selfLink": string
                },
                "attachments": [
                     {
                        "objectType": string,
                        "displayName": string,
                        "id": string,
                        "content": string,
                        "url": string,
                        "image": {
                            "url": string,
                            "type": string,
                            "height": unsigned integer,
                            "width": unsigned integer
                        },
                        "fullImage": {
                            "url": string,
                            "type": string,
                            "height": unsigned integer,
                            "width": unsigned integer
                        },
                        "embed": {
                            "url": string,
                            "type": string
                        },
                        "thumbnails": [
                            {
                                "url": string,
                                "description": string,
                                "image": {
                                       "url": string,
                                       "type": string,
                                       "height": unsigned integer,
                                       "width": unsigned integer
                                }
                            }
                        ]
                     }
                 ]
             },
             {...}
        ]
    }

