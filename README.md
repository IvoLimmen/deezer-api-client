# README

## Description

Java wrapper for deezer api - http://developers.deezer.com/api/ supports
all GET methods which don't need OAuth authentication

### Code example

```java
final DeezerClient deezerClient = new DeezerClient(new JdkHttpResourceConnection());

final ArtistId daftPunkArtistId = new ArtistId(27L);

// get Daft Punk - http://api.deezer.com/artist/27
final Artist artist = deezerClient.get(daftPunkArtistId);

// get Daft Punk albums - http://api.deezer.com/artist/27/albums
final Albums daftPunkAlbums = deezerClient.getAlbums(daftPunkArtistId);

// get Daft Punk fans - http://api.deezer.com/artist/27/fans
final Fans daftPunkFans = deezerClient.getFans(daftPunkArtistId);
```