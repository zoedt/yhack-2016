package hu.ait.android.screenreminder.data;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Todo extends SugarRecord implements Serializable, Comparable<Todo> {
    private String todo;
    private boolean done;
    private int priority;

    public Todo() {
    }

    public Todo(String todo, boolean done, int priority) {
        this.todo = todo;
        this.done = done;
        this.priority = priority;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Todo another) {
        //ascending order
        return this.getPriority() - another.getPriority();
    }
}
