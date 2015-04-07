package group8.com.application.Application;

/**
 * Created by enriquecordero on 30/03/15.
 */
public abstract class Controller {

    /* Methods for MeasurementsFactory */
    protected static void eventSpeedChanged(double speed) {
        Session.setSpeed(speed);
        GradingSystem.updateSpeedScore(speed);
    }

    protected static void eventFuelConsumptionChanged(double fuelConsumption) {
        Session.setFuelConsumption(fuelConsumption);
        GradingSystem.updateFuelConsumptionScore(fuelConsumption);
    }

    protected static void eventBrakeChanged(int brake) {
        Session.setBrake(brake);
        GradingSystem.updateBrakeScore(brake);
    }

    protected static void eventDriverDistractionLevelChanged(int driverDistractionLevel) {
        Session.setDriverDistractionLevel(driverDistractionLevel);
        GradingSystem.updateDriverDistractionLevelScore(driverDistractionLevel);
    }
    /* END - Methods for MeasurementsFactory */

}