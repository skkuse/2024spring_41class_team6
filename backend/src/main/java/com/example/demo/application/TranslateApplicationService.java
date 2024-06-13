package com.example.demo.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TranslateApplicationService {
    @Value("${indics.train_per_km}")
    private float train_per_km;
    
    @Value("${indics.flight_per_km}")
    private float flight_per_km;
    
    @Value("${indics.netflix_per_h}")
    private float netflix_per_h;
    
    @Value("${indics.google_search}")
    private float google_search;

    public class TranslateResult {
        public float train;
        public float flight;
        public float netflix;
        public float google;
        public TranslateResult (float emission) {
            this.train = emission/train_per_km;
            this.flight = emission/flight_per_km;
            this.netflix = emission/netflix_per_h;
            this.google = emission/google_search;
        }
    }
    public TranslateResult translate(float emission) {
        return new TranslateResult(emission);
    }
}