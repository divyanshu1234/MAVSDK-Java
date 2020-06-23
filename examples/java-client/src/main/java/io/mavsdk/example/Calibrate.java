package io.mavsdk.example;

import io.mavsdk.System;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Calibrate {
  private static final Logger logger = LoggerFactory.getLogger(Calibrate.class);

  public static void main(String[] args) {
    logger.debug("Starting example: calibration...");

    System drone = new System();
    CountDownLatch latch = new CountDownLatch(1);

    drone.getCalibration().getCalibrateGyro()
                          .subscribe(progressData -> {
                              if (progressData.getHasProgress()) {
                                  logger.debug("Progress: " + progressData.getProgress());
                              }

                              if (progressData.getHasStatusText()) {
                                  logger.debug("Status text: " + progressData.getStatusText());
                              }
                          }, throwable -> {
                              logger.error("error: ", throwable);
                          },
                          latch::countDown);

    try {
      latch.await();
    } catch (InterruptedException ignored) {
      // This is expected
    }
  }
}
