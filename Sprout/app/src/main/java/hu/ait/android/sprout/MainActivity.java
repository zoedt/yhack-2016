package hu.ait.android.sprout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mImportButton;
    private EditText mBank, mUser, mPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        mImportButton = (Button) findViewById(R.id.button_import_info);
        mBank = (EditText) findViewById(R.id.edit_text_bank);
        mUser = (EditText) findViewById(R.id.edit_text_user);
        mPass = (EditText) findViewById(R.id.edit_text_pass);

        if(sharedPref.getInt( getString(R.string.saved_import), 0 ) == 1){
            Intent intentAddTodo = new Intent(MainActivity.this, DisplayActivity.class);
            startActivity(intentAddTodo);
            mImportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mBank.getText().toString().trim().equals("") || mUser.getText().toString().trim().equals("") || mPass.getText().toString().trim().equals("")) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Fill out all of the information, please", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        editor.putInt(getString(R.string.saved_import), 1);
                        editor.commit();

                        Intent intentAddTodo = new Intent(MainActivity.this, DisplayActivity.class);
                        startActivity(intentAddTodo);
                    }
                }
            });
        } else {
            mImportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mBank.getText().toString().trim().equals("") || mUser.getText().toString().trim().equals("") || mPass.getText().toString().trim().equals("")){
                        Toast toast = Toast.makeText(getApplicationContext(), "Fill out all of the information, please", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        editor.putInt(getString(R.string.saved_import), 1);
                        editor.commit();

                        Intent intentAddTodo = new Intent(MainActivity.this, DisplayActivity.class);
                        startActivity(intentAddTodo);
                    }
                }
            });
        }

    }


}
