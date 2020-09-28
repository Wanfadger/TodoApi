package com.wanfadger.todo.controllers;


import com.wanfadger.todo.models.Todo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TodoController {

    private List<Todo> myBTodos = new ArrayList();
    private final AtomicLong counter = new AtomicLong();

    public List<Todo> TodoListController(){
        myBTodos.add(new Todo(counter.incrementAndGet() , "Visit Colosseum in Rome"));
        return   myBTodos;
    }

    @GetMapping(value = "/")
    public ResponseEntity index() {
        return ResponseEntity.ok(myBTodos);
    }
    @GetMapping(value = "/todo/{id}")
    public ResponseEntity getBucket(@PathVariable(value="id") Long id) {
        Todo foundTodo = null;
        for(Todo todo : myBTodos){
            if(todo.getId() == id)
                foundTodo = todo;
        }
        return ResponseEntity.ok(foundTodo);
    }
    @PostMapping(value = "/")
    public ResponseEntity addToBucketList(@RequestBody Todo todo) {
        todo.setId(counter.incrementAndGet());
        myBTodos.add(todo);
        return ResponseEntity.ok(todo);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity updateBucketList(@PathVariable long id , @RequestBody Todo todo) {
        todo.setId(id);
        myBTodos.forEach( todo1 -> {
            if (todo.getId() == id)
                myBTodos.add(todo);
        } );
        return ResponseEntity.ok(todo);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity removeBucketList(@PathVariable(value="id") Long id) {
        Todo todoToRemove = null;
        for(Todo todo : myBTodos){
            if(todo.getId() == id)
                todoToRemove = todo;
        }
        myBTodos.remove(todoToRemove);
        return ResponseEntity.ok(myBTodos);
    }

}
