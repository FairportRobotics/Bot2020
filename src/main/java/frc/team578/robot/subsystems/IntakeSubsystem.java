package frc.team578.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team578.robot.RobotMap;
import frc.team578.robot.subsystems.interfaces.Initializable;

public class IntakeSubsystem extends Subsystem implements Initializable {


    private WPI_TalonSRX intakeTalon;
    private DoubleSolenoid intakeArmSolenoid;



    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public void initialize() {
        intakeTalon = new WPI_TalonSRX(RobotMap.INTAKE_TALON);
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


}
