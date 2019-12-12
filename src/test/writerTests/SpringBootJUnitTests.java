package writerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import itsfine.com.invaliddatawriter.documents.ParkObject;
import itsfine.com.invaliddatawriter.service.InvalidDataWriterService;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import java.io.IOException;
import java.time.LocalDateTime;

@SpringBootApplication(scanBasePackages = {"itsfine.com.invaliddatawriter"})
@EnableMongoRepositories(basePackages = "itsfine.com.invaliddatawriter.repository")
@ComponentScan(basePackages = "itsfine.com.invaliddatawriter.documents")
@ComponentScan(basePackages = "itsfine.com.invaliddatawriter.service")
class SpringBootJUnitTests {

    ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
    ConfigurableApplicationContext context;
    InvalidDataWriterService service;

    public static final int PARKING_ID = 123;
    public static final String CAR_NUMBER = "123-456";
    public static final LocalDateTime DATE_TIME = LocalDateTime.now();
    ParkObject newCar = new ParkObject(PARKING_ID, CAR_NUMBER, DATE_TIME);

    //    MongoClient starting
    ConnectionString connString = new ConnectionString(
            "mongodb+srv://root:root@cluster0-412l4.mongodb.net/invaliddata?retryWrites=true&w=majority");
    MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connString)
            .retryWrites(true)
            .build();
    MongoClient mongoClient = MongoClients.create(settings);
    MongoDatabase database = mongoClient.getDatabase("invaliddata");
    MongoCollection<Document> wrongDataCollection = database.getCollection("wrongdata");

    @BeforeEach
    void setUp() throws IOException {
        context = SpringApplication.run(SpringBootJUnitTests.class);
        service = context.getBean(InvalidDataWriterService.class);
    }

    @AfterEach
    void tearDown() {
        String query = "{$and : [{parking_id : " + PARKING_ID + "}, {car_number : '" + CAR_NUMBER + "'}]}";
        wrongDataCollection.deleteOne(BasicDBObject.parse(query));
        context.close();
    }

    @Test
    void chechWrite() throws IOException {
        Assertions.assertEquals(true, service.toDbWrite(mapper.writeValueAsString(newCar)));
    }

    @Test
    void checkWrittenToDb() throws IOException {
        service.toDbWrite(mapper.writeValueAsString(newCar));
        String query = "{$and : [{parking_id : " + PARKING_ID + "}, {car_number : '" + CAR_NUMBER + "'}]}";
        Document received = null;
        if (wrongDataCollection.find(BasicDBObject.parse(query)) != null)
            received = wrongDataCollection.find(BasicDBObject.parse(query)).first();
        assert (received != null);
    }
}
