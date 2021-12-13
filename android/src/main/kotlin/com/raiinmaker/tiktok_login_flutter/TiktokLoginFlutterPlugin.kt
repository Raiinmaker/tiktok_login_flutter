package com.raiinmaker.tiktok_login_flutter

import android.app.Activity
import androidx.annotation.NonNull
import com.bytedance.sdk.open.tiktok.TikTokOpenApiFactory
import com.bytedance.sdk.open.tiktok.TikTokOpenConfig
import com.bytedance.sdk.open.tiktok.api.TikTokOpenApi
import com.bytedance.sdk.open.tiktok.authorize.model.Authorization
import com.raiinmaker.tiktok_login_flutter.tiktokapi.TikTokEntryActivity
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import android.util.Log



/** TiktokLoginFlutterPlugin */
class TiktokLoginFlutterPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {


  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private lateinit var eventChannel : EventChannel
  private var activity: Activity? = null



  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "tiktok_login_flutter")
    eventChannel = EventChannel(flutterPluginBinding.binaryMessenger,
            "tiktok_login_flutter_event")
    channel.setMethodCallHandler(this)
    eventChannel.setStreamHandler(TikTokEntryActivity.instance.messageStreamHandler)

    Log.i("INFO", "Initialized plugin")
  }



  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result:MethodChannel.Result) {
    when (call.method) {
      "initializeTiktokLogin" -> initializeTiktokLogin(call = call, result = result)
      "authorize" -> authorize(call = call, result = result)
      else -> result.notImplemented()
    }
  }

  private fun initializeTiktokLogin(call: MethodCall, result:MethodChannel.Result) {
    try {
      val clientKey =  call.arguments as String
      val tiktokOpenConfig = TikTokOpenConfig(clientKey)
      TikTokOpenApiFactory.init(tiktokOpenConfig)
      Log.i("INFO", "Initialized plugin")
      result.success(true)

    } catch (e: Exception) {
      result.error(
              "FAILED_TO_INITIALIZE",
              e.localizedMessage,
              null
      )

    }
   }

    private fun authorize(call: MethodCall, result: MethodChannel.Result) {


      try {
        val tiktokOpenApi: TikTokOpenApi = TikTokOpenApiFactory.create(activity)
        val request = Authorization.Request()
        request.scope = "user.info.basic"
        request.state = "xxx"
        request.callerLocalEntry = "com.raiinmaker.tiktok_login_flutter.tiktokapi.TikTokEntryActivity"
        Log.i("INFO", "Initialized request")
        tiktokOpenApi.authorize(request);
        result.success(null)
      } catch (e: Exception) {
        result.error(
                "FAILED_TO_INITIALIZE",
                e.localizedMessage,
                null
        )

      }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  override fun onDetachedFromActivity() {
    activity = null
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    activity = binding.activity
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    activity = binding.activity

  }

  override fun onDetachedFromActivityForConfigChanges() {
    activity = null
  }

}
