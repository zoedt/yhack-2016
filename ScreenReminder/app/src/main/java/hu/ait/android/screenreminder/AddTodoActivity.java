package hu.ait.android.screenreminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import hu.ait.android.screenreminder.data.Todo;

public class AddTodoActivity extends AppCompatActivity {
    public static final String KEY_TODO = "KEY_TODO";
    private EditText etTodoText;
    private CheckBox cbTodoDone;
    private Todo todoToEdit = null;
    Spinner spPriority;
    TextView tvPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        if (getIntent().getSerializableExtra(MainActivity.KEY_EDIT) != null) {
            todoToEdit = (Todo) getIntent().getSerializableExtra(MainActivity.KEY_EDIT);
        }

        etTodoText = (EditText) findViewById(R.id.etTodoText);
        cbTodoDone = (CheckBox) findViewById(R.id.cbTodoDone);

        setUpSpinner();

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTodo();
            }
        });


        if (todoToEdit != null) {
            etTodoText.setText(todoToEdit.getTodo());
            cbTodoDone.setChecked(todoToEdit.isDone());
            spPriority.setSelection(todoToEdit.getPriority());
        }
    }

    private void setUpSpinner() {
        spPriority = (Spinner) findViewById(R.id.spPriority);
        tvPriority = (TextView) findViewById(R.id.tvPriority);
        String[] categories = {getString(R.string.category1),
                getString(R.string.category2), getString(R.string.category3)};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(adapter);
        spPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        tvPriority.setText("0");
                        break;
                    case 1:
                        tvPriority.setText("1");
                        break;
                    case 2:
                        tvPriority.setText("2");
                        break;
                    default:
                        tvPriority.setText("2");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddTodoActivity.this, R.string.priority_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveTodo() {
        if ("".equals(etTodoText.getText().toString())) {
            etTodoText.setError(getString(R.string.error_field_empty));
        } else {
            Intent intentResult = new Intent();
            Todo todoResult = (todoToEdit != null) ? todoToEdit : new Todo();

            todoResult.setTodo(etTodoText.getText().toString());
            todoResult.setDone(cbTodoDone.isChecked());
            todoResult.setPriority(Integer.parseInt(tvPriority.getText().toString()));

            intentResult.putExtra(KEY_TODO, todoResult);
            setResult(RESULT_OK, intentResult);
            finish();
        }
    }
}
