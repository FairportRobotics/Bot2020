package frc.team578.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import frc.team578.robot.Robot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpinInConveyorCommand extends Command {

    private static final Logger log = LogManager.getLogger(SpinInConveyorCommand.class);

    public SpinInConveyorCommand() {
        requires(Robot.intakeSubsystem);
    }

    @Override
    protected void initialize() {
        log.info("Initializing SpinInConveyorCommand");
    }

    @Override
    protected void execute() {
        log.info("Exec SpinInConveyorCommand");
        // TODO: This method SHOULD be a toggle method, so add code to stop spinning
        Robot.intakeSubsystem.feederSpinIn();
    }


    @Override
    protected void interrupted() {
        log.info("Interrupted SpinInConveyorCommand");
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        log.info("Ending SpinInConveyorCommand " + timeSinceInitialized());
    }
}
