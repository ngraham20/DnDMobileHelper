import 'package:flutter/material.dart';
import 'package:flutter_application/character_scroll_list.dart';
import'tab_page_main.dart';

class DnDApplication extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "dnd_application",
      home: DnDTabbedPage()
    );
  }
}