package io.rcardin.swagger.idl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SwaggerIdlServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SwaggerIdlServerApplication.class, args);
	}

	@RestController
	@RequestMapping("/tasks")
	public static class TaskController {
		
		private final Logger logger = LoggerFactory.getLogger(getClass());
		
		@PostMapping
		public void create(@RequestBody Task task) {
			logger.info("Created task {}", task);
		}
		
		@GetMapping("/{id}")
		public Task findById(@PathVariable String id) {
			logger.info("Requested task with id {}", id);
			return new Task(id, "Some description");
		}
		
		@DeleteMapping("/{id}")
		public void delete(@PathVariable String id) {
			logger.info("Removed task with id {}", id);
		}
	}
	
	public static class Task {
		private final String id;
		private final String description;
		
		@JsonCreator
		public Task(
				@JsonProperty("id") String id,
				@JsonProperty("description") String description) {
			this.id = id;
			this.description = description;
		}
		
		public String getId() {
			return id;
		}
		
		public String getDescription() {
			return description;
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			Task task = (Task) o;
			return Objects.equals(id, task.id) &&
								 Objects.equals(description, task.description);
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(id, description);
		}
		
		@Override
		public String toString() {
			return "Task{" +
								 "id='" + id + '\'' +
								 ", description='" + description + '\'' +
								 '}';
		}
	}
}
