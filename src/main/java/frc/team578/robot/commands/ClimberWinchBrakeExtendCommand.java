package frc.team578.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import frc.team578.robot.Robot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClimberWinchBrakeExtendCommand extends Command {

    private static final Logger log = LogManager.getLogger(ClimberWinchBrakeExtendCommand.class);

    public ClimberWinchBrakeExtendCommand() {
        requires(Robot.climberSubsystem);
    }


    @Override
    protected void initialize() {
        log.debug("Initializing ClimberWinchBrakeExtendCommand");
    }

    @Override
    protected void execute() {
        log.debug("Exec ClimberWinchBrakeExtendCommand");
        if(!Robot.climberSubsystem.isBrakeExtended()) {
            Robot.climberSubsystem.winchBrakeExtend();
        }
        double traverse = Robot.oi.ob1.getJoystickX();
        if(traverse < 0) {
            Robot.climberSubsystem.traverseLeft();
        }
        else if(traverse > 0) {
            Robot.climberSubsystem.traverseRight();
        }
        else {
            Robot.climberSubsystem.traverseStop();
        }

    }


    @Override
    protected void interrupted() {
        log.debug("Interrupted ClimberWinchBrakeExtendCommand");
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        log.debug("Ending ClimberWinchBrakeExtendCommand " + timeSinceInitialized());
    }

}
