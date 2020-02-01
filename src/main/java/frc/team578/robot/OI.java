package frc.team578.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team578.robot.commands.*;
import frc.team578.robot.subsystems.interfaces.Initializable;
import frc.team578.robot.utils.Gamepad;

public class OI implements Initializable {

    public Joystick leftJoystick = new Joystick(RobotMap.LEFT_JOYSTICK_ID);
    public Joystick rightJoystick = new Joystick(RobotMap.RIGHT_JOYSTICK_ID);
    public OB ob1 = new OB(RobotMap.OPERATORBOX_ID); // Operator box - main control functions w/ analog joystick
    public GP gp2 = new GP(RobotMap.DEBUG_GAMEPAD_ID); // Debug gamepad - secondary, nonessential functions

    int JOYSTICK_TRIGGER_BUTTON_NUMBER = 1;
    JoystickButton leftTrigger = new JoystickButton(leftJoystick, JOYSTICK_TRIGGER_BUTTON_NUMBER);
    JoystickButton rightTrigger = new JoystickButton(rightJoystick, JOYSTICK_TRIGGER_BUTTON_NUMBER);

    public void initialize() {
        leftTrigger.whenPressed(new CentricModeRobotCommand());
        rightTrigger.whenPressed(new CentricModeFieldCommand());
    }

    public class OB {
        Gamepad operatorBox;
        JoystickButton one;
        JoystickButton two;
        JoystickButton three;
        JoystickButton four;
        JoystickButton five;
        JoystickButton six;
        JoystickButton seven;
        JoystickButton eight;
        JoystickButton nine;
        JoystickButton ten;

        public OB(int id) {
            operatorBox = new Gamepad(id);
            one = new JoystickButton(operatorBox, 1);
            two = new JoystickButton(operatorBox, 2);
            three = new JoystickButton(operatorBox, 3);
            four = new JoystickButton(operatorBox, 4);
            five = new JoystickButton(operatorBox, 5);
            six = new JoystickButton(operatorBox, 6);
            seven = new JoystickButton(operatorBox, 7);
            eight = new JoystickButton(operatorBox, 8);
            nine = new JoystickButton(operatorBox, 9);
            ten = new JoystickButton(operatorBox, 10);
        }

        public double getJoystickX() {
            return operatorBox.getLeftX();
        }
    }

    public class GP {
        Gamepad gamepad;
        JoystickButton rb;
        JoystickButton lb;
        JoystickButton rt;
        JoystickButton lt;
        JoystickButton buttonA;
        JoystickButton buttonB;
        JoystickButton buttonX;
        JoystickButton buttonY;
        JoystickButton back;
        JoystickButton start;

        public GP(int id) {
            gamepad = new Gamepad(id);
            rb = gamepad.getRightShoulder();
            lb = gamepad.getLeftShoulder();
            rt = gamepad.getRightTriggerClick();
            lt = gamepad.getLeftTriggerClick();
            buttonA = gamepad.getButtonA();
            buttonB = gamepad.getButtonB();
            buttonX = gamepad.getButtonX();
            buttonY = gamepad.getButtonY();
            back = gamepad.getBackButton();
            start = gamepad.getStartButton();
        }

        public double getPadLeftX() {
            return gamepad.getLeftX();
        }

        public double getPadLeftY() {
            return gamepad.getLeftY();
        }

        public double getPadRightX() {
            return gamepad.getRightX();
        }

        public double getPadRightY() {
            return gamepad.getRightY();
        }
    }

    // This affects drive and arm movement deadbands
    final double DEADBAND = 0.05;
    public double deadband(double value) {
        if (Math.abs(value) < DEADBAND) return 0.0;
        return value;
    }
}