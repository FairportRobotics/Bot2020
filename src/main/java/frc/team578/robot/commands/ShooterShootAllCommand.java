package frc.team578.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import frc.team578.robot.Robot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShooterShootAllCommand extends Command {

    private static final Logger log = LogManager.getLogger(ShooterShootAllCommand.class);

    public ShooterShootAllCommand() {
        requires(Robot.shooterSubsystem);
    }

    @Override
    protected void initialize() {
        log.info("Initializing ShooterShootAllCommand");
    }

    @Override
    protected void execute() {
        log.info("Exec ShooterShootAllCommand");
        // TODO: This method SHOULD be a toggle method, so add code to stop spinning
        Robot.shooterSubsystem.shootAll();
    }


    @Override
    protected void interrupted() {
        log.info("Interrupted ShooterShootAllCommand");
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        log.info("Ending ShooterShootAllCommand " + timeSinceInitialized());
    }
}
