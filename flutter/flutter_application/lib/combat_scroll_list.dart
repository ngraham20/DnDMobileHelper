import 'package:flutter/material.dart';
import 'combat_list_item.dart';
import 'character.dart';

class CombatScrollList extends StatefulWidget {

  @override
  State<StatefulWidget> createState() => CSLState();

}

class CSLState extends State<CombatScrollList> with TickerProviderStateMixin {
  List<CombatListItem> _listCombats = <CombatListItem>[];


  @override
  void initState() {
    super.initState();
    _initializeDefaults();
  }


  @override
  void dispose() {
    for(CombatListItem item in _listCombats) {
      item.animationController.dispose();
    }
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: new Column(
          children: <Widget>[
            new Flexible(
                child: new ListView.builder(
                  padding: new EdgeInsets.all(8.0),
                  itemBuilder: (_, int index) => _listCombats[index],
                  itemCount: _listCombats.length,
                )
            ),
          ]
      ),
    );
  }

  void _initializeDefaults() {
    List<Character> items = <Character>[
      Character("James", 25, 35, 65),
      Character("Nathaniel", 25, 35, 65)
    ];

    _setCombats(items);
  }

  void _setCombats(List<Character> Combats) {
    List<CombatListItem> items = <CombatListItem>[];
    for (var Combat in Combats) {
      CombatListItem item = CombatListItem(
          character: Combat,
          animationController: AnimationController(
              duration: Duration(milliseconds: 700),
              vsync: this
          )
      );

      items.add(item);
      item.animationController.forward();
    }
    setState(() {_listCombats = items;});

  }

}