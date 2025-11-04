package hello.controller;

import hello.model.Greeting;
import hello.model.Orders;
import hello.model.Sample;
import hello.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    SampleService sampleService;

    @RequestMapping("/")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping("/test")
    public String test() {
        sampleService.createModel(null);
        List<Sample> samples = sampleService.getAll( );
        return "Hello!" + samples;
    }

    @RequestMapping("/test1")
    public String test1() {
        Sample samples = sampleService.getByName("b") ;
        return "Hello!" + samples;
    }

    @RequestMapping("/test2")
    public String test2() {
        List<Sample> samples = sampleService.getSample() ;
        samples.forEach(e-> System.out.println(e));
        return "Hello!" + samples;
    }

    @RequestMapping("/test3")
    public String test3() {
        List<Map> samples = sampleService.performAggregation() ;
        samples.forEach(e-> System.out.println(e));
        return "Hello!" + samples;
    }

    @RequestMapping("/test4")
    public String test4() {
        List<Orders> samples = sampleService.performAggregation1() ;
        samples.forEach(e-> System.out.println(e));
        return "Hello!" + samples;
    }

}
