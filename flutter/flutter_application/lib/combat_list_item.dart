 // this widget describes the list item of a character

import 'package:flutter/material.dart';
import 'combat.dart';
import 'constants.dart';

class CombatListItem extends StatelessWidget {
  final Combat combat;
  final AnimationController animationController;

  final List<String> choices = <String>[
    Constants.DELETE
  ];
  CombatListItem({this.combat, this.animationController});

  @override
  Widget build(BuildContext context) {
    return SizeTransition(
        sizeFactor: CurvedAnimation(
            parent: animationController, curve: Curves.easeOut),
        child: Container(
          margin: const EdgeInsets.symmetric(vertical: 5.0),
            decoration: BoxDecoration(
              border: Border.all(
                color: Colors.grey[400],
              ),
            ),
          child: Row(
            children: <Widget>[
                Container(
                  margin: const EdgeInsets.only(
                    left: 8.0,
                    right: 8.0,
                    top: 4.0,
                    bottom: 4.0
                  ),
                  child: Column(
                    children: <Widget>[
                      Text(
                          "20",
                        style: TextStyle(
                          fontSize: 30
                        ),
                      ),
                      Text(
                          "Chars",
                      style: TextStyle(
                        fontSize: 12
                      ),
                      )
                    ],
                  ),
                ),
              Expanded(
                child: Center(child: Text(
                    "Name",
                style: TextStyle(
                  fontSize: 25.0
                ),)),
              )
            ],
          )
        )
    );
  }

  void _choiceAction(String choice) {
    print("WORKING");
  }

}