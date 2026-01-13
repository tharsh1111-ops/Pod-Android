# ANDROID PODCAST SEARCH APP - SETUP INSTRUCTIONS

## Opening in Android Studio

1. Open Android Studio
2. Click "Open" or "File > Open"
3. Navigate to this folder: `PodcastAppAndroid`
4. Click "OK"
5. Wait for Gradle sync to complete (2-5 minutes)

## Running the App

### On Physical Device (Recommended):
1. Enable Developer Options on your Android phone:
   - Go to Settings > About Phone
   - Tap "Build Number" 7 times
2. Enable USB Debugging:
   - Settings > Developer Options > USB Debugging
3. Connect phone to computer via USB
4. Click the green "Run" button (▶️) in Android Studio
5. Select your device from the list

### On Emulator:
1. Click "Device Manager" in Android Studio
2. Create a new virtual device (Pixel 5 or similar)
3. Download a system image (API 31+ recommended)
4. Click the green "Run" button (▶️)
5. Select the emulator

## Features

- **Search Podcasts** - Find podcasts by keyword
- **Trending** - Browse popular podcasts
- **Recent Episodes** - View latest episodes
- **View Episodes** - See all episodes for a podcast
- **Listen** - Tap to open episodes in browser/player

## Architecture

- **MVVM Pattern** - ViewModel, LiveData, Repository
- **Retrofit** - API calls to iTunes
- **Coroutines** - Async operations
- **ViewBinding** - Type-safe view access
- **Coil** - Image loading
- **Material Design 3** - Modern UI

## Project Structure

```
app/src/main/
├── java/com/podcast/search/
│   ├── MainActivity.kt           # Main UI
│   ├── adapter/
│   │   ├── PodcastAdapter.kt     # Podcast list
│   │   └── EpisodeAdapter.kt     # Episode list
│   ├── api/
│   │   ├── iTunesApiService.kt   # API endpoints
│   │   └── RetrofitClient.kt     # HTTP client
│   ├── model/
│   │   ├── Podcast.kt            # Data models
│   │   └── Episode.kt
│   ├── repository/
│   │   └── PodcastRepository.kt  # Data layer
│   └── viewmodel/
│       └── PodcastViewModel.kt   # Business logic
└── res/
    ├── layout/                   # XML layouts
    └── values/                   # Colors, strings, themes
```

## Troubleshooting

### Gradle Sync Failed
- Make sure you have an internet connection
- Click "File > Sync Project with Gradle Files"
- Wait for download to complete

### Can't Find Device
- Make sure USB debugging is enabled
- Try a different USB cable
- Click "Refresh" in device selector

### Build Errors
- Clean project: Build > Clean Project
- Rebuild: Build > Rebuild Project
- Invalidate caches: File > Invalidate Caches / Restart

### App Crashes
- Check Logcat in Android Studio for error messages
- Make sure you have internet connection
- API might be rate-limited (wait a minute)

## Next Steps

- Add favorites functionality using Room database
- Implement audio player with MediaPlayer
- Add download episode feature
- Implement caching for offline access
- Add podcast categories/genres filter

## Requirements

- Android Studio Giraffe or newer
- Minimum Android SDK: 24 (Android 7.0)
- Target SDK: 34 (Android 14)
- Kotlin 1.9+
- Internet connection for API calls

Enjoy your Android Podcast app! 🎙️
