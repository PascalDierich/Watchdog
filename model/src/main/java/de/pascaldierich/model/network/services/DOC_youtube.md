# YouTube Api v3 info

### Search for list to get latest published Videos:

#### Authorization:
__No Authorization required__

#### Parameter:
   * part -> 'snippet'
   * channelId -> String
   * publishedAfter -> YYYY-MM-DDThh:mm:ssZ (RFC 3339)
   * eventType -> 'completed'
   * maxResults -> integer (1 <= maxResults <= 50)
   * type -> 'video'
   * order -> 'date'

#### Response:
    {
        "kind": "youtube#searchListResponse",
        "etag": "\"__string__\"",
        "nextPageToken": "__string__",
        "regionCode": "US",
        "pageInfo": {
          "totalResults": __integer__,
          "resultsPerPage": 5
        },
        "items": [
            {
                "kind": "youtube#searchResult",
                "etag": "\"__string__\"",
                "id": {
                    "kind": "youtube#video",
                    "videoId": "__string__"
                },
                "snippet": {
                    "publishedAt": "__date by RFC 3339__",
                    "channelId": "__string__",
                    "title": "__string__",
                    "description": "__string__",

                    "thumbnails": {
                        "default": {
                            "url": "https://i.ytimg.com/vi/__string__.jpg",
                            "width": 120,
                            "height": 90
                        },
                        "medium": {
                            "url": "https://i.ytimg.com/vi/__string__.jpg",
                            "width": 320,
                            "height": 180
                        },
                        "high": {
                            "url": "https://i.ytimg.com/vi/__string__.jpg",
                            "width": 480,
                            "height": 360
                        }
                    },
                    "channelTitle": "__string__",
                    "liveBroadcastContent": "none"
                }
            },

            {...}

        ]
    }

### Get Channel id with list:

#### Authorization:
__No Authorization required__

#### Parameter:
   * part -> 'id'
   * forUsername -> String
   * maxResults -> integer (1 <= maxResults <= 50)

#### Response:
    {
        "kind": "youtube#channelListResponse",
        "etag": "\"__string__\"",
        "pageInfo": {
            "totalResults": __integer__,
            "resultsPerPage": 5
        },
        "items": [
            {
                "kind": "youtube#channel",
                "etag": "\"__string__\"",
                "id": "__string__"
            }
        ]
    }
