// this widget describes the list item of a character

import 'package:flutter/material.dart';
import 'character.dart';

class CharacterListItem extends StatelessWidget {
  final Character character;
  final AnimationController animationController;
  CharacterListItem({this.character, this.animationController});

  @override
  Widget build(BuildContext context) {
    return new SizeTransition(
        sizeFactor: new CurvedAnimation(
            parent: animationController, curve: Curves.easeOut),
        child: new Container(
          margin: const EdgeInsets.symmetric(vertical: 10.0),
          child: new Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              new Container(
                margin: const EdgeInsets.only(right: 16.0),
                child: new CircleAvatar(child: new Text("25\nDec")),
              ),
              new Expanded(
                child: new Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[
                    new Text("James", style: Theme.of(context).textTheme.subhead),
                    new Container(
                      margin: const EdgeInsets.only(top: 5.0),
                      child: new Text("Ummm"),
                    ),
                  ],
                ),
              ),
            ],
          ),
        )
    );
  }

}