package frc.team578.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team578.robot.RobotMap;
import frc.team578.robot.subsystems.interfaces.Initializable;

public class ShooterSubsystem extends Subsystem implements Initializable {

    int target = 1000;
    int kTimeoutMs = 0;
    private WPI_TalonSRX shooterTalon;
    int defaultRPM = 999;

    // TODO : How are we allowing a ball to be fed into the shooter?

    @Override
    protected void initDefaultCommand() {
    }

    @Override
    public void initialize() {

        shooterTalon = new WPI_TalonSRX(RobotMap.INTAKE_TALON);
        shooterTalon = new WPI_TalonSRX(1);
//    joystick = new Joystick(0);

        // Type of encoder, ID of the PID loop, timeout in ms to wait to report if failure
        shooterTalon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, kTimeoutMs);
        shooterTalon.setSensorPhase(true);
        shooterTalon.setInverted(false);


        double kF =  0.04;
        double kP =  0.1; //0.11;
        double kI =  0.0005; //0.001;
        double kD =  0.05; //0.1;
        int iZone = 0;


        /* Config nominal and peak output */
        shooterTalon.configNominalOutputForward(0, kTimeoutMs);
        shooterTalon.configNominalOutputReverse(0, kTimeoutMs);
        shooterTalon.configPeakOutputForward(1, kTimeoutMs);
        shooterTalon.configPeakOutputReverse(-1, kTimeoutMs);

        /* Config the Velocity closed loop gains in slot0 */
        shooterTalon.config_kF(0, kF, kTimeoutMs);
        shooterTalon.config_kP(0, kP, kTimeoutMs);
        shooterTalon.config_kI(0, kI, kTimeoutMs);
        shooterTalon.config_kD(0, kD, kTimeoutMs);
        shooterTalon.config_IntegralZone(0, iZone, kTimeoutMs);
        shooterTalon.setSelectedSensorPosition(0);

        // TODO : Lets try these values (override reverse above)
        shooterTalon.setNeutralMode(NeutralMode.Coast);
        shooterTalon.configPeakOutputReverse(0, kTimeoutMs);

    }

    public void shootOneBall(){
    }

    public void shootAll() {
        shoot(defaultRPM);
    }

    private void shoot(int rpm) {
        shooterTalon.set(ControlMode.Velocity, RPMsToVel(rpm));
    }

    private double RPMsToVel(double RPMs) {
        return (RPMs * 2048.0) / 600;
    }

    private double velToRPM(double vel) {
        return (vel * 600.0) / 2048;
    }
}
