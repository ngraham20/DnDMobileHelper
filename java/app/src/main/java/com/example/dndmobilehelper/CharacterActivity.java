package com.example.dndmobilehelper;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class CharacterActivity extends AppCompatActivity {

    private static final String TAG = "CharacterActivity";

    private Character character;

    private TextView nameText;

    private TextView typeText;
    private TextView armorText;
    private TextView initiativeText;
    private TextView initModText;

    private TextView typeTextDisplay;
    private TextView armorTextDisplay;
    private TextView initiativeTextDisplay;
    private TextView initModTextDisplay;

    private TextView currentHpText;
    private TextView tempHpText;

    private Button healButton;
    private EditText healthEdit;
    private Button damageButton;

    private CheckBox saveSuccess1;
    private CheckBox saveSuccess2;
    private CheckBox saveSuccess3;

    private CheckBox saveFail1;
    private CheckBox saveFail2;
    private CheckBox saveFail3;

    private Button changeInitButton;
    private EditText editInit;

    private Button changeInitModButton;
    private EditText editInitMod;

    private Button changeTempHpButton;
    private EditText editTempHp;

    private Button changeHPButton;
    private EditText editHp;

    private Button changeACButton;
    private EditText editAC;

    private Button changeNameButton;
    private EditText editName;

    /**
     * Default Constructor
     */
    public CharacterActivity()
    {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Beginning onCreate");
        Log.d(TAG, "onCreate: Calling Super");
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: Setting Layout");
        setContentView(R.layout.activity_character);


        Log.d(TAG, "onCreate: Initializing View Objects");
        initializeViews();

        Log.d(TAG, "onCreate: Getting Intent");
        int index = (int) getIntent().getSerializableExtra(CharacterAdapter.CHARACTER_EXTRA);
        //character = CharacterMasterList.getInstance().getmCharacterTemplates().get(index);
        character = MasterList.getInstance().getmAllCharacters().get(index);

        Log.d(TAG, "onCreate: Setting Text Displays");
        setStatsText();

        Log.d(TAG, "onCreate: Watching Buttons");
        buttonWatcher();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    /**
     * Add click listeners to the buttons
     */
    public void buttonWatcher()
    {
        healButton.setOnClickListener(new HpOnClickListener(this, true));
        damageButton.setOnClickListener(new HpOnClickListener(this, false));

        changeInitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean containsValue = !((editInit.getText().toString()).equals(""));
                if(containsValue)
                {
                    int newInit = Integer.parseInt(editInit.getText().toString());
                    character.setBaseInitiative(newInit);
                    setInitiativeText();
                    editInit.setText("");
                }

                save();
            }
        });

        changeInitModButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean containsValue = !((editInitMod.getText().toString()).equals(""));
                if(containsValue){
                    int newInitMod = Integer.parseInt(editInitMod.getText().toString());
                    character.setInitiativeModifier(newInitMod);
                    setInitModText();
                    setInitiativeText();
                    editInitMod.setText("");
                }

                save();
            }
        });

        changeTempHpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean containsValue = !((editTempHp.getText().toString()).equals(""));
                if(containsValue)
                {
                    int newTemp = Integer.parseInt(editTempHp.getText().toString());
                    character.setTemporaryHP(newTemp);
                    setTempHpText();
                    editTempHp.setText("");
                }

                save();
            }
        });

        changeHPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int oldMax = character.getMaxHealth();

                boolean containsValue = !((editHp.getText().toString()).equals(""));
                if(containsValue)
                {
                    int newHp = Integer.parseInt(editHp.getText().toString());
                    character.setMaxHealth(newHp);

                    if(character.getCurrentHealth() == oldMax)
                    {
                        character.setCurrentHealth(newHp);
                        setCurrentHealthText();
                        editHp.setText("");
                    }
                }
                save();
            }
        });

        changeACButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean containsValue = !((editAC.getText().toString()).equals(""));
                if(containsValue)
                {
                    int newAC = Integer.parseInt(editAC.getText().toString());
                    character.setArmorClass(newAC);
                    setArmorText();
                    editAC.setText("");
                }
                save();
            }
        });

        changeNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean containsValue = !((editName.getText().toString()).equals(""));
                if(containsValue)
                {
                    String newName = editName.getText().toString();
                    character.setCharacterName(newName);
                    setNameText();
                    editName.setText("");
                }
                save();

            }
        });
    }


    /**
     * Private class for view listeners
     */
    private class HpOnClickListener implements View.OnClickListener
    {
        private CharacterActivity activity;
        private boolean type;

        /**
         * Constructor
         * @param newActivity the activity context
         * @param newType damage or heal
         */
        public HpOnClickListener(CharacterActivity newActivity, boolean newType)
        {
            activity = newActivity;
            type = newType;
        }

        @Override
        public void onClick(View v)
        {
            boolean containsValue = !((healthEdit.getText().toString()).equals(""));
            if(containsValue)
            {
                int amount = Integer.parseInt(healthEdit.getText().toString());
                activity.updateCurrentHp(type, amount);
                setTempHpText();
                setCurrentHealthText();
            }

        }
    }

    /**
     * Sets the nameText view
     */
    public void setNameText()
    {
        nameText.setText(character.getCharacterName());
    }

    /**
     * Sets the StatsText views
     */
    public void setStatsText()
    {
        setTypeText();
        setArmorText();
        setInitiativeText();
        setInitModText();
        setCurrentHealthText();
        setTempHpText();
        setNameText();
    }

    /**
     * sets the typeText view
     */
    public void setTypeText()
    {
        typeText.setText(character.getCharacterType());
    }

    /**
     * sets the armorText view
     */
    public void setArmorText()
    {
        armorText.setText(Integer.toString(character.getArmorClass()));
    }

    /**
     * sets the Initiative view
     */
    public void setInitiativeText()
    {
        initiativeText.setText(Integer.toString(character.getCurrentInitiative()));
    }

    /**
     * sets the initiative modifier view
     */
    public void setInitModText()
    {
        initModText.setText("+" + Integer.toString(character.getInitiativeModifier()));
    }

    /**
     * sets the current health view
     */
    public void setCurrentHealthText()
    {
        currentHpText.setText(Integer.toString(character.getCurrentHealth()));
    }

    public void setTempHpText()
    {
        tempHpText.setText(Integer.toString(character.getTempHP()));
    }

    /**
     * updates the current hp with damage or heal
     * @param healed whether it should heal or not
     * @param amount the amount to heal/damage
     */
    //true is healed, false is damage
    public void updateCurrentHp(boolean healed, int amount)
    {
        if(healed)
        {
            character.heal(amount);
        }
        else
        {
            character.takeDamage(amount);
        }
    }

    private void save()
    {
        // TODO make this async
        MasterList.getInstance().save();
    }

    /**
     * initialize all views
     */
    private void initializeViews()
    {
        nameText = (TextView) findViewById(R.id.characterName);
        typeText = (TextView) findViewById(R.id.characterType);
        armorText = (TextView) findViewById(R.id.armorClass);
        initiativeText = (TextView) findViewById(R.id.baseInitiative);
        initModText = (TextView) findViewById(R.id.initiativeMod);
        typeTextDisplay = (TextView) findViewById(R.id.charTypeDisplay);
        armorTextDisplay = (TextView) findViewById(R.id.armorClassDisplay);
        initiativeTextDisplay = (TextView) findViewById(R.id.currentInitiativeDisplay);
        initModTextDisplay = (TextView) findViewById(R.id.initiativeModDisplay);
        currentHpText = (TextView) findViewById(R.id.currentHP);
        tempHpText = (TextView) findViewById(R.id.tempHP);
        healButton = (Button) findViewById(R.id.heal);
        healthEdit = (EditText) findViewById(R.id.healOrDamage);
        damageButton = (Button) findViewById(R.id.damage);
        saveSuccess1 = (CheckBox) findViewById(R.id.saveSuccessOne);
        saveSuccess3 = (CheckBox) findViewById(R.id.saveSuccessThree);
        saveSuccess2 = (CheckBox) findViewById(R.id.saveSuccessTwo);
        saveFail1 = (CheckBox) findViewById(R.id.savingFailureOne);
        saveFail2 = (CheckBox) findViewById(R.id.savingFailureTwo);
        saveFail3 = (CheckBox) findViewById(R.id.savingFailureThree);
        changeInitButton = (Button) findViewById(R.id.changeInitiative);
        editInit = (EditText) findViewById(R.id.editInitiative);
        changeInitModButton = (Button) findViewById(R.id.changeInitMod);
        editInitMod = (EditText) findViewById(R.id.editInitMod);
        changeTempHpButton = (Button) findViewById(R.id.changeTempHP);
        editTempHp = (EditText) findViewById(R.id.editTempHP);
        changeHPButton = (Button) findViewById(R.id.changeMaxHP);
        editHp = (EditText) findViewById(R.id.editHP);
        changeACButton = (Button) findViewById(R.id.changeAC);
        editAC = (EditText) findViewById(R.id.editAC);
        changeNameButton = (Button) findViewById(R.id.changeName);
        editName = (EditText) findViewById(R.id.editName);
    }

}
