package frc.team578.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team578.robot.Robot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntakeArmUpCommand extends Command {
    private static final Logger log = LogManager.getLogger(IntakeArmUpCommand.class);

    public IntakeArmUpCommand() {
        log.info("IntakeArmUpCommand Constructor");
    }

    @Override
    protected void initialize() {
        log.info("Initializing IntakeArmUpCommand");
    }

    @Override
    protected void execute() {
//        Robot.intakeSubsystem.intakeArmUp();
        log.info("Exec IntakeArmUpCommand");
    }


    @Override
    protected void interrupted() {
        log.info("Interrupted IntakeArmUpCommand");
    }

    @Override
    protected boolean isFinished() {

        boolean isFinished = true;
        log.info ("IntakeArmUpCommand is Finished : " + isFinished);
        return isFinished;
    }

    @Override
    protected void end() {

        log.info("Ending IntakeArmUpCommand " + timeSinceInitialized());

    }
}
