package hello.service;

import hello.model.Orders;
import hello.model.Sample;
import hello.repository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class SampleService {
    static int count = 1;

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private MongoTemplate mt;

    public void createModel(Sample sample) {
      //  return sampleRepository.save(sample);
        sampleRepository.save(new Sample(String.valueOf(getRandomCharacter('a', 'f')), (new Random()).nextInt(10),(new Random()).nextInt(10)));
      //  count++;
    }

    public char getRandomCharacter(char ch1, char ch2) {
        return (char) (ch1 + Math.random() * (ch2 - ch1 + 1));
    }

    public List<Sample> getAll( ) {
        return sampleRepository.findAll();
    }

    public  Sample getByName(String name) {
        return sampleRepository.findByName(name);
    }

    public List<Sample> getSample( ) {
        Criteria cr = Criteria.where("name").is("b");
        Query qu = new Query();
        qu.addCriteria(cr);
        System.out.println(mt.collectionExists("hello"));
        return mt.find(qu, Sample.class)  ;
    }

    public List<Map> performAggregation() {
        MatchOperation matchStage = Aggregation.match(Criteria.where("price").gt(1));
        GroupOperation groupStage = Aggregation.group("name").sum("quantity").as("totalQuantity").sum("price").as("totalCount");
        ProjectionOperation projectStage = Aggregation.project( "totalCount");

        SortOperation sort = Aggregation.sort(Sort.Direction.ASC,"totalQuantity");

        Aggregation aggregation = Aggregation.newAggregation(matchStage, groupStage, sort, projectStage);

        AggregationResults<Map> results = mt.aggregate(aggregation, "sample1", Map.class);

        return results.getMappedResults();
    }

    public List<Orders> performAggregation1() {
        MatchOperation matchStage = Aggregation.match(Criteria.where("price").lt(5));
        GroupOperation groupStage = Aggregation.group("name").sum("quantity").as("totalQuantity");
        ProjectionOperation projectStage = Aggregation.project("item", "product");

        Aggregation aggregation = Aggregation.newAggregation(groupStage);

        AggregationResults<Orders> results = mt.aggregate(aggregation, "orders", Orders.class);

        return results.getMappedResults();
    }
}
