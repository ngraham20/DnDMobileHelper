import 'package:flutter/material.dart';
import 'package:flutter_application/character_scroll_list.dart';

class DnDDrawerPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("DrawerApp"),
      ),
      drawer: Drawer(),
      body: CharacterScrollList(),
    );
  }

}