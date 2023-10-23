package pp.olena.todo.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pp.olena.todo.dto.CreateTask;
import pp.olena.todo.dto.TaskFilter;
import pp.olena.todo.dto.UpdateTask;
import pp.olena.todo.exception.NoChangesException;
import pp.olena.todo.exception.TaskNotFoundException;
import pp.olena.todo.exception.TaskUpdateForbiddenException;
import pp.olena.todo.persistance.entity.Task;
import pp.olena.todo.persistance.repository.TaskRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TaskServiceTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldReturnTaskWhenIdExists() {
        //given
        Task task = new Task(1L, "Description", "not done", LocalDateTime.now(), null, null);
        taskRepository.save(task);

        //then
        Task created = taskService.getTaskById(1L);
        assertNotNull(created);
        assertEquals(task.getDescription(), created.getDescription());
        assertEquals(task.getStatus(), created.getStatus());
        assertNull(created.getDueTo());
        assertNotNull(created.getCreatedAt());
        assertNull(created.getDoneAt());
    }

    @Test
    void shouldThrowExceptionWhenIdDoesNotExist() {
        //when
        TaskNotFoundException thrown = Assertions.assertThrows(TaskNotFoundException.class,
            () -> taskService.getTaskById(1L));

        //then
        assertEquals("Task with id 1 does not exist.",
            thrown.getMessage());
    }

    @Test
    void shouldCreateTaskWhenDescriptionPresent() {
        //given
        CreateTask createTask = new CreateTask("Description", null);

        //when
        Long id = taskService.createTask(createTask);

        //then
        assertNotNull(id);
        Optional<Task> optional = taskRepository.findById(id);
        assertTrue(optional.isPresent());
        Task task = optional.get();
        assertEquals("Description", task.getDescription());
        assertEquals("not done", task.getStatus());
        assertNotNull(task.getCreatedAt());
        assertNull(task.getDueTo());
        assertNull(task.getDoneAt());
    }

    @Test
    void shouldCreateTaskWhenDescriptionAndDueDateInTheFutureArePresent() {
        //given
        CreateTask createTask = new CreateTask("Description", LocalDateTime.now().plusDays(1));

        //when
        Long id = taskService.createTask(createTask);

        //then
        assertNotNull(id);
        Optional<Task> optional = taskRepository.findById(id);
        assertTrue(optional.isPresent());
        Task task = optional.get();
        assertEquals("Description", task.getDescription());
        assertEquals("not done", task.getStatus());
        assertNotNull(task.getCreatedAt());
        assertNotNull(task.getDueTo());
        assertNull(task.getDoneAt());
    }

    @Test
    void shouldReturnExceptionWhenTaskToUpdateNotFoundById() {
        //given
        UpdateTask updateTask = new UpdateTask("New Description", null);

        //when
        TaskNotFoundException thrown = Assertions.assertThrows(TaskNotFoundException.class,
            () -> taskService.updateTask(1L, updateTask));

        //then
        assertThat(thrown.getMessage()).contains("Task with id 1 does not exist.");
    }

    @Test
    void shouldReturnExceptionWhenTaskToUpdateHasPastDueStatus() {
        //given
        Task task = new Task(1L, "Description", "past due", LocalDateTime.now(), null, null);
        taskRepository.save(task);
        UpdateTask updateTask = new UpdateTask("New Description", null);

        //when
        TaskUpdateForbiddenException thrown = Assertions.assertThrows(TaskUpdateForbiddenException.class,
            () -> taskService.updateTask(1L, updateTask));

        //then
        assertThat(thrown.getMessage()).contains("Task is past due date and is not allowed to be updated.");
    }

    @Test
    void shouldReturnExceptionWhenTaskToUpdateIsPastDueButStatusIsNotYetUpdated() {
        //given
        Task task = new Task(1L, "Description", "not done", LocalDateTime.now(), LocalDateTime.now().minusDays(1),
            null);
        taskRepository.save(task);
        UpdateTask updateTask = new UpdateTask("New Description", null);

        //when
        TaskUpdateForbiddenException thrown = Assertions.assertThrows(TaskUpdateForbiddenException.class,
            () -> taskService.updateTask(1L, updateTask));

        //then
        assertThat(thrown.getMessage()).contains("Task is past due date and is not allowed to be updated.");
    }

    @Test
    void shouldReturnExceptionWhenTaskToUpdateHasNotChanged() {
        //given
        Task task = new Task(1L, "Description", "not done", LocalDateTime.now(), null, null);
        taskRepository.save(task);
        UpdateTask updateTask = new UpdateTask("Description", null);

        //when
        NoChangesException thrown = Assertions.assertThrows(NoChangesException.class,
            () -> taskService.updateTask(1L, updateTask));

        //then
        assertThat(thrown.getMessage()).contains("Task was not updated, no changes detected for the id 1.");
    }

    @Test
    void shouldUpdateTaskWhenFoundAndValid() {
        //given
        Task task = new Task(1L, "Description", "not done", LocalDateTime.now(), LocalDateTime.now().plusDays(1), null);
        taskRepository.save(task);
        UpdateTask updateTask = new UpdateTask("New Description", null);

        //when
        taskService.updateTask(1L, updateTask);

        //then
        Optional<Task> optional = taskRepository.findById(1L);
        assertTrue(optional.isPresent());
        Task updated = optional.get();
        assertEquals("New Description", updated.getDescription());
        assertEquals(task.getDueTo(), updated.getDueTo());
        assertNull(updated.getDoneAt());
    }

    @Test
    void shouldThrowExceptionWhenIdNotFound() {
        //when
        TaskNotFoundException thrown = Assertions.assertThrows(TaskNotFoundException.class,
            () -> taskService.deleteTask(1L));

        //then
        assertThat(thrown.getMessage()).contains("Task with id 1 does not exist.");
    }

    @Test
    void shouldDeleteTaskWhenFoundById() {
        //given
        Task task = new Task(1L, "Description", "not done", LocalDateTime.now(), LocalDateTime.now().plusDays(1), null);
        taskRepository.save(task);

        //when
        taskService.deleteTask(1L);

        //then
        Optional<Task> optional = taskRepository.findById(1L);
        assertTrue(optional.isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenNoTasksFound() {
        //when
        List<Task> tasks = taskService.getAllByFilter(null);

        //then
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }

    @Test
    void shouldReturnAllTasksWhenEmptyFilterFields() {
        //given
        Task task1 = new Task(1L, "Description1", "not done", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
            null);
        Task task2 = new Task(2L, "Description2", "done", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
            LocalDateTime.now());
        taskRepository.saveAll(List.of(task1, task2));

        //when
        List<Task> tasks = taskService.getAllByFilter(null);

        //then
        assertNotNull(tasks);
        assertEquals(2, tasks.size());
    }

    @Test
    void shouldReturnDoneWhenFound() {
        //given
        Task task1 = new Task(1L, "Description1", "not done", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
            null);
        Task task2 = new Task(2L, "Description2", "done", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
            LocalDateTime.now());
        taskRepository.saveAll(List.of(task1, task2));
        TaskFilter filter = new TaskFilter("done", null);

        //when
        List<Task> tasks = taskService.getAllByFilter(filter);

        //then
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        Task foundTask = tasks.get(0);
        assertEquals("Description2", foundTask.getDescription());
        assertEquals("done", foundTask.getStatus());
    }

    @Test
    void shouldReturnNotDoneWhenFound() {
        //given
        Task task1 = new Task(1L, "Description1", "not done", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
            null);
        Task task2 = new Task(2L, "Description2", "done", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
            LocalDateTime.now());
        taskRepository.saveAll(List.of(task1, task2));
        TaskFilter filter = new TaskFilter("not done", null);

        //when
        List<Task> tasks = taskService.getAllByFilter(filter);

        //then
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        Task foundTask = tasks.get(0);
        assertEquals("Description1", foundTask.getDescription());
        assertEquals("not done", foundTask.getStatus());
    }

    @Test
    void shouldReturnDueToTodayWhenTimeIsNotEqual() {
        //given
        Task task1 = new Task(1L, "Description1", "not done", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
            null);
        Task task2 = new Task(2L, "Description2", "done", LocalDateTime.now(), LocalDateTime.now().plusDays(2),
            LocalDateTime.now());
        taskRepository.saveAll(List.of(task1, task2));
        TaskFilter filter = new TaskFilter(null, LocalDateTime.now().plusDays(1).plusMinutes(5));

        //when
        List<Task> tasks = taskService.getAllByFilter(filter);

        //then
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        Task foundTask = tasks.get(0);
        assertEquals("Description1", foundTask.getDescription());
        assertEquals("not done", foundTask.getStatus());
    }

    @Test
    void shouldReturnOneWhenAllFilterFieldsUsed() {
        //given
        Task task1 = new Task(1L, "Description1", "not done", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
            null);
        Task task2 = new Task(2L, "Description2", "done", LocalDateTime.now(), LocalDateTime.now().plusDays(2),
            LocalDateTime.now());
        Task task3 = new Task(3L, "Description3", "done", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
            LocalDateTime.now());
        taskRepository.saveAll(List.of(task1, task2, task3));
        TaskFilter filter = new TaskFilter("done", LocalDateTime.now().plusDays(1).plusMinutes(5));

        //when
        List<Task> tasks = taskService.getAllByFilter(filter);

        //then
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        Task foundTask = tasks.get(0);
        assertEquals("Description3", foundTask.getDescription());
        assertEquals("done", foundTask.getStatus());
    }

    @Test
    void shouldUpdateDoneAtWhenUpdateStatusSetToDone() {
        //given
        Task task = new Task(1L, "Description", "not done", LocalDateTime.now(), LocalDateTime.now().plusDays(1), null);
        taskRepository.save(task);
        UpdateTask updateTask = new UpdateTask(null, "done");

        //when
        taskService.updateTask(1L, updateTask);

        //then
        Optional<Task> optional = taskRepository.findById(1L);
        assertTrue(optional.isPresent());
        Task updated = optional.get();
        assertEquals("Description", updated.getDescription());
        assertNotNull(updated.getDoneAt());
    }

    @Test
    void shouldUpdateDoneAtToNullWhenUpdateStatusSetToNotDone() {
        //given
        Task task = new Task(1L, "Description", "done", LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now().minusDays(1));
        taskRepository.save(task);
        UpdateTask updateTask = new UpdateTask(null, "not done");

        //when
        taskService.updateTask(1L, updateTask);

        //then
        Optional<Task> optional = taskRepository.findById(1L);
        assertTrue(optional.isPresent());
        Task updated = optional.get();
        assertEquals("Description", updated.getDescription());
        assertNull(updated.getDoneAt());
    }
}