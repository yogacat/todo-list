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
    void shouldThrowValidationExceptionWhenDueDateIsInThePast() {
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
    void shouldThrowValidationExceptionWhenDescriptionIsNull() {
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
    void shouldThrowValidationExceptionWhenDescriptionIsEmpty() {
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
}