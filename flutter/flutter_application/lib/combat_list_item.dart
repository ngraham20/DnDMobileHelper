 // this widget describes the list item of a character

import 'package:flutter/material.dart';
import 'character.dart';
import 'constants.dart';

class CombatListItem extends StatelessWidget {
  final Character character;
  final AnimationController animationController;

  final List<String> choices = <String>[
    Constants.DELETE
  ];
  CombatListItem({this.character, this.animationController});

  @override
  Widget build(BuildContext context) {
    return SizeTransition(
        sizeFactor: CurvedAnimation(
            parent: animationController, curve: Curves.easeOut),
        child: new Container(
          decoration: BoxDecoration(
            border: Border.all(
                color: Colors.grey[400],
            ),
          ),
          //color: Colors.pink,
          margin: const EdgeInsets.symmetric(vertical: 5.0),
          child: Container(
            margin: const EdgeInsets.all(8.0),
            child: new Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                new Container(
                  margin: const EdgeInsets.only(right: 16.0, left: 8.0),
                  child: Column(
                    children: <Widget>[
                      Container(
                        decoration: BoxDecoration(
                          borderRadius: BorderRadius.all(const Radius.circular(5.0))
                        ),
                        child: Text(
                          character.ac.toString(),
                          style: TextStyle(fontSize: 35.0),
                        ),
                      ),
                      Container(
                        child: Text(
                          "AC",
                          style: TextStyle(fontSize: 13.0),
                        ),
                      )
                    ],
                  ),
                ),
                new Container(
                  child: Column(
                    children: <Widget>[
                      Container(
                        decoration: BoxDecoration(
                            borderRadius: BorderRadius.all(const Radius.circular(5.0))
                        ),
                        child: Text(
                          character.hp.toString(),
                          style: TextStyle(fontSize: 35.0),
                        ),
                      ),
                      Container(
                        child: Text(
                          "HP",
                          style: TextStyle(fontSize: 13.0),
                        ),
                      )
                    ],
                  ),
                ),
                new Expanded(
                  child: Container(
                    margin: EdgeInsets.only(right: 4.0),
                    //color: Colors.amber,
                    child: Center(
                      child: Text(
                        character.name,
                        style: TextStyle(fontSize: 25.0),
                      ),
                    ),
                  )
                ),
                Container(
                  //color: Colors.limeAccent,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: <Widget>[
                        Container(
                          child: PopupMenuButton<String>(
                            onSelected: _choiceAction,
                            itemBuilder: (BuildContext context) {
                              return choices.map((String choice) {
                                return PopupMenuItem<String>(
                                  value: choice,
                                  child: Text(choice)
                                );
                              }).toList();
                            },
                          ),
                        ),
                      Container(
                          child: Row(
                            children: <Widget>[
                              Text("Init:"),
                              Container(
                                margin: EdgeInsets.all(2.0),
                                child: Text(
                                  character.init.toString(),
                                  style: TextStyle(
                                      fontSize: 14.0,
                                      fontWeight: FontWeight.bold),
                                ),
                              ),
                            ],
                          ),
                        ),
                    ],
                  ),
                )
              ],
            ),
          ),
        )
    );
  }

  void _choiceAction(String choice) {
    print("WORKING");
  }

}