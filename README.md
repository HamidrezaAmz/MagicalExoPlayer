[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)
[![](https://jitpack.io/v/HamidrezaAmz/MagicalExoPlayer.svg)](https://jitpack.io/#HamidrezaAmz/MagicalExoPlayer)

# MagicalExoPlayer
The Easiest Way To Play Video Using ExoPlayer In Your Android Application. Add Dependencies Into Your Gadle File, Sync Your Project And Then Just Pass Your Url Or Local Video Address to The Player.

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
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

Step 2. Add the dependency

```gradle
dependencies {
    implementation 'com.github.HamidrezaAmz:MagicalExoPlayer:1.0.1'
}
```


### Here we go for implementation

Add xml of custom view into your parent view
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

## Custom Attributes
| Command | Description |
| --- | --- |
| `andexo_resize_mode` | Type Of Video Player Size, you can pass **Fill**,**Fit**,**Zoom** |
| `andexo_full_screen` | Show FullScreen Toggle Button, you can Pass **True**,**False** |

## Other Libraries

* [ExoPlayer](https://github.com/google/ExoPlayer) - Google Player


## Author

* **Hamidreza amoozadeh** - *Android DEV* - [github](https://github.com/HamidrezaAmz)
