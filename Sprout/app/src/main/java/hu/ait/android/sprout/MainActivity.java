package hu.ait.android.sprout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    public static final String ROOT_URL = "http://intuit-mint.herokuapp.com/";
    private TextView mTextView;
    private Button mImportButton;
    private List<Transaction> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImportButton = (Button) findViewById(R.id.button_import_info);
        mTextView = (TextView) findViewById(R.id.text_view_welcome);

        mImportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do the API call and randomly pick some objects
                getTransactions();
                Log.e("hi", "test");
            }
        });
    }

    private void getTransactions(){
        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        Log.e("hi", "enter transactions");

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
                Log.e("hi", error.getMessage());
            }
        });
    }

    private void showTotal(){
        String[] items = new String[transactions.size()];
        int total = 0;

        //Traversing through the whole list to get all the names
        for(int i=0; i<transactions.size(); i++){
            //Storing names to string array
            items[i] = transactions.get(i).getName();
            total += transactions.get(i).getAmount();
        }

        if(total < 0){
            total = total * -1;
        }
        total = total % 4000;
        mTextView.append("total = " + total);
        Log.e("hi", "append");
        total = 0;

    }
}
