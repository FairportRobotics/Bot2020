package frc.team578.robot.utils;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team578.robot.commands.auto.enums.AutoActionEnum;
import frc.team578.robot.commands.auto.enums.AutoStartingPositionEnum;

public class ChooserUtil {
    public static SendableChooser<AutoStartingPositionEnum> initializeStartingPositionChooser() {
        SendableChooser<AutoStartingPositionEnum> startingPositionChooser = new SendableChooser<>();
        startingPositionChooser.addOption("Left", AutoStartingPositionEnum.LEFT);
        startingPositionChooser.setDefaultOption("Middle", AutoStartingPositionEnum.MIDDLE);
        startingPositionChooser.addOption("Right", AutoStartingPositionEnum.RIGHT);
        SmartDashboard.putData("Starting Position", startingPositionChooser);
        return startingPositionChooser;

    }

    public static SendableChooser<AutoActionEnum> initializeAutoActionChooser() {
        SendableChooser<AutoActionEnum> autoActionChooser = new SendableChooser<>();
        autoActionChooser.setDefaultOption("Cross_Line_Forward", AutoActionEnum.CROSS_LINE_FORWARD);
        autoActionChooser.addOption("Cross_Line_Backwards", AutoActionEnum.CROSS_LINE_BACKWARDS);
        SmartDashboard.putData("Auto Action", autoActionChooser);
        return autoActionChooser;

    }
}
