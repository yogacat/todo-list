package pp.olena.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pp.olena.todo.persistance.entity.Task;
import pp.olena.todo.persistance.repository.TaskRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PastDueScheduledServiceTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PastDueScheduledService scheduledService;

    @Test
    void shouldChangeStatusToPastDueOnlyWhenTheTaskIsNotDone() {
        //given
        taskRepository.saveAll(createTestDataToCheckPastDue());

        //when
        scheduledService.updatePastDueTasks();

        //then
        Optional<Task> optional1 = taskRepository.findById(1L);
        Optional<Task> optional2 = taskRepository.findById(2L);
        Optional<Task> optional3= taskRepository.findById(3L);
        Optional<Task> optional4 = taskRepository.findById(4L);
        assertTrue(optional1.isPresent());
        assertTrue(optional2.isPresent());
        assertTrue(optional3.isPresent());
        assertTrue(optional4.isPresent());

        Task task1 = optional1.get();
        assertThat(task1.getStatus()).isEqualTo("not done");
        assertThat(task1.getDueTo()).isNull();
        assertThat(task1.getDoneAt()).isNull();

        Task task2 = optional2.get();
        assertThat(task2.getStatus()).isEqualTo("past due");
        assertThat(task2.getDueTo()).isNotNull();
        assertThat(task2.getDoneAt()).isNull();


        Task task3 = optional3.get();
        assertThat(task3.getStatus()).isEqualTo("past due");
        assertThat(task3.getDueTo()).isNotNull();
        assertThat(task3.getDoneAt()).isNull();

        Task task4 = optional4.get();
        assertThat(task4.getStatus()).isEqualTo("done");
        assertThat(task4.getDueTo()).isNotNull();
        assertThat(task4.getDoneAt()).isNotNull();
    }

    private List<Task> createTestDataToCheckPastDue() {
        Task taskNoDueTo = new Task(1L, "No Due to", "not done", LocalDateTime.now(), null, null);
        Task taskPastDue = new Task(2L, "Past due", "not done", LocalDateTime.now(),
            LocalDateTime.now().minusMinutes(5), null);
        Task taskAlreadyMarkedPastDue = new Task(3L, "Already Marked past due", "past due", LocalDateTime.now(),
            LocalDateTime.now().minusMinutes(5), null);
        Task taskDone = new Task(4L, "Done", "done", LocalDateTime.now(), LocalDateTime.now().minusMinutes(5),
            LocalDateTime.now().minusMinutes(5));

        return List.of(taskNoDueTo, taskPastDue, taskAlreadyMarkedPastDue, taskDone);
    }
}