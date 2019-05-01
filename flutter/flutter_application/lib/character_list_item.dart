 // this widget describes the list item of a character

import 'package:flutter/material.dart';
import 'character.dart';
import 'constants.dart';

class CharacterListItem extends StatelessWidget {
  final Character character;
  final AnimationController animationController;

  final List<String> choices = <String>[
    Constants.DELETE
  ];
  CharacterListItem({this.character, this.animationController});

  @override
  Widget build(BuildContext context) {
    return new SizeTransition(
        sizeFactor: new CurvedAnimation(
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
                  margin: const EdgeInsets.only(right: 8.0),
                  child: Container(
                    decoration: BoxDecoration(
                      color: Theme.of(context).accentColor,
                      borderRadius: BorderRadius.only(
                        topRight: const Radius.circular(5.0),
                        topLeft: const Radius.circular(5.0),
                        bottomRight: const Radius.circular(5.0),
                        bottomLeft: const Radius.circular(5.0)
                      )
                    ),
                    child: Text(
                      character.ac.toString(),
                      style: TextStyle(fontSize: 35.0),
                    ),
                  ),
                ),
                new Container(
                  margin: const EdgeInsets.only(right: 8.0),
                  child: Container(
                    decoration: BoxDecoration(
                        color: Theme.of(context).accentColor,
                        borderRadius: BorderRadius.all(const Radius.circular(5.0))
                    ),
                    child: Text(
                      character.hp.toString(),
                      style: TextStyle(fontSize: 35.0),
                    ),
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
                        decoration: BoxDecoration(
                            color: Theme.of(context).accentColor,
                            borderRadius: BorderRadius.only(
                                topRight: const Radius.circular(5.0),
                                topLeft: const Radius.circular(5.0),
                                bottomRight: const Radius.circular(5.0),
                                bottomLeft: const Radius.circular(5.0)
                            )
                        ),
                          child: Container(
                            margin: EdgeInsets.all(2.0),
                            child: Text(
                              character.init.toString(),
                              style: TextStyle(fontSize: 14.0),
                            ),
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