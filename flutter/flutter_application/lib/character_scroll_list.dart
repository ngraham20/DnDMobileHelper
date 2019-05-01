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


  @override
  void initState() {
    super.initState();

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
        animationController: AnimationController(
            duration: Duration(milliseconds: 700),
            vsync: this
        )
      );

      items.add(item);
      item.animationController.forward();
    }
    setState(() {listCharacters = items;});

  }

  void _insertCharacter(Character character) {
    CharacterListItem item = new CharacterListItem(
      character: character,
      animationController: AnimationController(
          duration: Duration(milliseconds: 700),
          vsync: this
      )
    );
    setState(() {
      listCharacters.insert(0, item);
    });
    item.animationController.forward();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
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
            : null,),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
      floatingActionButton: Container(
        margin: const EdgeInsets.symmetric(vertical: 24.0),
        child: FloatingActionButton(
          onPressed: () {_insertCharacter(Character("DEFAULT", 1, 1, 1));},
          child: Icon(Icons.add, color: Colors.white,),
        ),
      ),
    );
  }
}