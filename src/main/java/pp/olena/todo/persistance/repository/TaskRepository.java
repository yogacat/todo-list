package pp.olena.todo.persistance.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pp.olena.todo.persistance.entity.Task;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAll(Specification<Task> spec);

    @Query("SELECT t FROM Task t WHERE t.status = 'not done' AND t.dueTo < CURRENT_TIMESTAMP")
    List<Task> findAllWithDueToBeforeNowAndStatusNotDone();
}
