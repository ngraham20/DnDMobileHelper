// this widget contains the scrolling list of characters
import 'package:flutter/material.dart';
import 'package:flutter_application/character_list_item.dart';
import 'character.dart';

class CharacterScrollList extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => CSLState();

}

class CSLState extends State<CharacterScrollList> with TickerProviderStateMixin {
  List<CharacterListItem> listCharacters = <CharacterListItem>[];
  AnimationController _animationController;


  @override
  void initState() {
    super.initState();
    _animationController = AnimationController(
        duration: Duration(milliseconds: 700),
        vsync: this
    );

    _initializeDefaults();
  }

  @override
  void dispose() {
    for (CharacterListItem item in listCharacters)
      {
        item.animationController.dispose();
      }
    super.dispose();
  }

  void _initializeDefaults() {
    List<Character> items = <Character>[
      Character("James", 25, 35, 65),
      Character("Nathaniel", 25, 35, 65)
    ];

    _setCharacters(items);
  }

  void _setCharacters(List<Character> characters) {
    List<CharacterListItem> items = <CharacterListItem>[];
    for (var character in characters) {
      CharacterListItem item = CharacterListItem(
        character: character,
        animationController: _animationController
      );

      items.add(item);
    }
    setState(() {listCharacters = items;});
    _animationController.forward();
  }

  void _insertCharacter(Character character) {
    CharacterListItem item = CharacterListItem(
      character: character,
      animationController: _animationController
    );
    setState(() {
      listCharacters.insert(0, item);
    });
    item.animationController.forward();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: new Column(
          children: <Widget>[
            new Flexible(
                child: new ListView.builder(
                  padding: new EdgeInsets.all(8.0),
                  itemBuilder: (_, int index) => listCharacters[index],
                  itemCount: listCharacters.length,
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