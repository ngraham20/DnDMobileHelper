package com.example.dndmobilehelper;

public class Monster extends Character
{
    /**
     * Constructor
     * @param name the name of the Monster
     * @param newAC the AC of the Monster
     * @param newMaxHP the Max HP of the Monster
     * @param newInitMod the Initiative Modifier Value of the Monster
     */
    public Monster(String name, int newAC, int newMaxHP, int newInitMod)
    {
        super(name, newAC, newMaxHP, newInitMod, type.MONSTER);
    }

    /**
     * Copy Constructor
     * @param monster the Monster to copy
     */
    Monster(Monster monster)
    {
        super(monster);
    }
}
