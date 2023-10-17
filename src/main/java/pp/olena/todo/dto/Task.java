package pp.olena.todo.dto;

import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

//todo validation
public record Task(String description, Status status, @CreatedDate LocalDateTime createdAt, LocalDateTime dueTo, LocalDateTime doneAt) {

}
