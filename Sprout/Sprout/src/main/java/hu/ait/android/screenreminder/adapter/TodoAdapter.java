package hu.ait.android.screenreminder.adapter;


import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.ait.android.screenreminder.MainActivity;
import hu.ait.android.screenreminder.R;
import hu.ait.android.screenreminder.data.Todo;

public class TodoAdapter
        extends RecyclerView.Adapter<TodoAdapter.ViewHolder>
        implements TodoTouchHelperAdapter {

    private Context context;
    private List<Todo> todos = new ArrayList<Todo>();

    public TodoAdapter(Context context) {
        this.context = context;
        todos = Todo.listAll(Todo.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_list_row, parent, false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvTodo.setText(todos.get(position).getTodo());
        holder.cbDone.setChecked(todos.get(position).isDone());
        switch (todos.get(position).getPriority()) {
            case 0:
                holder.rlRow.setBackgroundColor(Color.parseColor("#c0392b"));
                break;
            case 1:
                holder.rlRow.setBackgroundColor(Color.parseColor("#f1c40f"));
                break;
            case 2:
                holder.rlRow.setBackgroundColor(Color.parseColor("#16a085"));
                break;
        }

        holder.cbDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Todo todo = todos.get(position);
                todo.setDone(holder.cbDone.isChecked());
                todo.save();
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).showEditTodoActivity(todos.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void updateBackground() {
        if (MainActivity.appOn) {
            // Get display details
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int w = size.x;
            int h = size.y;

            // Create the bitmap and draw the background
            Bitmap.Config conf = Bitmap.Config.ARGB_8888;
            Bitmap bmp = Bitmap.createBitmap(w, h, conf);
            Canvas canvas = new Canvas(bmp);
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPaint(paint);

            // Set each text segment
            int newLineY = 0;
            int counter = 1;
            int margin = 5;
            for (Todo todo : todos) {
                String done =
                        (todo.isDone()) ? context.getString(R.string.done) :
                                context.getString(R.string.not_done);
                String todoTxt = counter + ". " + todo.getTodo() + ": " + done;
                int width = w / 20;
                int height = h / 5 + newLineY;

                switch (todo.getPriority()) {
                    case 0:
                        paint.setColor(Color.parseColor("#c0392b"));
                        break;
                    case 1:
                        paint.setColor(Color.parseColor("#f1c40f"));
                        break;
                    case 2:
                        paint.setColor(Color.parseColor("#16a085"));
                        break;
                }

                paint.setStyle(Paint.Style.FILL);
                Paint.FontMetrics fm = new Paint.FontMetrics();
                paint.setTextSize(h / 40);
                paint.getFontMetrics(fm);
                canvas.drawRect(width - margin, height + fm.top - margin,
                        width + paint.measureText(todoTxt) + margin, height + fm.bottom
                                + margin, paint);

                paint.setColor(Color.BLACK);
                canvas.drawText(todoTxt, width, height, paint);

                newLineY += h / 25;
                counter++;
            }

            // Set the wallpaper
            WallpaperManager myWallpaperManager
                    = WallpaperManager.getInstance(context.getApplicationContext());
            try {
                myWallpaperManager.setBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addTodo(Todo todo) {
        todos.add(0, todo);
        todo.save();
        notifyDataSetChanged();
        updateBackground();
    }

    public void removeTodo(int position) {
        todos.get(position).delete();
        todos.remove(position);
        notifyItemRemoved(position);
        updateBackground();
    }

    public void updateTodo(int index, Todo todo) {
        todos.set(index, todo);
        todo.save();
        notifyItemChanged(index);
        updateBackground();
    }


    public void sortPriority() {
        Collections.sort(todos);
        for (Todo todo : todos) {
            todo.save();
        }
        notifyDataSetChanged();
        updateBackground();
    }

    @Override
    public void onItemDismiss(int position) {
        removeTodo(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(todos, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(todos, i, i - 1);
            }
        }
        for (Todo todo : todos) {
            todo.save();
        }
        notifyItemMoved(fromPosition, toPosition);
        updateBackground();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTodo;
        private CheckBox cbDone;
        private Button btnEdit;
        private RelativeLayout rlRow;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTodo = (TextView) itemView.findViewById(R.id.tvTodo);
            cbDone = (CheckBox) itemView.findViewById(R.id.cbDone);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            rlRow = (RelativeLayout) itemView.findViewById(R.id.rlRow);
        }
    }
}
