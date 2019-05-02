import 'package:flutter/material.dart';
import 'combat_list_item.dart';
import 'combat.dart';

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
    return Scaffold(
      appBar: AppBar(
        actions: <Widget>[
          IconButton(
            icon: Icon(Icons.add, color: Colors.white),
          )
        ],
      ),
      body: Container(
        child: new Column(
            children: <Widget>[
              Flexible(
                  child: new ListView.builder(
                    padding: new EdgeInsets.all(8.0),
                    itemBuilder: (_, int index) => _listCombats[index],
                    itemCount: _listCombats.length,
                  )
              ),
              Container(
                width: double.infinity,
                height: 55.0,
                color: Theme.of(context).primaryColor,
                child: Container(
                  margin: EdgeInsets.all(5.0),
                  child: RaisedButton(
                      color: Colors.blueGrey[700],
                      elevation: 4.0,
                      splashColor: Theme.of(context).buttonColor,
                      onPressed: () {},
                      child: Text(
                        "Your Characters",
                        style: TextStyle(
                            color: Colors.white,
                            fontSize: 20.0
                        ),
                      )
                  ),
                ),
              ),
            ]
        ),
      ),
    );
  }

  void _initializeDefaults() {
    List<Combat> items = <Combat>[
      Combat("Combat1"),
      Combat("Combat2")
    ];

    _setCombats(items);
  }

  void _setCombats(List<Combat> combats) {
    List<CombatListItem> items = <CombatListItem>[];
    for (var Combat in combats) {
      CombatListItem item = CombatListItem(
          combat: Combat,
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