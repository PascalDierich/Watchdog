# Feed DB

## Tables:

* Favorites
* NewsFeed
* Observables
* Sites

### Table Observables

| displayName : String | Id : auto-generated Key | image : blob |
|----------------------|-------------------------|--------------|
| Pascal Dierich       | 1                       | [Bitmap]     |
| Hans Peter           | 2                       | [Bitmap]     |

### Table Sites:

| Id : joined from Observables |  Site : String | Key : String     |
|------------------------------|----------------|------------------|
| 1 ( -> Pascal Dierich)       | YouTube        | /kvndfkjvndfever |
| 2 ( -> Hans Peter)           | YouTube        | /nlkdsnvokdsv    |
| 1 ( -> Pascal Dierich)       | Plus           | /nejkvndfkv      |

### Table Favorites:

### Table NewsFeed