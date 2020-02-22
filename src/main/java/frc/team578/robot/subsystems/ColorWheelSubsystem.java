package frc.team578.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.team578.robot.subsystems.interfaces.Initializable;
import frc.team578.robot.utils.ColorManager;
import frc.team578.robot.utils.WheelColor;

/**
 * ColorWheelSubsystem
 */
public class ColorWheelSubsystem extends Subsystem implements Initializable { 
    public static boolean wheelIsSpinning = false;

    // Talon IDs and talons for the Color Wheel
    private static TalonSRX colorSpinner = null;
    private static int COLOR_SPINNER_TALON_ID = -1;

    public static Color kBlueTarget;
    public static Color kGreenTarget;
    public static Color kRedTarget;
    public static Color kYellowTarget;

    // # Color wheel subsystem
    public static final I2C.Port i2cPort = I2C.Port.kOnboard;

    private static ColorSensorV3 colorSensor;
    private static ColorMatch colorMatcher;

    @Override
    public void initialize() {
        // 
        colorSpinner = new TalonSRX(COLOR_SPINNER_TALON_ID);

        // The color sensor using the i2c port, it detects the colors, it has a very short range though, only a few inches
        colorSensor = new ColorSensorV3(i2cPort);
        // This compares the input color to a range of colors we add below
        colorMatcher = new ColorMatch();

        // These are the colors we match with the color input
        kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
        kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
        kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
        kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

        // Adding the colors to the color matcher
        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);
    }

    @Override
    protected void initDefaultCommand() {
    }

    public void periodic() {
        // ! The color the sensor currently sees
        Color c = colorSensor.getColor();
  
        // Infrared Value from the color sensor
        double IR = colorSensor.getIR();
    
        ColorMatchResult r = colorMatcher.matchClosestColor(c);
    
        // Check to see which color we currently see, it will always see a color.
        if(r.color == kRedTarget) {
          ColorManager.setCurrentColor(WheelColor.Red);
        } else if(r.color == kGreenTarget) {
          ColorManager.setCurrentColor(WheelColor.Green);
        } else if(r.color == kBlueTarget) {
          ColorManager.setCurrentColor(WheelColor.Blue);
        } else if(r.color == kYellowTarget) {
          ColorManager.setCurrentColor(WheelColor.Yellow);
        }
    

        // Putting relevant color data in smart dashboard.
        SmartDashboard.putString("Panel Color", ColorManager.getPanelColor().name());
        SmartDashboard.putString("Actual Color", ColorManager.getSeenColor().name());
    
        SmartDashboard.putNumber("Red", c.red);
        SmartDashboard.putNumber("Green", c.green);
        SmartDashboard.putNumber("Blue", c.blue);
        SmartDashboard.putNumber("IR", IR);
        SmartDashboard.putNumber("Proximity", colorSensor.getProximity());    
    }

/**
 * ! Note, methods for the subsystem motor still need to be created if the subsystem is actually used on the robot.
 */

    public void startSpinningWheel() {
        // Change this later if subsystem is used.
        colorSpinner.set(ControlMode.PercentOutput, 0.5);
    }

    public void stopWheel() {
        // if(/** motor velocity is negative */) {}
        // Starts using pid to stop the wheel by spinning in the opposite direction of the current spinning direction.
    }

    
}