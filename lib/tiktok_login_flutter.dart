import 'dart:async';
import 'package:flutter/services.dart';
//
class TiktokLoginFlutter {
  static const MethodChannel _channel = MethodChannel('tiktok_login_flutter');
  // final _eventChannel = const EventChannel('tiktok_login_flutter_event');

  static Future<void> initializeTiktokLogin(String clientKey) async {
   var result = await _channel.invokeMethod('initializeTiktokLogin', clientKey);
  }
  static Future<String?> authorize() async {
    return  await _channel.invokeMethod('authorize');
  }

  // Stream get onResponse => _eventChannel
  //     .receiveBroadcastStream()
  //     .distinct()
  //     .map((dynamic event) => event as String);
}
