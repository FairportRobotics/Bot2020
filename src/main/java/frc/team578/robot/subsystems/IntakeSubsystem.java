package frc.team578.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team578.robot.RobotMap;
import frc.team578.robot.subsystems.interfaces.Initializable;

public class IntakeSubsystem extends Subsystem implements Initializable {

    private WPI_TalonSRX intakeTalon;
    private DoubleSolenoid intakeArmSolenoid;
    private WPI_TalonSRX feederTalon;
    DigitalInput intakeBallSensor = new DigitalInput(RobotMap.INTAKE_BALL_SENSOR);
    DigitalInput intakeFullSensor = new DigitalInput(RobotMap.INTAKE_FULL_SENSOR);


    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public void initialize() {
        intakeTalon = new WPI_TalonSRX(RobotMap.INTAKE_TALON);
        feederTalon = new WPI_TalonSRX(RobotMap.FEEDER_TALON);
        intakeArmSolenoid = new DoubleSolenoid(RobotMap.PCM1, RobotMap.PCM1_INTAKE_UP, RobotMap.PCM1_INTAKE_DOWN);
    }


    public void intakeArmUp() {
    }

    public void intakeArmDown() {
    }

    public void intakeSpinIn() {
    }

    public void intakeSpinOut() {
    }

    public void feederSpinIn() {
    }

    public void feederSpinOut() {
    }

    public boolean isFeederFull() {
        return intakeFullSensor.get();
    }

    public boolean isBallPresent() {
        return intakeBallSensor.get();
    }
}
