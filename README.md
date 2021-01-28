![GitHub](https://img.shields.io/github/license/hamidrezaamz/MagicalExoPlayer)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)
[![](https://jitpack.io/v/HamidrezaAmz/MagicalExoPlayer.svg)](https://jitpack.io/#HamidrezaAmz/MagicalExoPlayer)

# MagicalExoPlayer
The Easiest Way To Play Video Using ExoPlayer In Your Android Application. Add Dependencies Into Your Gadle File, Sync Your Project And Then Just Pass Your Url Or Local Video Address To The Player. MagicalExoPlayer Support **MP4**, **HLS**, **DASH**  And **MP3**. 

![mock_up_and_exo_player_2](https://user-images.githubusercontent.com/13493645/63092374-cbef1400-bf76-11e9-8727-734f036692ca.jpg)

## Getting Started

These instructions will help you to use this library inside your projects

### Prerequisites

This library was built with **androidX**, so you should migrate into androidX to use this library with out any problem. For migration you can use [Migrating to AndroidX](https://developer.android.com/jetpack/androidx/migrate)

### Installing

Step 1. Add the JitPack repository to your build file,
Add it in your root build.gradle at the end of repositories:

```gradle
allprojects {
        repositories {
            maven { url 'https://jitpack.io' }
        }
    }
```

Step 2. Add the dependency

```gradle
dependencies {
    implementation 'com.github.HamidrezaAmz:MagicalExoPlayer:1.0.16'
}
```


### Here we go for implementation

Add player view into your XML
```xml
<com.potyvideo.library.AndExoPlayerView
        android:id="@+id/andExoPlayerView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

Refrence to custom-view inside your activity or fragment (I use [butterknife](https://github.com/JakeWharton/butterknife/)), Or you can use **findViewById()**
```java
 @BindView(R.id.andExoPlayerView)
 AndExoPlayerView andExoPlayerView;
```
Or
```java
 AndExoPlayerView andExoPlayerView = findViewById(R.id.andExoPlayerView);
```

## Implementation Example
```java
 andExoPlayerView.setSource("URL OR FILE ADDRESS");
```

## Custom Attributes
| Command | Description |
| --- | --- |
| `andexo_resize_mode` | Type Of Video Player Size, you can pass **Fill**,**Fit**,**Zoom** |
| `andexo_full_screen` | Show FullScreen Toggle Button, you can Pass **True**,**False** |
| `andexo_play_when_ready` | Player Start Playing On Stream Is Ready **True**,**False** |
| `andexo_aspect_ratio` | In Order To Get The Desired Playerr Size, You Can Pass The Aspect Ratios You Need **ASPECT_1_1**, **ASPECT_16_9**, **ASPECT_4_3**, **ASPECT_MATCH**, **ASPECT_MP3** |
| `andexo_show_controller` | Show Or Hide Player Controller **True**,**False** |
| `andexo_loop` | Play video with loop modes **Infinite**,**Finite** |


## XML With Custom Attrs.
```xml
<com.potyvideo.library.AndExoPlayerView
        android:id="@+id/andExoPlayerView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:andexo_aspect_ratio="aspect_16_9"
        app:andexo_full_screen="true"
        app:andexo_play_when_ready="true"
        app:andexo_show_controller="true"
        app:andexo_resize_mode="Fit" />
```


## TIP
* If you wan to support full screen, please add this config into your activity in manifest.xml
```xml
android:configChanges="keyboard|keyboardHidden|orientation|screenSize|screenLayout|smallestScreenSize|uiMode"
```

For example your activity with be something like this
```xml
<activity
      android:name=".MainActivity"
      android:configChanges="keyboard|keyboardHidden|orientation|screenSize|screenLayout|smallestScreenSize|uiMode">
      <intent-filter>
          <action android:name="android.intent.action.MAIN" />

          <category android:name="android.intent.category.LAUNCHER" />
      </intent-filter>
</activity>
```


* If you want to pass custom headers over your stream url, you can just pass them as a hashmap like this:
```java
  HashMap<String , String> extraHeaders = new HashMap<>();
  extraHeaders.put("foo","bar");
  andExoPlayerView.setSource("STREAM_URL", extraHeaders);
```


## Other Libraries

* [ExoPlayer](https://github.com/google/ExoPlayer) - Google Player - Version 2.10.3


***

## :heart: Support My Projects 
However, if you get some profit from this or just want to encourage me to continue creating stuff, there are few ways you can do it

* Starring and sharing the projects you like 🚀
* Bitcoin—You can send me bitcoins at this address (or scanning the code below):

`16cAkXGQRjx6xLH4CCN5wyWaVDQH2pzyVu`
