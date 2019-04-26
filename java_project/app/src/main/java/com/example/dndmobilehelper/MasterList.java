package com.example.dndmobilehelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MasterList {
    private static final MasterList holder = new MasterList();
    public static String NULL_COMBAT = "NULL";
    private static final String FILE_NAME = "combats.json";

    // temporary variables
    private ArrayList<Combat> mCombats = new ArrayList<>();
    private ArrayList<Character> mCharacterTemplates = new ArrayList<>();
    private ArrayList<Character> mAllCharacters = new ArrayList<>();
    private ArrayList<Character> mOutsideCombat = new ArrayList<>();

    // variables for loading and saving
    private JSONObject rootObject = new JSONObject();
    private JSONArray jCombats = new JSONArray();
    private JSONArray jOutsideCombat = new JSONArray();

    /**
     * Private Constructor to prevent creation of new Master Lists
     */
    private MasterList()
    {
    }

    public void addCombat(Combat combat) throws JSONException {
        mCombats.add(combat);
        jCombats.put(new JSONObject()
                .put("name",combat.getName())
                .put("characters", new JSONArray(combat.getCharacters()))
                .put("mobs", new JSONArray(combat.getMobs())));

        // TODO move this to async at some point
        save();
    }

    public void addCharacter(Character character) throws JSONException
    {
        mCharacterTemplates.add(character);
        mAllCharacters.add(character);
        mOutsideCombat.add(character);
        jOutsideCombat.put(new JSONObject()
                .put("name", character.getCharacterName())
                .put("type", character.getCharacterType())
                .put("ac",character.getArmorClass())
                .put("init_mod",character.getInitiativeModifier())
                .put("init_base",character.getBaseInitiative())
                .put("current_hp",character.getCurrentHealth())
                .put("temp_hp", character.getTempHP()));

        // TODO move this to async at some point
        save();
    }

    public void removeCharacter(Character character)
    {
        int index = mOutsideCombat.indexOf(character);
        jOutsideCombat.remove(index);
        mOutsideCombat.remove(index);
        mCharacterTemplates.remove(character);
        mAllCharacters.remove(character);
        save();
    }

    public void removeCombat(Combat combat)
    {
        int index = mCombats.indexOf(combat);
        jCombats.remove(index);
        mCombats.remove(index);
        save();
    }



    /**
     * Returns the only instance
     * @return this instance
     */
    public static MasterList getInstance()
    {
        return holder;
    }

    /**
     * Returns the combats list
     * @return the combats list
     */
    public ArrayList<Combat> getmCombats() {
        return this.mCombats;
    }

    public ArrayList<Character> getmCharacterTemplates()
    {
        return mCharacterTemplates;
    }

    public ArrayList<Character> getmAllCharacters() {
        return mAllCharacters;
    }

    public static void CreateFileIfNotExist()
    {
        try
        {
            if(DnDFileHandler.getInstance().createFileIfNotExist(FILE_NAME))
            {
                JSONObject root = new JSONObject()
                        .put("combats", new JSONArray())
                        .put("no_combats", new JSONArray());

                DnDFileHandler.getInstance().writeToFile(FILE_NAME, root.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void load() throws IOException, JSONException {
        String jsonString = DnDFileHandler.getInstance().readFile(FILE_NAME);
        parseJsonString(jsonString);
    }

    public void save()
    {
        rootObject = new JSONObject();
        jOutsideCombat = new JSONArray();
        jCombats = new JSONArray();

        try {
            // setup root node
            rootObject.put("combats", jCombats);
            rootObject.put("no_combats", jOutsideCombat);

            // setup combats
            for(Combat combat : mCombats)
            {
                JSONArray jCombatCharacters = new JSONArray();
                JSONArray jCombatMobs = new JSONArray();
                for(Character character : combat.getCharacters())
                {
                    if(character.getCharacterType().equals(Character.MOB))
                    {
                        jCombatMobs.put(new JSONObject()
                                .put("name", character.getCharacterName())
                                .put("type", character.getCharacterType())
                                .put("ac", character.getArmorClass())
                                .put("init_mod", character.getInitiativeModifier())
                                .put("init_base", character.getBaseInitiative())
                                .put("current_hp", character.getCurrentHealth())
                                .put("temp_hp", character.getTempHP()));
                    }
                    else {
                        jCombatCharacters.put(new JSONObject()
                                .put("name", character.getCharacterName())
                                .put("type", character.getCharacterType())
                                .put("ac", character.getArmorClass())
                                .put("init_mod", character.getInitiativeModifier())
                                .put("init_base", character.getBaseInitiative())
                                .put("current_hp", character.getCurrentHealth())
                                .put("temp_hp", character.getTempHP()));
                    }
                }
                jCombats.put(new JSONObject()
                        .put("name",combat.getName())
                        .put("characters", jCombatCharacters)
                        .put("mobs", jCombatMobs));
            }

            //setup outside combat
            for(Character character : mOutsideCombat)
            {
                jOutsideCombat.put(new JSONObject()
                        .put("name", character.getCharacterName())
                        .put("type", character.getCharacterType())
                        .put("ac",character.getArmorClass())
                        .put("init_mod",character.getInitiativeModifier())
                        .put("init_base",character.getBaseInitiative())
                        .put("current_hp",character.getCurrentHealth())
                        .put("temp_hp", character.getTempHP()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            DnDFileHandler.getInstance().writeToFile(FILE_NAME, rootObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseJsonString(String json) throws JSONException {
        rootObject = new JSONObject(json);

        jCombats = rootObject.getJSONArray("combats");
        jOutsideCombat = rootObject.getJSONArray("no_combats");

        // add characters not in combat

        for(int i = 0; i < jOutsideCombat.length(); i++)
        {
            JSONObject jCharacter = jOutsideCombat.getJSONObject(i);

            Character character = Character.characterFactory(
                    jCharacter.getString("name"),
                    jCharacter.getString("type"),
                    jCharacter.getInt("ac"),
                    jCharacter.getInt("current_hp"),
                    jCharacter.getInt("init_mod")
            );

            character.setInCombat(false, NULL_COMBAT);
            mOutsideCombat.add(character);
            mCharacterTemplates.add(character);
            mAllCharacters.add(character);
        }

        // grab combats and all characters in that combat
        for(int i = 0; i < jCombats.length(); i++)
        {
            JSONObject jCombat = jCombats.getJSONObject(i);
            JSONArray jCharacters = jCombat.getJSONArray("characters");
            JSONArray jMobs = jCombat.getJSONArray("mobs");

            Combat combat = new Combat(jCombat.getString("name"));

            for(int j = 0; j < jCharacters.length(); j++)
            {
                JSONObject jCharacter = jCharacters.getJSONObject(j);
                Character character = Character.characterFactory(
                        jCharacter.getString("name"),
                        jCharacter.getString("type"),
                        jCharacter.getInt("ac"),
                        jCharacter.getInt("current_hp"),
                        jCharacter.getInt("init_mod"));

                combat.addCharacter(character);
                character.setInCombat(true, combat.getName());
                mCharacterTemplates.add(character);
                mAllCharacters.add(character);
            }

            // grab all mobs and allow them into the combat
            for(int j = 0; j < jMobs.length(); j++)
            {
                JSONObject jMob = jMobs.getJSONObject(j);
                Character mob = Character.characterFactory(
                        jMob.getString("name"),
                        jMob.getString("type"),
                        jMob.getInt("ac"),
                        jMob.getInt("current_hp"),
                        jMob.getInt("init_mod"));

                combat.addCharacter(mob);
                // the mob isn't added to the template list here, but only to the main list
                mAllCharacters.add(mob);
            }

            mCombats.add(combat);

        }
    }

    public void addCharacterToCombat(Character character, Combat combat)
    {
        if(character instanceof Mob)
        {

            Mob newMob = new Mob((Mob)character);
            // add copies of character
            combat.addCharacter(newMob);
            mAllCharacters.add(newMob);
            JSONObject jCombat = null;

            try {
                jCombat = jCombats.getJSONObject(mCombats.indexOf(combat));
                jCombat.getJSONArray("mobs")
                        .put(new JSONObject()
                                .put("name", character.getCharacterName())
                                .put("type", character.getCharacterType())
                                .put("ac", character.getArmorClass())
                                .put("init_mod", character.getInitiativeModifier())
                                .put("init_base", character.getBaseInitiative())
                                .put("current_hp", character.getCurrentHealth())
                                .put("temp_hp", character.getTempHP()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // notice, that the mob isn't removed from outside combat here. This is intentional

        }
        else {
            character.setInCombat(true, combat.getName());
            combat.addCharacter(character);
            JSONObject jCombat = null;
            try {
                jCombat = jCombats.getJSONObject(mCombats.indexOf(combat));
                jCombat.getJSONArray("characters")
                        .put(new JSONObject()
                                .put("name", character.getCharacterName())
                                .put("type", character.getCharacterType())
                                .put("ac", character.getArmorClass())
                                .put("init_mod", character.getInitiativeModifier())
                                .put("init_base", character.getBaseInitiative())
                                .put("current_hp", character.getCurrentHealth())
                                .put("temp_hp", character.getTempHP()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            int index = mOutsideCombat.indexOf(character);
            mOutsideCombat.remove(index);
            jOutsideCombat.remove(index);
        }

        // TODO async
        save();
    }

    public void removeCharacterFromCombat(Character character, Combat combat)
    {
        if(character instanceof Mob)
        {
            int index = combat.getMobs().indexOf(character);
            JSONObject jCombat = null;
            combat.deleteCharacter(character);
            try {
                jCombat = jCombats.getJSONObject(mCombats.indexOf(combat));
                jCombat.getJSONArray("mobs").remove(index);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {

            int index = combat.getCharacters().indexOf(character);
            character.setInCombat(false, NULL_COMBAT);
            combat.deleteCharacter(character);
            JSONObject jCombat = null;

            try {
                jCombat = jCombats.getJSONObject(mCombats.indexOf(combat));
                jCombat.getJSONArray("characters").remove(index);

                jOutsideCombat
                        .put(new JSONObject()
                                .put("name", character.getCharacterName())
                                .put("type", character.getCharacterType())
                                .put("ac", character.getArmorClass())
                                .put("init_mod", character.getInitiativeModifier())
                                .put("init_base", character.getBaseInitiative())
                                .put("current_hp", character.getCurrentHealth())
                                .put("temp_hp", character.getTempHP()));

                mOutsideCombat.add(character);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // TODO make async
        save();
    }
}
