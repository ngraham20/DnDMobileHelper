package com.example.dndmobilehelper;

public class PC extends Character
{
    /**
     * Constructor
     * @param name the Name of the PC
     * @param newAC the AC of the PC
     * @param newMaxHP the Max HP of the PC
     * @param newInitMod the Initiative Modifier of the PC
     */
    public PC(String name, int newAC, int newMaxHP, int newInitMod)
    {
        super(name, newAC, newMaxHP, newInitMod, type.PLAYER);
    }

    PC(PC pc)
    {
        super(pc);
    }
}
