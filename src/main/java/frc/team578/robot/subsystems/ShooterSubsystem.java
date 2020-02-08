package frc.team578.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team578.robot.RobotMap;
import frc.team578.robot.subsystems.interfaces.Initializable;

public class ShooterSubsystem extends Subsystem implements Initializable {

    private WPI_TalonSRX shooterTalon;
    int defaultRPM = 999;

    // TODO : How are we allowing a ball to be fed into the shooter?

    @Override
    protected void initDefaultCommand() {
    }

    @Override
    public void initialize() {
        shooterTalon = new WPI_TalonSRX(RobotMap.INTAKE_TALON);
    }

    public void shoot() {
        shoot(defaultRPM);
    }

    public void shoot(int rpm) {
    }
}
