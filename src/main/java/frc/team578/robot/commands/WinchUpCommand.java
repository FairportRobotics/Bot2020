package frc.team578.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import frc.team578.robot.Robot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WinchUpCommand extends Command {

    private static final Logger log = LogManager.getLogger(WinchUpCommand.class);

    public WinchUpCommand() {
        requires(Robot.climberSubsystem);
    }

    @Override
    protected void initialize() {
        log.info("Initializing WinchUpCommand");
    }

    @Override
    protected void execute() {
        log.info("Exec WinchUpCommand");
        // TODO: This method SHOULD be a toggle method, so add code to stop spinning
        Robot.climberSubsystem.winchUp();
    }


    @Override
    protected void interrupted() {
        log.info("Interrupted WinchUpCommand");
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        log.info("Ending WinchUpCommand " + timeSinceInitialized());
    }
}
