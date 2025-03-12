package org.example.homework001.model.Ticket.ApiReponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private HttpStatus status;
    private T payLoad;
    private LocalDateTime timestamp;
    private int offset;      // Starting point
    private int limit;       // Max items to return
    private long totalItems;

}
