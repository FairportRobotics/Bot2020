package frc.team578.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import frc.team578.robot.Robot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShooterSingleShotCommand extends Command {

    private static final Logger log = LogManager.getLogger(ShooterSingleShotCommand.class);

    public ShooterSingleShotCommand() {
        requires(Robot.shooterSubsystem);
    }

    @Override
    protected void initialize() {
        log.info("Initializing ShooterSingleShotCommand");
    }

    @Override
    protected void execute() {
        log.info("Exec ShooterSingleShotCommand");
        Robot.shooterSubsystem.shootOneBall();
    }


    @Override
    protected void interrupted() {
        log.info("Interrupted ShooterSingleShotCommand");
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        log.info("Ending ShooterSingleShotCommand " + timeSinceInitialized());
    }
}
