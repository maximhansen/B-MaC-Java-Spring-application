package be.kdg.bakery.messaging.dto;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Setter
public class ResponseDto {
    private UUID orderNumber;
    private Date timeStamp;
    private String status;
    private String message;

    public ResponseDto() {
    }

    public ResponseDto(UUID orderNumber, Date timeStamp, String status, String message) {
        this.orderNumber = orderNumber;
        this.timeStamp = timeStamp;
        this.status = status;
        this.message = message;
    }

    public UUID getOrderNumber() {
        return orderNumber;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "orderNumber=" + orderNumber +
                ", TimeStamp=" + timeStamp +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
