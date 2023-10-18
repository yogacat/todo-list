package pp.olena.todo.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pp.olena.todo.dto.Task;
import pp.olena.todo.dto.TaskFilter;
import pp.olena.todo.dto.TaskId;
import pp.olena.todo.service.TaskService;
import pp.olena.todo.validation.CreateTask;
import pp.olena.todo.validation.UpdateTask;
import java.util.List;

/**
 * Controller for the API, allows to create/update/edit/delete and retrieve items.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class TodoListController {

    private final TaskService taskService;

    @Autowired
    public TodoListController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Get details of a specific item.
     * @param id Long id of the Task.
     * @return ResponseEntity
     */
    @GetMapping(value = "/tasks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> getTask(@PathVariable @NotBlank Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    /**
     * Create a task.
     * @param task {@link Task}
     * @return ResponseEntity
     */
    @PostMapping(value = "/tasks", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<TaskId> createTask(@RequestBody @CreateTask @NotNull Task task) {
        TaskId taskId = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskId);
    }

    /**
     * Updates a task. Allows to change description of an item, mark an item as "done", mark an item as "not done". Will
     * not change an item that is "past due".
     * @param task {@link Task}
     * @return ResponseEntity
     */
    @PutMapping(value = "/tasks", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updateTask(@RequestBody @UpdateTask @NotNull Task task) {
        taskService.updateTask(task);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Deletes a task by id.
     * @param id Long id of the task to delete
     * @return ResponseEntity
     */
    @DeleteMapping(value = "/tasks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteTask(@PathVariable @NotBlank Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Get all tasks by filter parameters.
     * @param filter {@link TaskFilter}
     * @return ResponseEntity
     */
    @GetMapping(value = "/tasks/filter/{filter}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Task>> getAllTasks(@PathVariable @NotNull TaskFilter filter) {
        List<Task> tasks = taskService.getAllByFilter(filter);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }
}
