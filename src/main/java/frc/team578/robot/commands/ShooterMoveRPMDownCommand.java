package frc.team578.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team578.robot.Robot;

public class ShooterMoveRPMDownCommand extends CommandGroup {
    public ShooterMoveRPMDownCommand() {
        requires(Robot.shooterSubsystem);

//        if(Robot.shooterSubsystem.isSpinning()) {
            addSequential(new ShooterDecrementRPM());
            addSequential(new ShooterToDefaultRPMCommand());
//        } else {
//            addSequential(new ShooterDecrementRPM());
//        }
    }

}
