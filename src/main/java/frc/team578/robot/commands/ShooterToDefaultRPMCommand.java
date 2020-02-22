package frc.team578.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import frc.team578.robot.Robot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShooterToDefaultRPMCommand extends Command {

    private static final Logger log = LogManager.getLogger(ShooterToDefaultRPMCommand.class);

    public ShooterToDefaultRPMCommand() {
        requires(Robot.shooterSubsystem);
    }

    @Override
    protected void initialize() {
        log.info("Initializing ShooterShootAllCommand");
    }

    @Override
    protected void execute() {
        log.info("Exec ShooterShootAllCommand");
        Robot.shooterSubsystem.spinToDefaultRPM();
    }


    @Override
    protected void interrupted() {
        log.info("Interrupted ShooterShootAllCommand");
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        log.info("Ending ShooterShootAllCommand " + timeSinceInitialized());
    }
}
