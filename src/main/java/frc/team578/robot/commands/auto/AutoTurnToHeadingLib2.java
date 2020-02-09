package frc.team578.robot.commands.auto;


import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.team578.robot.Robot;

public class AutoTurnToHeadingLib2 extends PIDCommand  {

    private static final double kP = 0.04;
    private static final double kI = 0.00;
    private static final double kD = 0.075;

    public AutoTurnToHeadingLib2(Double targetAngleDegrees) {

        super(new PIDController(kP, kI, kD),
                Robot.gyroSubsystem::getHeading,
                targetAngleDegrees,
                output -> Robot.swerveDriveSubsystem.move(0, 0, output, Robot.gyroSubsystem.getHeading()),
                null); // This has to be a wpilib2 subsystem

        // Set the controller to be continuous (because it is an angle controller)
        getController().enableContinuousInput(-180, 180);
        // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
        // setpoint before it is considered as having reached the reference
        getController()
                .setTolerance(1, 1);
    }

    @Override
    public boolean isFinished() {
        // End when the controller is at the reference.
        return getController().atSetpoint();
    }
}
