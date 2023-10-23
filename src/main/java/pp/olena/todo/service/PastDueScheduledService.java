package pp.olena.todo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pp.olena.todo.condition.DisableSchedulingCondition;
import pp.olena.todo.dto.Status;
import pp.olena.todo.persistance.entity.Task;
import pp.olena.todo.persistance.repository.TaskRepository;
import java.util.List;

/**
 * Checks if the Task is past due and updates its status.
 */

@Service
@Slf4j
public class PastDueScheduledService {

    private final TaskRepository taskRepository;

    @Autowired
    public PastDueScheduledService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Scheduled(fixedDelayString = "${todo.scheduled.update.pastDueInterval.milliseconds}")
    @Conditional(DisableSchedulingCondition.class)
    public void updatePastDueTasks() {
        List<Task> pastDueTasks = taskRepository.findAllWithDueToBeforeNowAndStatusNotDone();
        if (!pastDueTasks.isEmpty()) {
            pastDueTasks.forEach(t -> t.setStatus(Status.PAST_DUE.getValue()));
            taskRepository.saveAll(pastDueTasks);
            log.info("Set {} tasks to past due status", pastDueTasks.size());
        }
    }
}
