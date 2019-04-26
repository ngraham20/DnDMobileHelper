package com.example.dndmobilehelper;

import java.io.Serializable;
import java.util.ArrayList;


public class Combat implements Serializable
{
    private ArrayList<Character> characters;
    private ArrayList<Character> mobs;
    private String name;

    /**
     * Constructor
     * @param name the name of the combat
     */
    public Combat(String name)
    {
        this.name = name;
        characters = new ArrayList<Character>();
        mobs = new ArrayList<Character>();
    }

    /**
     * Copy Constructor
     * @param other the Combat to copy
     */
    public Combat(Combat other) {
        this.characters = other.characters;
        this.mobs = other.mobs;
        this.name = other.name;
    }

    /**
     * Adds a character to the combat's characters
     * @param character the character to add
     */
    public void addCharacter(Character character)
    {
        characters.add(character);

        if(character instanceof Mob)
        {
            mobs.add(character);
        }
    }

    /**
     * Deletes a character from the character list
     * @param doomedCharacter the character to be rejected from glorious combat
     * @return the success of the removal
     */
    public boolean deleteCharacter(Character doomedCharacter)
    {
        characters.remove(doomedCharacter);

        if(doomedCharacter instanceof Mob) {
            mobs.remove(doomedCharacter);
        }
        return true;
    }

    /**
     * Returns the character list
     * @return the character list
     */
    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public ArrayList<Character> getMobs() {
        return mobs;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    /**
     * Returns the name of the combat
     * @return the name of the combat
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the combat
     * @param name the name to set to
     */
    public void setName(String name) {
        this.name = name;
    }
}
