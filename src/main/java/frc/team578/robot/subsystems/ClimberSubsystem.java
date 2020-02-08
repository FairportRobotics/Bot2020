package frc.team578.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team578.robot.RobotMap;
import frc.team578.robot.subsystems.interfaces.Initializable;

public class ClimberSubsystem extends Subsystem implements Initializable {

    private WPI_TalonSRX traverseTalon;
    private WPI_TalonSRX winchTalon;
    private DoubleSolenoid climberSolenoid;

    @Override
    public void initialize() {
    }

    @Override
    protected void initDefaultCommand() {
        traverseTalon = new WPI_TalonSRX(RobotMap.TRAVERSE_TALON);
        winchTalon = new WPI_TalonSRX(RobotMap.WINCH_TALON);
        climberSolenoid = new DoubleSolenoid(RobotMap.PCM1, RobotMap.PCM1_CLIMBER_UP, RobotMap.PCM1_CLIMBER_DOWN);
    }

    public void traverseLeft() {
    }

    public void traverseRight() {
    }

    public void deployClimber() {
        // Fast
    }

    public void retractClimber() {
        // Slow
    }

    public void winchUp() {
    }

    public void winchDown() {
    }
}
