package frc.team578.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import frc.team578.robot.Robot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntakeInCommand extends Command {

    private static final Logger log = LogManager.getLogger(IntakeInCommand.class);

    public IntakeInCommand() {
        requires(Robot.intakeSubsystem);
    }

    @Override
    protected void initialize() {
        log.debug("Initializing IntakeInCommand");
    }

    @Override
    protected void execute() {
        log.debug("Exec IntakeInCommand");
        Robot.intakeSubsystem.intakeSpinIn();
    }


    @Override
    protected void interrupted() {
        Robot.intakeSubsystem.stop();
        log.debug("Interrupted IntakeInCommand");
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        log.debug("Ending IntakeInCommand " + timeSinceInitialized());
    }
}
