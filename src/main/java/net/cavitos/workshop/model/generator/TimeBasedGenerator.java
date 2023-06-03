package net.cavitos.workshop.model.generator;

import com.fasterxml.uuid.Generators;

public class TimeBasedGenerator {

    private TimeBasedGenerator() {        
    }
    
    public static String generateTimedUUID() {

        return Generators.timeBasedGenerator()
            .generate()
            .toString();
    }
}
