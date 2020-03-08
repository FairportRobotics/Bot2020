package frc.team578.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team578.robot.commands.*;
import frc.team578.robot.commands.debug.*;
import frc.team578.robot.subsystems.interfaces.Initializable;
import frc.team578.robot.utils.Gamepad;
import frc.team578.robot.utils.OperatorBox;

public class OI implements Initializable {

    public Joystick leftJoystick = new Joystick(RobotMap.LEFT_JOYSTICK_ID);
    public Joystick rightJoystick = new Joystick(RobotMap.RIGHT_JOYSTICK_ID);
    public OperatorBox ob1 = new OperatorBox(RobotMap.OPERATORBOX_ID); // Operator box - main control functions w/ analog joystick
    public Gamepad gp1 = new Gamepad(RobotMap.DEBUG_GAMEPAD_ID); // Debug gamepad - secondary, nonessential functions

    int JOYSTICK_TRIGGER_BUTTON_NUMBER = 1;
    JoystickButton leftTrigger = new JoystickButton(leftJoystick, JOYSTICK_TRIGGER_BUTTON_NUMBER);
    JoystickButton rightTrigger = new JoystickButton(rightJoystick, JOYSTICK_TRIGGER_BUTTON_NUMBER);

    public void initialize() {
        /* Driver */
        leftTrigger.whenPressed(new CentricModeRobotCommand());
        rightTrigger.whenPressed(new CentricModeFieldCommand());

        /* Operator */
        // Big Boi
        // Hook commands
        ob1.one.whenPressed(new HookDeployCommand()); // Press to deploy hook
        ob1.two.whenPressed(new HookDeployReverseCommand()); // Press to bring hook back down
        // Intake commands
        ob1.three.whileHeld(new IntakeInCommand()); // Hold to spin intake in
        ob1.seven.whileHeld(new IntakeOutCommand()); // Hold to spin intake out
        // Shooter commands
        ob1.four.whenPressed(new ShooterSingleShotCommand()); // Press to shoot one ball
        ob1.eight.whileHeld(new ShooterShootAllCommand()); // Hold to continuously shoot
        // Winch brake commands
        ob1.five.whileHeld(new ClimberWinchBrakeExtendCommand()); // Press to extend winch brake and holding this down should allow for use of the joystick to traverse
        ob1.nine.whenPressed(new ClimberWinchBrakeRetractCommand() ); // Press to retract winch brake
        // Winch commands
        ob1.six.whileHeld(new ClimberWinchUpCommand()); // Hold to move the winch up
        ob1.ten.whileHeld(new ClimberDebugWinchDownCommand()); // Hold to move the winch down

        // TODO: Add analog joysticks for traversal using L and R joystick, disable up and down

        // Debug Gamepad
        gp1.buttonA.whileHeld(new ConveyorDebugSpinForwardCommand()); // Hold to spin in conveyor belt
        gp1.buttonB.whileHeld(new ConveyorDebugSpinBackwardsCommand()); // Hold to spin out conveyor belt

        gp1.buttonX.whenPressed(new ShooterToDefaultRPMCommand()); // Press to spin shooter to default RPM
        gp1.buttonY.whenPressed(new ShooterDebugStopCommand()); // Press to stop shooter

        gp1.lb.whenPressed(new IntakeArmDownCommand()); // Press to move intake arm down (out)
        gp1.rb.whenPressed(new IntakeArmUpCommand()); // Press to move intake arm up (in)

        gp1.lt.whileHeld(new IntakeInCommand2());
    }

}