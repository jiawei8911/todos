package todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/todos")
public class TodoController {
    @Autowired
    TodoRepository todoRepository;

    @RequestMapping(method = GET)
    public Iterable<Todo> showTodoList() {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:app.xml");
        todoRepository = context.getBean(TodoRepository.class);
        Iterable<Todo> all = todoRepository.findAll();
        context.close();
        return all;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Todo showATodo(@PathVariable(value = "id") Long id) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:app.xml");
        todoRepository = context.getBean(TodoRepository.class);
        Todo todo = todoRepository.findOne(id);
        context.close();
        return todo;
    }

    @RequestMapping(method = POST)
    public Todo create(@RequestBody Todo newTodo) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:app.xml");
        todoRepository = context.getBean(TodoRepository.class);
        Todo todo = todoRepository.save(newTodo);
        context.close();
        return todo;
    }

    @RequestMapping(value = "/{id}" ,method = DELETE)
    public void delete(@PathVariable("id") Long id){
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:app.xml");
        todoRepository = context.getBean(TodoRepository.class);
        todoRepository.delete(id);
        context.close();
    }

    @RequestMapping(value = "/{id}" , method = PUT)
    public Todo update(@RequestBody Todo updateTodo, @PathVariable("id") Long id){
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:app.xml");
        todoRepository = context.getBean(TodoRepository.class);
        if(id != updateTodo.getId()){
            todoRepository.delete(id);
        }
        Todo updatedList = todoRepository.save(updateTodo);
        context.close();
        return updatedList;
    }
}
