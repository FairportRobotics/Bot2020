package frc.team578.robot.commands.auto.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team578.robot.commands.ShooterSingleShotCommand;
import frc.team578.robot.commands.auto.AutoMoveTimed;

public class PenfieldCenterShot extends CommandGroup {
    public PenfieldCenterShot() {
        addSequential(new AutoMoveTimed("AutoMoveBackwards", -0.5, 0, 0, 1));
        addSequential(new ShooterSingleShotCommand());

    }
}
