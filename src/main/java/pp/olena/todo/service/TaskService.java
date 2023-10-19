package pp.olena.todo.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import pp.olena.todo.dto.CreateTask;
import pp.olena.todo.dto.Status;
import pp.olena.todo.dto.TaskFilter;
import pp.olena.todo.dto.UpdateTask;
import pp.olena.todo.exception.TaskNotFoundException;
import pp.olena.todo.exception.TaskUpdateForbidden;
import pp.olena.todo.persistance.entity.Task;
import pp.olena.todo.persistance.repository.TaskRepository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Contains business logic for the API. Performs permission checks, checks if entity exists before deleting/updating
 * it.
 */
@Service
public class TaskService {

    private static final String ERROR_MSG_TASK_NOT_FOUND = "Task with id {} does not exist.";

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Returns a task by the id if it exists. Otherwise, will return 404 exception.
     * @param id Long
     * @return Task
     */
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(replacePlaceholder(ERROR_MSG_TASK_NOT_FOUND, id)));
    }

    /**
     * Creates a task in the status "not done".
     * @param createTask {@link CreateTask}
     * @return Long id of the created task
     */
    @Transactional
    public Long createTask(CreateTask createTask) {
        Task task = new Task();
        task.setDescription(createTask.description());
        task.setDueTo(createTask.dueTo());
        task.setStatus(Status.NOT_DONE.getValue());
        taskRepository.save(task);
        return task.getId();
    }

    /**
     * Updates the task entity if it was allowed.
     * @param id Long id of the task
     * @param updateTask {@link UpdateTask}
     */
    @Transactional
    public void updateTask(Long id, UpdateTask updateTask) {
        Task task = taskRepository.findById(id)
            .orElseThrow(
                () -> new TaskNotFoundException(replacePlaceholder(ERROR_MSG_TASK_NOT_FOUND, id)));
        if (isAllowedToUpdate(task)) {
            if (updateTask.description() != null) {
                task.setDescription(updateTask.description());
            }
            if (updateTask.status() != null) {
                task.setStatus(updateTask.status());
            }

            taskRepository.save(task);
        } else {
            throw new TaskUpdateForbidden("Task is past due date and is not allowed to be updated.");
        }
    }

    /**
     * Deletes a task if it was found.
     * @param id Long id of the task.
     */
    @Transactional
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(replacePlaceholder(ERROR_MSG_TASK_NOT_FOUND, id)));
        taskRepository.delete(task);
    }

    /**
     * Returns all the tasks by filter, if all the filter fields are empty all the tasks will be returned.
     * @param filter {@link TaskFilter}
     * @return collection of {@link Task}
     */
    public List<Task> getAllByFilter(TaskFilter filter) {
        Task taskExample = new Task();
        taskExample.setStatus(filter.status());
        //todo it's better to cut time here, but then an example might not work
        taskExample.setDueTo(filter.dueTo());

        return taskRepository.findAll(Example.of(taskExample));
    }

    private static String replacePlaceholder(String message, Long id) {
        return message.replace("{}", id.toString());
    }

    private boolean isAllowedToUpdate(Task task) {
        return !Status.PAST_DUE.getValue().equals(task.getStatus())
            && (task.getDueTo() == null || !task.getDueTo().isBefore(LocalDateTime.now()));
    }
}
