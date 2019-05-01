import 'package:flutter/material.dart';
import 'package:flutter_application/drawer_page_main.dart';
import 'themes.dart';

class DnDApplication extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "dnd_application",
      home: DnDDrawerPage(),
      theme: Theme.of(context).platform == TargetPlatform.iOS
        ? dndIOSTheme
        : dndAndroidTheme
    );
  }
}