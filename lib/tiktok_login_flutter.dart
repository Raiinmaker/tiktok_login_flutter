import 'dart:async';
import 'dart:typed_data';
import 'package:flutter/services.dart';

class TiktokLoginFlutter {
  static const MethodChannel _channel = MethodChannel('tiktok_login_flutter');

  static Future<bool> initializeTiktokLogin(String clientKey) async {
    return await _channel.invokeMethod('initializeTiktokLogin', clientKey);
  }

  static Future<String> authorize(String scope) async {
    return await _channel.invokeMethod('authorize', {"scope": scope});
  }

  static Future<bool> share(String file) async {
    return await _channel.invokeMethod('share', {"file": file});
  }
}
