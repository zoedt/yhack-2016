package hu.ait.android.screenreminder;

import android.app.WallpaperManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import hu.ait.android.screenreminder.adapter.TodoAdapter;
import hu.ait.android.screenreminder.adapter.TodoItemTouchHelperCallback;
import hu.ait.android.screenreminder.data.Todo;


public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_TODO = 101;
    public static final int REQUEST_CODE_EDIT_TODO = 102;
    private static int RESULT_LOAD_IMG = 103;
    String imgDecodableString;
    public static final String KEY_EDIT = "KEY_EDIT";
    public static final String KEY_APP_ON = "KEY_APP_ON";
    private TodoAdapter todoRecyclerAdapter;

    public static boolean appOn;
    private Todo todoEditHolder;
    private int todoToEditPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the last state of the app
        if (savedInstanceState != null &&
                savedInstanceState.containsKey(KEY_APP_ON)) {
            appOn = savedInstanceState.getBoolean(KEY_APP_ON);
            MenuItem m_item = (MenuItem) findViewById(R.id.action_on_off);
            if (appOn && m_item != null) {
                m_item.setTitle(R.string.turn_off);
            } else {
                m_item.setTitle(R.string.turn_on);
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.ic_action_alarm);

        todoRecyclerAdapter = new TodoAdapter(this);
        final RecyclerView recyclerView =
                (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // RecyclerView layout manager
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(todoRecyclerAdapter);

        ItemTouchHelper.Callback callback =
                new TodoItemTouchHelperCallback(todoRecyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        FloatingActionButton fab =
                (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTodoActivity();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean(KEY_APP_ON, appOn);
    }

    private void showAddTodoActivity() {
        Intent intentAddTodo = new Intent(MainActivity.this, AddTodoActivity.class);
        startActivityForResult(intentAddTodo,
                REQUEST_CODE_ADD_TODO);
    }

    public void showEditTodoActivity(Todo todoToEdit, int position) {
        Intent intentEditTodo = new Intent(MainActivity.this,
                AddTodoActivity.class);
        todoEditHolder = todoToEdit;
        todoToEditPosition = position;

        intentEditTodo.putExtra(KEY_EDIT, todoToEdit);
        startActivityForResult(intentEditTodo, REQUEST_CODE_EDIT_TODO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == REQUEST_CODE_ADD_TODO) {
                    Todo todo = (Todo) data.getSerializableExtra(
                            AddTodoActivity.KEY_TODO);

                    todoRecyclerAdapter.addTodo(todo);
                } else if (requestCode == REQUEST_CODE_EDIT_TODO) {
                    Todo todoTemp = (Todo) data.getSerializableExtra(
                            AddTodoActivity.KEY_TODO);

                    todoEditHolder.setTodo(todoTemp.getTodo());
                    todoEditHolder.setDone(todoTemp.isDone());
                    todoEditHolder.setPriority(todoTemp.getPriority());

                    if (todoToEditPosition != -1) {
                        todoRecyclerAdapter.updateTodo(todoToEditPosition, todoEditHolder);
                        todoToEditPosition = -1;
                    } else {
                        todoRecyclerAdapter.notifyDataSetChanged();
                    }
                } else if (requestCode == RESULT_LOAD_IMG && null != data) {
                    // Get the Image from data
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();

                    // Set the wallpaper
                    WallpaperManager myWallpaperManager
                            = WallpaperManager.getInstance(getApplicationContext());
                    try {
                        myWallpaperManager.setBitmap(BitmapFactory
                                .decodeFile(imgDecodableString));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case RESULT_CANCELED:
                Toast.makeText(MainActivity.this, R.string.text_cancelled, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        MenuItem item = menu.findItem(R.id.action_on_off);
        if (appOn) {
            item.setTitle(R.string.turn_off);
        } else {
            item.setTitle(R.string.turn_on);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_todo:
                showAddTodoActivity();
                break;
            case R.id.action_sort_priority:
                todoRecyclerAdapter.sortPriority();
                break;
            case R.id.action_on_off:
                if (appOn) {
                    appOn = false;
                    item.setTitle(R.string.turn_on);
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

                } else {
                    appOn = true;
                    item.setTitle(R.string.turn_off);
                    todoRecyclerAdapter.updateBackground();
                }
                break;
            default:
                break;
        }

        return true;
    }

}
