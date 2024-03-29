package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller // This means that this class is a Controller
@RequestMapping(path="/todo") // This means URL's start with /demo (after Application path)
public class TodoController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private TodoRepository todoRepository;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addTodo (@RequestParam String name
            , @RequestParam Boolean completed) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        TodoList todo = new TodoList();
        todo.setName(name);
        todo.setCompleted(completed);
        todoRepository.save(todo);
        return "Saved";
    }

    @PostMapping(path="/update") // Map ONLY POST Requests
    public @ResponseBody String updateTodo (@RequestParam Integer id,
                                            @RequestParam String name,
                                            @RequestParam Boolean completed) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        if (todoRepository.findById(id).isPresent())  {
            TodoList todo = todoRepository.findById(id).get();
            todo.setName(name);
            todo.setCompleted(completed);
            todoRepository.save(todo);
            return "Saved";
        } else {
            return "id is not present";
        }
    }

    @PostMapping(path="/delete") // Map ONLY POST Requests
    public @ResponseBody String deleteTodo (@RequestParam Integer id){
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        if (todoRepository.existsById(id))  {
            todoRepository.deleteById(id);
            return "Deleted";
        } else {
            return "id is not present";
        }
    }



    @GetMapping(path="/all")
    public @ResponseBody Iterable<TodoList> getAllTodos() {
        // This returns a JSON or XML with the users
        return todoRepository.findAll();
    }
}
