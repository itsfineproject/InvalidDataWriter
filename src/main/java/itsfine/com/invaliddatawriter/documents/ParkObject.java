package itsfine.com.invaliddatawriter.documents;

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
    private ObjectId _id;
    private int parking_id;
    private String car_number;
    private LocalDateTime date_time;
}
