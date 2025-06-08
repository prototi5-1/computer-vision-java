package org.mycompany.lab6.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class RectangleDetectionResultTest {

    @Test
    public void testProperties() {
        RectangleDetectionResult result = new RectangleDetectionResult(100, 50, 3);
        assertEquals(100, result.getTargetWidth());
        assertEquals(50, result.getTargetHeight());
        assertEquals(3, result.getCount());
        assertTrue(result.hasRectangles());

        RectangleDetectionResult emptyResult = new RectangleDetectionResult(100, 50, 0);
        assertFalse(emptyResult.hasRectangles());
    }
}