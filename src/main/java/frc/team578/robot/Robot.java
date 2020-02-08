package frc.team578.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team578.robot.subsystems.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Robot extends TimedRobot {

    private static final Logger log = LogManager.getLogger(Robot.class);

    // Operator Interface
    public static OI oi;

    // Subsystem
    public static SwerveDriveSubsystem swerveDriveSubsystem;
    public static IntakeSubsystem intakeSubsystem;
    public static ShooterSubsystem shooterSubsystem;
    public static ClimberSubsystem climberSubsystem;
    public static GyroSubsystem gyroSubsystem;
    public static UsbCamera camera;

    @Override
    public void robotInit() {

        try {

            log.info("Starting Robot Init");

            gyroSubsystem = new GyroSubsystem("gyro");
            gyroSubsystem.initialize();
            log.info("Gyro Subsystem Initialized");

            swerveDriveSubsystem = new SwerveDriveSubsystem();
            swerveDriveSubsystem.initialize();
            log.info("Swerve Drive Subsystem Initialized");

            intakeSubsystem = new IntakeSubsystem();
            intakeSubsystem.initialize();
            log.info("Intake Subsystem Initialized");

            shooterSubsystem = new ShooterSubsystem();
            shooterSubsystem.initialize();
            log.info("Shooter Subsystem Initialized");

            climberSubsystem = new ClimberSubsystem();
            climberSubsystem.initialize();
            log.info("Climber Subsystem Initialized");

            camera = CameraServer.getInstance().startAutomaticCapture();
            // cam.setResolution(100, 75);
            // cam.setFPS(-1);
            log.info("Initialized Camera");

            oi = new OI();
            oi.initialize();
            log.info("OI Subsystem Initialized");

        } catch (Throwable t) {
            log.error("Error In Robot Initialization : " + t.getMessage(), t);
            throw t;
        }

        log.info("Robot Init Complete");
    }

    @Override
    public void robotPeriodic() {

        Scheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        super.disabledInit();
    }

    @Override
    public void disabledPeriodic() {
        updateAllDashboards();
        Scheduler.getInstance().run();
    }


    @Override
    public void autonomousInit() {

        /*
          TODO : Do we want to lower the arm at the beginning (or is this manual)
         */

        Robot.swerveDriveSubsystem.stop();
        Robot.swerveDriveSubsystem.setModeRobot();

    }

    @Override
    public void autonomousPeriodic() {

        updateAllDashboards();
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {

        Robot.swerveDriveSubsystem.stop();
        Robot.swerveDriveSubsystem.setModeField();

    }

    @Override
    public void teleopPeriodic() {


        updateAllDashboards();
        Scheduler.getInstance().run();

    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
        Scheduler.getInstance().run();
    }


    public void updateAllDashboards() {
        Robot.swerveDriveSubsystem.updateDashboard();
        Robot.gyroSubsystem.updateDashboard();
    }
}
