package com.example.dndmobilehelper;

import java.io.Serializable;
import java.util.Arrays;

public abstract class Character implements Serializable, Comparable
{
    private int temporaryHP;
    private String characterName;
    private String currentCombat;
    private int armorClass;
    private int maxHealth;
    private int currentHealth;
    private int initiativeModifier;
    private int baseInitiative;
    private type characterType;
    private boolean[] successSaves = new boolean[3];
    private boolean[] failSaves = new boolean[3];
    public enum type {MONSTER, NONPLAYER, PLAYER, MOB}
    private boolean inCombat;

    public static final String MONSTER = "Monster";
    public static final String NPC = "NPC";
    public static final String PC = "PC";
    public static final String MOB = "Mob";


    /**
     * Normal Constructor
     * @param newName new name
     * @param newAC new AC
     * @param newMaxHP new max HP
     * @param newInitMod new initiative modifier
     * @param newType new Type
     */
    public Character(String newName, int newAC, int newMaxHP, int newInitMod, type newType)
    {
        characterName = newName;
        armorClass = newAC;
        maxHealth = newMaxHP;
        currentHealth = maxHealth;
        initiativeModifier = newInitMod;
        characterType = newType;
        inCombat = false;
        currentCombat = com.example.dndmobilehelper.MasterList.NULL_COMBAT;
    }

    /**
     * Copy constructor
     * @param other the Character to copy
     */
    public Character(Character other) {
        this.temporaryHP = other.temporaryHP;
        this.characterName = other.characterName;
        this.armorClass = other.armorClass;
        this.maxHealth = other.maxHealth;
        this.currentHealth = other.currentHealth;
        this.initiativeModifier = other.initiativeModifier;
        this.baseInitiative = other.baseInitiative;
        this.characterType = other.characterType;
        this.successSaves = other.successSaves;
        this.failSaves = other.failSaves;
    }

    /**
     * Static factory function to return one of the children objects
     * @param name name of new Character
     * @param type type to return
     * @param ac AC of new Character
     * @param hp max HP of new Character
     * @param initMod initiative modifier of new Character
     * @return the new Character created by the factory
     */
    static public Character characterFactory(String name, String type, int ac, int hp, int initMod)
    {
        switch (type)
        {
            case MONSTER:
                return new Monster(name, ac, hp, initMod);
            case NPC:
                return new NPC(name, ac, hp, initMod);
            case PC:
                return new PC(name, ac, hp, initMod);
            case MOB:
                return new Mob(name, ac, hp, initMod);
        }
        return null;
    }

    /**
     * return whether the character is in combat
     * @return if the character is in combat
     */
    public boolean getInCombat()
    {
        return inCombat;
    }

    /**
     * Sets whether the character is in combat
     * @param inCombat the value to set
     */
    public void setInCombat(boolean inCombat, String combatName) {
        this.inCombat = inCombat;
        this.currentCombat = combatName;
    }


    public static Character copy(Character character)
    {
        if(character instanceof Monster)
        {
            return new Monster((Monster)character);
        }
        if(character instanceof NPC)
        {
            return new NPC((NPC)character);
        }
        if(character instanceof PC)
        {
            return new PC((PC)character);
        }
        if(character instanceof Mob)
        {
            return new Mob((Mob)character);
        }
        return null;
    }

    /**
     * Resets the values of the saving throws
     */
    public void resetSavingThrows()
    {
        Arrays.fill(successSaves, Boolean.FALSE);
        Arrays.fill(failSaves, Boolean.FALSE);
    }

    /**
     * Returns the temporary hp
     * @return the temp hp
     */
    public int getTempHP() {
        if (temporaryHP > 0) {
            return temporaryHP;
        }
        else {
            return 0;
        }
    }

    /**
     * Adds a failed save to the character
     */
    public void addFailedSave()
    {
        for(int i = 0; i < failSaves.length; i++)
        {
            if(failSaves[i] == false)
            {
                failSaves[i] = true;
                break;
            }
        }
    }

    /**
     * Adds a successful save to the character
     */
    public void addSuccessfulSave()
    {
        for(int i = 0; i < successSaves.length; i++)
        {
            if(successSaves[i] == false)
            {
                successSaves[i] = true;
                break;
            }
        }
    }

    /**
     * Damage the character
     * @param damage the amount to damage by
     */
    public void takeDamage(int damage)
    {

        //don't want to damage for negatives cause thats weird reverse healing
        if(damage < 0)
        {
            damage = 0;
        }
        //if all damage goes into taking down the temp hp then just subtract from tempHp and done
        else if(temporaryHP > 0 && damage <= temporaryHP)
        {
            temporaryHP -= damage;

        }
        //damage will overflow temporaryHP, so must subtract from main health
        else
        {
            //temporary hp isn't 0, so subtract it from damage, so later calculation will use leftover damage amt instead of passed in total
            if (temporaryHP > 0)
            {
                damage -= temporaryHP;
                temporaryHP = 0;
            }

            int damagedHealth = currentHealth - damage;

            if(damagedHealth <  0)
            {
                currentHealth = 0;
            }
            else
            {
                currentHealth = damagedHealth;
            }

        }



    }

    /**
     * Heals the character by an amount
     * @param healAmount the amount to heal by
     */
    public void heal(int healAmount)
    {
        int healedHealth = currentHealth + healAmount;
        if(healedHealth > maxHealth)
        {
            currentHealth = maxHealth;
        }
        else
        {
            currentHealth = healedHealth;
        }
    }

    /**
     * Return temporary hp
     * @param newTempHP the temporary hp
     */
    public void setTemporaryHP(int newTempHP)
    {
        temporaryHP = newTempHP;
    }

    /**
     * Returns the character name
     * @return the character name
     */
    public String getCharacterName() {
        return characterName;
    }

    /**
     * Sets the character name
     * @param characterName the name to set to
     */
    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    /**
     * Returns the current health
     * @return character current health
     */
    public int getCurrentHealth()
    {
        return currentHealth;
    }

    /**
     * Sets the current health
     * @param newHealth the health to set to
     */
    public void setCurrentHealth(int newHealth)
    {
        if(newHealth > maxHealth)
        {
            currentHealth = maxHealth;
        }
        else
        {
            currentHealth = newHealth;
        }
    }

    /**
     * Returns the armor class
     * @return the armor class to return
     */
    public int getArmorClass() {
        return armorClass;
    }

    /**
     * Returns the max health
     * @return max health
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Returns the current initiative (base init + mod)
     * @return the base initiative + initiative modifier
     */
    public int getCurrentInitiative() {
        return baseInitiative + initiativeModifier;
    }

    /**
     * Returns the initiative modifier
     * @return the initiative modifier
     */
    public int getInitiativeModifier() {
        return initiativeModifier;
    }

    /**
     * Returns the character type
     * @return the character type
     */
    public String getCharacterType() {

        switch(characterType) {
            case MONSTER:
            {
                return Character.MONSTER;
            }
            case PLAYER:
            {
                return Character.PC;
            }
            case NONPLAYER:
            {
                return Character.NPC;
            }
            case MOB:
            {
                return Character.MOB;
            }
            default:
            {
                return "";
            }

        }
    }

    /**
     * Sets the armor class of the character
     * @param armorClass the armor class of the character
     */
    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    /**
     * Sets the character type
     * @param characterType the character type
     */
    public void setCharacterType(type characterType) {
        this.characterType = characterType;
    }

    /**
     * Sets the base initiative
     * @param baseInitiative the base initiative
     */
    public void setBaseInitiative(int baseInitiative) {
        this.baseInitiative = baseInitiative;
    }

    /**
     * Sets the initative modifier
     * @param initiativeModifier the initiative modifier
     */
    public void setInitiativeModifier(int initiativeModifier) {
        this.initiativeModifier = initiativeModifier;
    }

    /**
     * Sets the max health
     * @param maxHealth the max health
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * Compares one Character to another based on Initiative for sorting
     * @param compareable the Character to compare to
     * @return the result of the comparison
     */
    @Override
    public int compareTo(Object compareable) {
        int compareInit = ((Character)compareable).getCurrentInitiative();
        return compareInit-this.getCurrentInitiative();
    }

    public int getBaseInitiative() {
        return baseInitiative;
    }

    public int getTemporaryHP() {
        return temporaryHP;
    }
}
