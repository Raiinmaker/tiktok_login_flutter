import 'package:flutter/material.dart';
import 'package:tiktok_login_flutter/tiktok_login_flutter.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await TiktokLoginFlutter.initializeTiktokLogin("INSERT YOUR CLIENT KEY HERE");

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
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('TikTok Login Kit'),
        ),
        body: Center(
          child: TextButton(
              onPressed: () async {
                var code = await TiktokLoginFlutter.authorize(
                    "user.info.basic,video.list,video.upload");
                debugPrint(code);
              },
              child: const Text("Authorize")),
        ),
      ),
    );
  }
}
