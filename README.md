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
