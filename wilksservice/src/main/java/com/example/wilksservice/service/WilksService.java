package com.example.wilksservice.service;

import com.example.wilksservice.Gender;
import org.springframework.stereotype.Service;

@Service
public class WilksService {

    public double calculateWilks(double bodyWeight, double totalLift, Gender gender) {
        double[] coefficients;
        if (gender == Gender.MALE) {
            coefficients = new double[]{ -216.0475144, 16.2606339, -0.002388645, -0.00113732, 7.01863E-06, -1.291E-08};
        } else {
            coefficients = new double[]{ 594.31747775582, -27.23842536447, 0.82112226871, -0.00930733913, 4.731582E-05, -9.054E-08 };
        }
        double denominator = coefficients[0]+coefficients[1]*bodyWeight+coefficients[2]*Math.pow(bodyWeight,2)+ coefficients[3]*Math.pow(bodyWeight,3)
                + coefficients[4]*Math.pow(bodyWeight,4)
                + coefficients[5]*Math.pow(bodyWeight,5);
        return totalLift * 500 / denominator;
    }
}
