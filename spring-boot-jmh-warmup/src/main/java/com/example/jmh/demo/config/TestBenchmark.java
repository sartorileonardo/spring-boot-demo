package com.example.jmh.demo.config;

import com.example.jmh.demo.service.MessageService;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@State(Scope.Benchmark)
@Threads(1)
public class TestBenchmark {

    @Benchmark
    @BenchmarkMode(Mode.All)
    @Setup(Level.Iteration)
    @Fork(warmups = 1, value = 1)
    @Warmup(batchSize = -1, iterations = 10, time = 1, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(batchSize = -1, iterations = 10, time = 1, timeUnit = TimeUnit.MILLISECONDS)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void run(Blackhole bh) throws Exception {
        bh.consume(new MessageService().getMessages());
    }

    @Bean
    public void benchmark() throws Exception {
        String[] argv = {};
        org.openjdk.jmh.Main.main(argv);
    }

}