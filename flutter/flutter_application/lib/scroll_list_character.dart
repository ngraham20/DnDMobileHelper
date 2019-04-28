// this widget contains the scrolling list of characters
import 'package:flutter/material.dart';
import 'package:flutter_application/character_list_item.dart';
import 'character.dart';

class CharacterScrollList extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => CSLState();

}

class CSLState extends State<CharacterScrollList> with TickerProviderStateMixin {
  final List<CharacterListItem> _list_characters = <CharacterListItem>[];

  @override
  void dispose() {
    for (CharacterListItem item in _list_characters)
      {
        item.animationController.dispose();
      }
    super.dispose();
  }

  void _insertCharacter(Character character) {
    CharacterListItem newItem = CharacterListItem(
      character: character,
      animationController: AnimationController(
        duration: Duration(milliseconds: 700),
        vsync: this
      ),
    );
    setState(() {
      _list_characters.insert(0, newItem);
    });
    newItem.animationController.forward();
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return null;
  }

}