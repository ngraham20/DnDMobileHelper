package com.example.dndmobilehelper;

public class Mob extends Character {
    public Mob(String newName, int newAC, int newMaxHP, int newInitMod) {
        super(newName, newAC, newMaxHP, newInitMod, type.MOB);
    }

    public Mob(Mob other) {
        super(other);
    }
}
