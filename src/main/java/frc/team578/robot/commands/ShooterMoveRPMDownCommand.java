package frc.team578.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team578.robot.Robot;

public class ShooterMoveRPMDownCommand extends CommandGroup {
    public ShooterMoveRPMDownCommand() {
        requires(Robot.shooterSubsystem);

        addSequential(new ShooterDecrementRPM());

        if(Robot.shooterSubsystem.isSpinning()) { // If it's spinning, then spin it to the new RPM
            addSequential(new ShooterToDefaultRPMCommand());
        }
    }

}
