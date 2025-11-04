package hello.repository;

import hello.model.Sample;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SampleRepository extends MongoRepository<Sample, String> {
    // Custom query methods can be added here
    Sample findByName(String name);

}
