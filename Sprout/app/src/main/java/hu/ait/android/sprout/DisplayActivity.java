package hu.ait.android.sprout;

import android.app.WallpaperManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
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
    private int heightScreen;
    private int widthScreen;
    public static final String ROOT_URL = "http://intuit-mint.herokuapp.com/";
    private List<Transaction> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_app_icon);

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
                if (etGoal.getText().toString().equals("")) {
                    goalAmount = 0;
                } else {
                    goalAmount = Integer.parseInt(etGoal.getText().toString());
                }
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
                if (etBudget.getText().toString().equals("")) {
                    budgetAmount = 0;
                } else {
                    budgetAmount = Integer.parseInt(etBudget.getText().toString());
                }
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
                getTransactions();
            }
        });

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // get the height and width of screen
        heightScreen = metrics.heightPixels;
        widthScreen = metrics.widthPixels;
    }

    public void updateBackground(boolean bgOn) {
        // set to normal sprout beginning
        int imageID = android.R.drawable.ic_menu_camera;
        int imageIDBG = android.R.drawable.ic_menu_camera;
        // change image ID based on balance
        if (balanceAmount < budgetAmount) {
            // sad sprout
            imageID = R.drawable.uncomfort_img;
            imageIDBG = R.drawable.uncomfort_bg;
        } else if (balanceAmount > goalAmount) {
            // happy sprout
            imageID = R.drawable.supercomfort_img;
            imageIDBG = R.drawable.supercomfort_bg;
        } else {
            // okay sprout
            imageID = R.drawable.comfort_img;
            imageIDBG = R.drawable.comfort_bg;
        }

        // update the wallpaper if toggle is on
        if (bgOn) {
            // Set the wallpaper
            WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            try {
                Drawable myDrawable = ResourcesCompat.getDrawable(getResources(), imageIDBG, null);
                myWallpaperManager.setBitmap(((BitmapDrawable) myDrawable).getBitmap());
                myWallpaperManager.suggestDesiredDimensions(widthScreen, heightScreen);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ivSprout.setImageResource(imageID);
    }

    private void getTransactions(){
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        MintAPI api = adapter.create(MintAPI.class);

        api.getTransactions(new Callback<List<Transaction>>() {
            @Override
            public void success(List<Transaction> list, Response response) {

                transactions = list;
                showTotal();
            }

            @Override
            public void failure(RetrofitError error) {
                //handle errors here
            }
        });
    }

    private void showTotal(){
        int total = 0;

        for(int i=0; i<transactions.size()/4; i++){
            total += transactions.get(i).getAmount();
        }

        if(total < 0){
            total = total * -1;
        }
        total = total % 4000;

        balanceAmount = total;
        tvBalance.setText(Integer.toString(balanceAmount));
        updateBackground(backgroundOn);
    }

}
