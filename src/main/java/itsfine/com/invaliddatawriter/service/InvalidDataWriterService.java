package itsfine.com.invaliddatawriter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import itsfine.com.invaliddatawriter.documents.ParkObject;
import itsfine.com.invaliddatawriter.repository.InvalidDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.io.IOException;

@EnableBinding(Sink.class)
public class InvalidDataWriterService {

    @Autowired
    private InvalidDataRepository repository;

    ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    @StreamListener(Sink.INPUT)
    public void invalidDataWrite(String carJson) throws IOException {
        ParkObject carData = mapper.readValue(carJson, ParkObject.class);
        repository.save(carData);
    }
}
