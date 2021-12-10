package com.raiinmaker.tiktok_login_flutter

import androidx.annotation.NonNull
import com.bytedance.sdk.open.tiktok.TikTokOpenApiFactory
import com.bytedance.sdk.open.tiktok.TikTokOpenConfig

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** TiktokLoginFlutterPlugin */
class TiktokLoginFlutterPlugin: FlutterPlugin, MethodCallHandler {


  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "tiktok_login_flutter")
    channel.setMethodCallHandler(this)
  }



  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    when (call.method) {
      "initializeTiktokLogin" -> initializeTiktokLogin(call = call, result = result)
      else -> result.notImplemented()
    }
  }

  private fun initializeTiktokLogin(call: MethodCall, result: Result) {
    try {
      val clientKey =  call.arguments as String
      val tiktokOpenConfig = TikTokOpenConfig(clientKey)
      TikTokOpenApiFactory.init(tiktokOpenConfig)
      result.success(true)

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


}
