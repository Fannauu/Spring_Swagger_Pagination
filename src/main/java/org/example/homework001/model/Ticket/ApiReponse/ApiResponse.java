package org.example.homework001.model.Ticket.ApiReponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private HttpStatus status;
    private T payLoad;
    private LocalDateTime timestamp;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    // Starting point
    private int offset;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int limit;
    // Max items to return
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long totalItems;

    public ApiResponse(boolean success, String message, HttpStatus status, T payLoad, LocalDateTime timestamp, int offset, int limit, long totalItems) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.payLoad = payLoad;
        this.timestamp = timestamp;
        this.offset = offset;
        this.limit = limit;
        this.totalItems = totalItems;
    }

    public ApiResponse(boolean success, String message, HttpStatus status, T payload, LocalDateTime timestamp) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.payLoad = payload;
        this.timestamp = timestamp;
    }

}
