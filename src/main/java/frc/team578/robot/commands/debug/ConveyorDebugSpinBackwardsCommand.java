package frc.team578.robot.commands.debug;


import edu.wpi.first.wpilibj.command.Command;
import frc.team578.robot.Robot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConveyorDebugSpinBackwardsCommand extends Command {

    private static final Logger log = LogManager.getLogger(ConveyorDebugSpinBackwardsCommand.class);

    public ConveyorDebugSpinBackwardsCommand() {
        requires(Robot.intakeSubsystem);
    }

    @Override
    protected void initialize() {
        log.info("Initializing ConveyorDebugSpinBackwardsCommand");
    }

    @Override
    protected void execute() {
        log.info("Exec ConveyorDebugSpinBackwardsCommand");
        // TODO: This method SHOULD be a toggle method, so add code to stop spinning
        Robot.intakeSubsystem.feederSpinOut();
    }


    @Override
    protected void interrupted() {
        log.info("Interrupted ConveyorDebugSpinBackwardsCommand");
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        log.info("Ending ConveyorDebugSpinBackwardsCommand " + timeSinceInitialized());
    }
}
