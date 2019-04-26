package com.example.dndmobilehelper;

public class NPC extends Character
{
    /**
     * Constructor
     * @param name the Name of the NPC
     * @param newAC the AC of the NPC
     * @param newMaxHP the Max HP of the NPC
     * @param newInitMod the Initiative Modifier of the NPC
     */
    public NPC(String name, int newAC, int newMaxHP, int newInitMod)
    {
        super(name, newAC, newMaxHP, newInitMod, type.NONPLAYER);

    }

    NPC(NPC npc)
    {
        super(npc);
    }

}
