package pp.olena.todo.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pp.olena.todo.persistance.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
