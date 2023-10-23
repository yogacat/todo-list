package pp.olena.todo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pp.olena.todo.dto.CreateTask;
import pp.olena.todo.dto.TaskId;
import pp.olena.todo.dto.UpdateTask;
import pp.olena.todo.persistance.entity.Task;
import pp.olena.todo.persistance.repository.TaskRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TodoListControllerTest {

    @Autowired
    private TodoListController controller;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldGetTaskWhenFound() {
        //given
        Task task = new Task(1L, "Description", "not done", LocalDateTime.now(), null, null);
        taskRepository.save(task);

        //when
        ResponseEntity<Task> response = controller.getTask(1L);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldCreateTaskWhenValidInput() {
        //given
        CreateTask createTask = new CreateTask("Description", LocalDateTime.now().plusDays(1));

        //when
        ResponseEntity<TaskId> response = controller.createTask(createTask);

        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertThat(response.getBody()).isNotNull();
        Long id = response.getBody().id();
        assertTrue(taskRepository.findById(id).isPresent());
    }

    @Test
    void shouldUpdateTaskWhenValidInput() {
        Task task = new Task(1L, "Description", "not done", LocalDateTime.now(), null, null);
        taskRepository.save(task);
        UpdateTask updateTask = new UpdateTask("New description", null);

        //when
        ResponseEntity<?> response = controller.updateTask(1L, updateTask);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody()).isNull();
        Optional<Task> optional = taskRepository.findById(1L);
        assertTrue(optional.isPresent());
        assertEquals("New description", optional.get().getDescription());
    }

    @Test
    void shouldDeleteTaskWhenValidInput() {
        Task task = new Task(1L, "Description", "not done", LocalDateTime.now(), null, null);
        taskRepository.save(task);

        //when
        ResponseEntity<?> response = controller.deleteTask(1L);

        //then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertThat(response.getBody()).isNull();
        Optional<Task> optional = taskRepository.findById(1L);
        assertTrue(optional.isEmpty());
    }

    @Test
    void shouldReturnAllTasksWhenNoFilter() {
        Task task1 = new Task(1L, "Description1", "not done", LocalDateTime.now(), null, null);
        Task task2 = new Task(2L, "Description2", "done", LocalDateTime.now(), null, LocalDateTime.now());
        taskRepository.saveAll(List.of(task1, task2));

        //when
        ResponseEntity<List<Task>> response = controller.getAllTasks(null);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody()).isNotNull();
        List<Task> foundTasks = response.getBody();
        assertEquals(2, foundTasks.size());
    }
}