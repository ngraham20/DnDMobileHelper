import 'package:flutter/material.dart';
import 'package:flutter_application/character_scroll_list.dart';
import 'package:flutter_application/combat_scroll_list.dart';

class DnDDrawerPage extends StatelessWidget {
  CharacterScrollList _characterScrollList = CharacterScrollList();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("DrawerApp"),
      ),
      drawer: Drawer(
        child: CombatScrollList(),
      ),
      body: _characterScrollList,
    );
  }
}