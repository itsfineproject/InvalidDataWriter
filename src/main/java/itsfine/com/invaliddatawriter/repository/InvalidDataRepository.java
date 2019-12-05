package itsfine.com.invaliddatawriter.repository;

import itsfine.com.invaliddatawriter.documents.ParkObject;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvalidDataRepository extends MongoRepository<ParkObject, ObjectId> {
}
