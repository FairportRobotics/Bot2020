package frc.team578.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import frc.team578.robot.Robot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WinchDownCommand extends Command {

    private static final Logger log = LogManager.getLogger(WinchDownCommand.class);

    public WinchDownCommand() {
        requires(Robot.climberSubsystem);
    }

    @Override
    protected void initialize() {
        log.info("Initializing WinchDownCommand");
    }

    @Override
    protected void execute() {
        log.info("Exec WinchDownCommand");
        // TODO: This method SHOULD be a toggle method, so add code to stop spinning
        Robot.climberSubsystem.winchDown();
    }


    @Override
    protected void interrupted() {
        log.info("Interrupted WinchDownCommand");
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        log.info("Ending WinchDownCommand " + timeSinceInitialized());
    }
}
