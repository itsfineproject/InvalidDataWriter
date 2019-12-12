package itsfine.com.invaliddatawriter.documents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Document(collection = "wrongdata")
public class ParkObject {

    @Id
    @JsonIgnore
    private ObjectId _id;
    private int parking_id;
    private String car_number;
    private LocalDateTime date_time;

    public ParkObject(int parking_id, String car_number, LocalDateTime date_time) {
        this.parking_id = parking_id;
        this.car_number = car_number;
        this.date_time = date_time;
    }
}
