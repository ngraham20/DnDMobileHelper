// Combat class file
import 'character.dart';

class Combat {
  String name;
  List<Character> _characters;

  Combat(String name) {
    this.name = name;
    this._characters = <Character>[];
  }

  List<Character> getCharacters() {
    return this._characters;
  }
}