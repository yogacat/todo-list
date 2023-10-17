package pp.olena.todo.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
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
import pp.olena.todo.dto.Status;
import pp.olena.todo.dto.Task;

@RestController
@RequestMapping("/api")
@Slf4j
public class TodoListController {

    /**
     * Get details of a specific item.
     * @param id Long id of the Task.
     */
    @GetMapping(value = "/tasks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTask(@PathVariable @NotBlank Long id) {
        //get a task - todo implementation
        return ResponseEntity.status(HttpStatus.OK).body("Future task");
    }

    /**
     * Create a task.
     * @param task {@link Task}
     */
    @PostMapping(value = "/tasks", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createTask(@RequestBody @Valid @NotNull Task task) {
        //create a task - todo implementation
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Allows to change description of an item, mark an item as "done", mark an item as "not done".
     * @param task {@link Task}
     */
    @PutMapping(value = "/tasks", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updateTask(@RequestBody @Valid @NotNull Task task) {
        //update a task - todo implementation
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = "/tasks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteTask(@PathVariable @NotBlank Long id) {
        //delete a task - todo implementation
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTasks() {
        //get all tasks - todo implementation
        return ResponseEntity.status(HttpStatus.OK).body("Future list of tasks");
    }

    @GetMapping(value = "/tasks/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTasks(@PathVariable @NotBlank Status status) {
        //get all tasks - todo implementation - consider using some kind of filter
        return ResponseEntity.status(HttpStatus.OK).body("Future list of tasks");
    }
}
