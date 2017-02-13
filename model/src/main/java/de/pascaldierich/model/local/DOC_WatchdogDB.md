# Watchdog DB

### Tables:

* Favorites
* NewsFeed
* Observables
* Sites

### Table Observables

|  _ID : Integer auto-generated |  displayName : String | thumbnail : blob |
|-------------------------------|-----------------------|------------------|
| 1                             | Pascal Dierich        | [Bitmap]         |
| 2                             | Hans Peter            | [Bitmap]         |
| 3                             | Bill Gates            | [Bitmap]         |

### Table Sites

|  _ID : Integer |  site : String | key : String     |
|----------------|----------------|------------------|
| 1              | YouTube        | /kvndfkjvndfever |
| 2              | YouTube        | /nlkdsnvokdsv    |
| 1              | Plus           | /nejkvndfkv      |

### Table Favorites

|  _ID : Integer auto-generated | userId : Integer  | thumbnailUrl : String | description : String | title : String | postId : String | timeSaved : timestamp auto-generated | site : String    | timeDownloaded : timestamp auto-generated |
|-------------------------------|-------------------|-----------------------|----------------------|----------------|-----------------|--------------------------------------|------------------|-------------------------------------------|
| 1                             | 1                 | xyz.jpg               | [description]        | [title]        | /[uniquePostId] | [timestamp]                          | YouTube          | [timestamp]                               |
| 2                             | 2                 | yzx.jpg               | [description]        | [title]        | /[uniquePostId] | [timestamp]                          | Plus             | [timestamp]                               |
| 3                             | 1                 | zxy.jpg               | [description]        | [title]        | /[uniquePostId] | [timestamp]                          | YouTube          | [timestamp]                               |

### Table NewsFeed

|  _ID : Integer auto-generated | userId : Integer  | thumbnailUrl : String | description : String | title : String | postId : String | site : String    | timeDownloaded : timestamp auto-generated |
|-------------------------------|-------------------|-----------------------|----------------------|----------------|-----------------|------------------|-------------------------------------------|
| 1                             | 1                 | xyz.jpg               | [description]        | [title]        | /[uniquePostId] | YouTube          | [timestamp]                               |
| 2                             | 2                 | yzx.jpg               | [description]        | [title]        | /[uniquePostId] | Plus             | [timestamp]                               |
| 3                             | 1                 | zxy.jpg               | [description]        | [title]        | /[uniquePostId] | YouTube          | [timestamp]                               |