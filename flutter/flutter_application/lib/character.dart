// the character class file

import 'package:flutter/widgets.dart';

class Character {
  String name;
  int hp;
  int ac;
  int init;

  Character(String name, int hp, int ac, int init) {
    this.name = name;
    this.hp = hp;
    this.ac = ac;
    this.init = init;
  }
}