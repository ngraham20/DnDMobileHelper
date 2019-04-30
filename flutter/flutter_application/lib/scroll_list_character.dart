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

  CSLState() {
    _initializeDefaults();
  }

  @override
  void dispose() {
    for (CharacterListItem item in _list_characters)
      {
        item.animationController.dispose();
      }
    super.dispose();
  }

  void _initializeDefaults() {
    _insertCharacter(
      Character("James", 25, 35, 65));

    _insertCharacter(
      Character("Nathaniel", 25, 35, 65)
    );
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
    return Container(
      child: new Column(
          children: <Widget>[
            new Flexible(
                child: new ListView.builder(
                  padding: new EdgeInsets.all(8.0),
                  reverse: true,
                  itemBuilder: (_, int index) => _list_characters[index],
                  itemCount: _list_characters.length,
                )
            ),
          ]
      ),
      decoration: Theme.of(context).platform == TargetPlatform.iOS
          ? new BoxDecoration(
        border: new Border(
          top: new BorderSide(color: Colors.grey[200]),
        ),
      )
          : null,);
  }

}