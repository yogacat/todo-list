package pp.olena.todo.dto;

import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

/**
 * Data object that I will return in the controller to the requestor.
 * @param id Long id of the task
 * @param description String
 * @param status {@link Status}
 * @param createdAt LocalDateTime
 * @param dueTo LocalDateTime
 * @param doneAt LocalDateTime
 */
public record Task(Long id, String description, Status status, @CreatedDate LocalDateTime createdAt,
                   LocalDateTime dueTo, LocalDateTime doneAt) {

}
