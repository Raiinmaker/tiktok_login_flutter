import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';

import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:tiktok_login_flutter/tiktok_login_flutter.dart';
import 'package:wechat_assets_picker/wechat_assets_picker.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await TiktokLoginFlutter.initializeTiktokLogin("awtemju6r7rrj7z1");

  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(home: Home());
  }
}

class Home extends StatelessWidget {
  const Home({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('TikTok Login Kit'),
      ),
      body: Center(
        child: Row(
          children: [
            ElevatedButton(
                onPressed: () async {
                  final code = await TiktokLoginFlutter.authorize(
                      "user.info.basic,video.list");
                },
                child: const Text("Authorize")),
            ElevatedButton(
                onPressed: () async {
                  final List<AssetEntity>? result =
                      await AssetPicker.pickAssets(context);

                  if (result != null) {
                    final file = await result.single.id;
                    print(file);
                    final bool success = await TiktokLoginFlutter.share(file);
                  }
                },
                child: const Text("Share")),
          ],
        ),
      ),
    );
  }
}
