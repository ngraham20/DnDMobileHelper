import 'package:flutter/material.dart';
import 'package:flutter_application/character_scroll_list.dart';
import 'character.dart';

class DnDDrawerPage extends StatelessWidget {
  CharacterScrollList _characterScrollList = CharacterScrollList();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("DrawerApp"),
      ),
      drawer: Drawer(),
      body: _characterScrollList,
    );
  }

  Character _createNewCharacter() {
    return Character("Default", 1, 1, 1);
  }

}