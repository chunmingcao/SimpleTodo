package com.grand.simpletodo.modules;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by caocm_000 on 1/15/2016.
 */
@Table(name = "TodoItem")
public class TodoItem extends Model{
    public enum Priority{
        LOW, MEDIUM, HEIGH
    }
    @Column
    private String name;
    @Column
    private Date dueDate;
    @Column
    private Priority priority;
    @Column
    private String todoNote;

    public TodoItem(){
        super();
    }

    public TodoItem(String name){
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getTodoNote() {
        return todoNote;
    }

    public void setTodoNote(String todoNote) {
        this.todoNote = todoNote;
    }
}
