import 'package:flutter/material.dart';
import 'package:tiktok_login_flutter/tiktok_login_flutter.dart';

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
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body:  Center(
          child:  TextButton(onPressed: () async {
             await TiktokLoginFlutter.authorize();

             // TiktokLoginFlutter().onResponse.listen((event) {
             //   print(event);
             // });

          }, child: const Text("Authorize")),
        ),
      ),
    );
  }
}
