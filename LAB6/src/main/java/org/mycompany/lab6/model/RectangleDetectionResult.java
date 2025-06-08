package org.mycompany.lab6.model;

public final class RectangleDetectionResult {
    private final int targetWidth;
    private final int targetHeight;
    private final int count;

    public RectangleDetectionResult(int targetWidth, int targetHeight, int count) {
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        this.count = count;
    }

    public int getTargetWidth() {
        return targetWidth;
    }

    public int getTargetHeight() {
        return targetHeight;
    }

    public int getCount() {
        return count;
    }

    public boolean hasRectangles() {
        return count > 0;
    }
}