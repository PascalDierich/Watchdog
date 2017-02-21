# Watchdog DB

### Tables:

* Observables
* Sites
* Favorites
* NewsFeed

### Table Observables

This table saves the DisplayName to use in App and create an
unique auto-incremented Key for each Observable. <br>
An Image can be added too but isn't necessary.
<p>

|  userId : Integer auto-generated |  displayName : String | thumbnail : blob |
|----------------------------------|-----------------------|------------------|
| 1                                | Pascal Dierich        | [Bitmap]         |
| 2                                | Hans Peter            | [Bitmap]         |
| 3                                | Bill Gates            | [Bitmap]         |

### Table Sites

This table will save the Site specific UserId's with the equivalent sites for the
API Services. <br>
The userId Column will hold the Observables ID.
<p>

|  userId : Integer |  site : String | key : String     |
|-------------------|----------------|------------------|
| 1                 | YouTube        | /kvndfkjvndfever |
| 2                 | YouTube        | /nlkdsnvokdsv    |
| 1                 | Plus           | /nejkvndfkv      |

### Table Favorites

This table saves the favorites Posts locally. <br>
The userId is equivalent to the Observables ID column.
<p>

|  _ID : Integer auto-generated | userId : Integer  | thumbnailUrl : String | description : String | title : String | postId : String | site : String    | timeSaved : timestamp auto-generated |
|-------------------------------|-------------------|-----------------------|----------------------|----------------|-----------------|------------------|--------------------------------------|
| 1                             | 1                 | xyz.jpg               | [description]        | [title]        | /[uniquePostId] | YouTube          | [timestamp]                          |
| 2                             | 2                 | yzx.jpg               | [description]        | [title]        | /[uniquePostId] | Plus             | [timestamp]                          |
| 3                             | 1                 | zxy.jpg               | [description]        | [title]        | /[uniquePostId] | YouTube          | [timestamp]                          |

### Table NewsFeed

This table holds the new posts which got downloaded. <br>
The userId is equivalent to the Observables ID column.
<p>

|  _ID : Integer auto-generated | userId : Integer  | thumbnailUrl : String | description : String | title : String | postId : String | site : String    | timeDownloaded : timestamp auto-generated |
|-------------------------------|-------------------|-----------------------|----------------------|----------------|-----------------|------------------|-------------------------------------------|
| 1                             | 1                 | xyz.jpg               | [description]        | [title]        | /[uniquePostId] | YouTube          | [timestamp]                               |
| 2                             | 2                 | yzx.jpg               | [description]        | [title]        | /[uniquePostId] | Plus             | [timestamp]                               |
| 3                             | 1                 | zxy.jpg               | [description]        | [title]        | /[uniquePostId] | YouTube          | [timestamp]                               |