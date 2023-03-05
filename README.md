# tiktok_login_flutter

Authentication for tiktok using TikTokOpenSDK

In your main function

```
await TiktokLoginFlutter.initializeTiktokLogin("<CientKey>");
```

Then you can get authorization token

```
var code = await TiktokLoginFlutter.authorize("user.info.basic,video.list");
```

Due to changes in Android 11 regarding package visibility, when impementing Tiktok SDK for devices targeting Android 11 and higher, add the following to the Android Manifest file:

```
<queries>
    <package android:name="com.zhiliaoapp.musically" />
    <package android:name="com.ss.android.ugc.trill" />
</queries>
```
