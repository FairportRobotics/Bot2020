package frc.team578.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team578.robot.RobotMap;
import frc.team578.robot.subsystems.interfaces.Initializable;

public class IntakeSubsystem extends Subsystem implements Initializable {


    private WPI_TalonSRX intakeTalon;
//    private DoubleSolenoid intakeArmSolenoid;
    private double spinIntakeInPower = 0.5;
    private double spinIntakeOutPower = 0.5;



    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public void initialize() {
        intakeTalon = new WPI_TalonSRX(RobotMap.INTAKE_TALON);
        intakeTalon.configFactoryDefault();
        intakeTalon.setNeutralMode(NeutralMode.Brake);
//        intakeArmSolenoid = new DoubleSolenoid(RobotMap.PCM1, RobotMap.PCM1_INTAKE_UP, RobotMap.PCM1_INTAKE_DOWN);
    }

    //Piston  -  These kForward and kReverse Values may need to be switched
//    public void intakeArmUp() { intakeArmSolenoid.set(DoubleSolenoid.Value.kForward); }
//    public void intakeArmDown() { intakeArmSolenoid.set(DoubleSolenoid.Value.kReverse);}

    //Intake Motor
    public void intakeSpinIn() {
        System.err.println("Exceute intakeSpinIn");
        System.out.println(intakeTalon.isAlive());
        intakeTalon.set(ControlMode.PercentOutput, spinIntakeInPower);
    }
    public void intakeSpinOut() {
        System.err.println("Execute intakeSpinOut");
        intakeTalon.set(ControlMode.PercentOutput, -spinIntakeOutPower);
    }

    public void stop() {intakeTalon.set(ControlMode.PercentOutput, 0);}




}
