package hu.ait.android.sprout;

import android.app.WallpaperManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DisplayActivity extends AppCompatActivity {

    private EditText etGoal;
    private EditText etBudget;
    private ImageView ivSprout;
    private TextView tvBalance;
    private Switch switchBackground;
    private FloatingActionButton fabRefresh;
    private boolean backgroundOn;
    private int balanceAmount;
    private int goalAmount;
    private int budgetAmount;
    public static final String ROOT_URL = "http://intuit-mint.herokuapp.com/";
    private List<Transaction> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(android.R.drawable.ic_menu_save);

        // get the goal
        etGoal = (EditText) findViewById(R.id.etGoal);
        goalAmount = Integer.parseInt(etGoal.getText().toString());
        etGoal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                goalAmount = Integer.parseInt(etGoal.getText().toString());
                updateBackground(backgroundOn);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // nothing
            }
        });


        // get the budget
        etBudget = (EditText) findViewById(R.id.etBudget);
        budgetAmount = Integer.parseInt(etBudget.getText().toString());
        etBudget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                budgetAmount = Integer.parseInt(etBudget.getText().toString());
                updateBackground(backgroundOn);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // nothing
            }
        });

        // get the imageview for the sprout
        ivSprout = (ImageView) findViewById(R.id.ivSprout);

        // get the text view for balance
        tvBalance = (TextView) findViewById(R.id.tvBalance);
        getTransactions();

        // get the switch for turning the background on/off
        switchBackground = (Switch) findViewById(R.id.switchBackground);
        backgroundOn = switchBackground.isChecked();
        switchBackground.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                backgroundOn = isChecked;
            }
        });

        // refresh action
        fabRefresh = (FloatingActionButton) findViewById(R.id.fabRefresh);
        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO call api, refresh balance, and refresh background
                getTransactions();
            }
        });
    }

    public void updateBackground(boolean bgOn) {
        // set to normal sprout beginning
        int imageID = android.R.drawable.ic_menu_camera;
        // change image ID based on balance
        if (balanceAmount < budgetAmount) {
            // sad sprout
            imageID = android.R.drawable.ic_menu_add;
        } else if (balanceAmount > goalAmount) {
            // happy sprout
            imageID = android.R.drawable.ic_menu_camera;
        } else {
            // okay sprout
            imageID = android.R.drawable.ic_menu_agenda;
        }

        // update the wallpaper if toggle is on
        if (bgOn) {
            // Set the wallpaper
            WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            try {
                Drawable myDrawable = ResourcesCompat.getDrawable(getResources(), imageID, null);
                myWallpaperManager.setBitmap(((BitmapDrawable) myDrawable).getBitmap());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ivSprout.setImageResource(imageID);
    }

    private void getTransactions(){
        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Log.e("hi", "enter transactions");

        //Creating an object of our api interface
        MintAPI api = adapter.create(MintAPI.class);

        //Defining the method
        api.getTransactions(new Callback<List<Transaction>>() {
            @Override
            public void success(List<Transaction> list, Response response) {

                //Storing the data in our list
                transactions = list;

                //Calling a method to show the list
                showTotal();
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                // Log.e("hi", error.getMessage());
            }
        });
    }

    private void showTotal(){
        String[] items = new String[transactions.size()];
        int total = 0;

        //Traversing through the whole list to get all the names
        for(int i=0; i<transactions.size()/4; i++){
            //Storing names to string array
            items[i] = transactions.get(i).getName();
            total += transactions.get(i).getAmount();
        }

        if(total < 0){
            total = total * -1;
        }
        total = total % 4000;
        // Log.e("hi", "append");
        // total = 0;

        balanceAmount = total;
        tvBalance.setText(Integer.toString(balanceAmount));
        updateBackground(backgroundOn);
    }

}
