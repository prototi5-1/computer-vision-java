package org.mycompany.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageAPITest {

    private static final Logger log = LoggerFactory.getLogger(ImageAPITest.class);

    @Test
    public void testOpenCVInitialization() {
        log.info("Running ImageAPITest");
        try {
            ImageAPI api = new ImageAPI();
            String os = api.getOperatingSystemType().name();
            String version = org.opencv.core.Core.getVersionString();

            log.info("OS version - " + os);
            log.info("Open CV version - " + version);

            assertNotNull(version, "OpenCV version should not be null");
            assertFalse(version.isEmpty(), "OpenCV version should not be empty");
        } catch (Exception e) {
            fail("OpenCV initialization failed: " + e.getMessage());
        }
    }
}
