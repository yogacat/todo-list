package pp.olena.todo.controller;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pp.olena.todo.dto.CreateTask;
import pp.olena.todo.dto.UpdateTask;
import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TodoListControllerValidationTest {

    @Autowired
    private TodoListController controller;

    @Test
    void shouldThrowValidationExceptionWhenCreateTaskIsNull() {
        //when
        ValidationException thrown = Assertions.assertThrows(ValidationException.class,
            () -> controller.createTask(null));

        //then
        assertThat(thrown.getMessage())
            .contains(
                "createTask.task: Task cannot be created, description must be present and due date must be in the future if present.");
    }

    @Test
    void shouldThrowValidationExceptionWhenCreateTaskDueDateIsInThePast() {
        //given
        CreateTask createTask = new CreateTask("Description", LocalDateTime.now().minusDays(1));

        //when
        ValidationException thrown = Assertions.assertThrows(ValidationException.class,
            () -> controller.createTask(createTask));

        //then
        assertThat(thrown.getMessage())
            .contains(
                "createTask.task: Task cannot be created, description must be present and due date must be in the future if present.");
    }

    @Test
    void shouldThrowValidationExceptionWhenCreateTaskDescriptionIsNull() {
        //given
        CreateTask createTask = new CreateTask(null, null);

        //when
        ValidationException thrown = Assertions.assertThrows(ValidationException.class,
            () -> controller.createTask(createTask));

        //then
        assertThat(thrown.getMessage())
            .contains(
                "createTask.task: Task cannot be created, description must be present and due date must be in the future if present.");
    }

    @Test
    void shouldThrowValidationExceptionWhenCreateTaskDescriptionIsEmpty() {
        //given
        CreateTask createTask = new CreateTask("", null);

        //when
        ValidationException thrown = Assertions.assertThrows(ValidationException.class,
            () -> controller.createTask(createTask));

        //then
        assertThat(thrown.getMessage())
            .contains(
                "createTask.task: Task cannot be created, description must be present and due date must be in the future if present.");
    }

    @Test
    void shouldThrowValidationExceptionWhenUpdateTaskIdIsNull() {
        //given
        UpdateTask updateTask = new UpdateTask("New Description", null);

        //when
        ValidationException thrown = Assertions.assertThrows(ValidationException.class,
            () -> controller.updateTask(null, updateTask));

        //then
        assertThat(thrown.getMessage())
            .contains(
                "updateTask.id: must not be null");
    }

    @Test
    void shouldThrowValidationExceptionWhenUpdateTaskIsNull() {
        //when
        ValidationException thrown = Assertions.assertThrows(ValidationException.class,
            () -> controller.updateTask(1L, null));

        //then
        assertThat(thrown.getMessage())
            .contains(
                "updateTask.task: Task cannot be updated, either description or status must be present. Status can be done or not done.");
    }

    @Test
    void shouldThrowValidationExceptionWhenUpdateTaskDescriptionIsNull() {
        //given
        UpdateTask updateTask = new UpdateTask(null, null);

        //when
        ValidationException thrown = Assertions.assertThrows(ValidationException.class,
            () -> controller.updateTask(1L, updateTask));

        //then
        assertThat(thrown.getMessage())
            .contains(
                "updateTask.task: Task cannot be updated, either description or status must be present. Status can be done or not done.");
    }

    @Test
    void shouldThrowValidationExceptionWhenUpdateTaskDescriptionIsEmpty() {
        //given
        UpdateTask updateTask = new UpdateTask("", null);

        //when
        ValidationException thrown = Assertions.assertThrows(ValidationException.class,
            () -> controller.updateTask(1L, updateTask));

        //then
        assertThat(thrown.getMessage())
            .contains(
                "updateTask.task: Task cannot be updated, either description or status must be present. Status can be done or not done.");
    }

    @Test
    void shouldThrowValidationExceptionWhenUpdateTaskStatusIsInvalid() {
        //given
        UpdateTask updateTask = new UpdateTask("", "wrong status");

        //when
        ValidationException thrown = Assertions.assertThrows(ValidationException.class,
            () -> controller.updateTask(1L, updateTask));

        //then
        assertThat(thrown.getMessage())
            .contains(
                "updateTask.task: Task cannot be updated, either description or status must be present. Status can be done or not done.");
    }

    @Test
    void shouldThrowValidationExceptionWhenUpdateTaskStatusIsNotAllowed() {
        //given
        UpdateTask updateTask = new UpdateTask("", "past due");

        //when
        ValidationException thrown = Assertions.assertThrows(ValidationException.class,
            () -> controller.updateTask(1L, updateTask));

        //then
        assertThat(thrown.getMessage())
            .contains(
                "updateTask.task: Task cannot be updated, either description or status must be present. Status can be done or not done.");
    }
}