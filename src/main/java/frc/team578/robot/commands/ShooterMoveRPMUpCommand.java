package frc.team578.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team578.robot.Robot;

public class ShooterMoveRPMUpCommand extends CommandGroup {
    public ShooterMoveRPMUpCommand() {
        requires(Robot.shooterSubsystem);

        addSequential(new ShooterIncrementRPM());
        addSequential(new ShooterToDefaultRPMCommand());
    }

}
