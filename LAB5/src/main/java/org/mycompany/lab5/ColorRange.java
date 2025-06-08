package org.mycompany.lab5;

import org.opencv.core.Scalar;

import java.util.Objects;

/**
 * Класс описывает диапазон цвета в формате Scalar (B, G, R).
 */
public class ColorRange {
    private final Scalar lowerBound;
    private final Scalar upperBound;

    public ColorRange(Scalar lowerBound, Scalar upperBound) {
        this.lowerBound = Objects.requireNonNull(lowerBound);
        this.upperBound = Objects.requireNonNull(upperBound);
    }

    public Scalar getLowerBound() {
        return lowerBound;
    }

    public Scalar getUpperBound() {
        return upperBound;
    }
}
