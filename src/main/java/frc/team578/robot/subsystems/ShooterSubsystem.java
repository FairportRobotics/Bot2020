package frc.team578.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team578.robot.RobotMap;
import frc.team578.robot.subsystems.interfaces.Initializable;
import frc.team578.robot.utils.PIDFinished;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class ShooterSubsystem extends Subsystem implements Initializable {

    private WPI_TalonSRX shooterTalon;
    private double defaultRPM = 500;
    private double maxRPM = 2000;
    private int kTimeoutMs = 0;
    private PIDFinished<Double> pidFinishRPMDerivative;
    private PIDFinished<Integer> pidFinishRPMTarget;


    @Override
    protected void initDefaultCommand() {
    }

    @Override
    public void initialize() {

        shooterTalon = new WPI_TalonSRX(RobotMap.INTAKE_TALON);
        shooterTalon.configFactoryDefault();
        shooterTalon.set(ControlMode.Current,0);

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
        shooterTalon.configPeakOutputReverse(0, kTimeoutMs); // No Reverse Power


        /* Config the Velocity closed loop gains in slot0 */
        shooterTalon.config_kF(0, kF, kTimeoutMs);
        shooterTalon.config_kP(0, kP, kTimeoutMs);
        shooterTalon.config_kI(0, kI, kTimeoutMs);
        shooterTalon.config_kD(0, kD, kTimeoutMs);
        shooterTalon.config_IntegralZone(0, iZone, kTimeoutMs);
        shooterTalon.setSelectedSensorPosition(0);
        shooterTalon.setNeutralMode(NeutralMode.Coast);

        // Set up PID checker for derivative
        Supplier<Double> derivSupplier = shooterTalon::getErrorDerivative;
        Predicate<Double> derivPredicate = deriv -> (deriv < 1 && deriv > -1);
        pidFinishRPMDerivative = new PIDFinished<>(derivSupplier, derivPredicate);

        // Set up PID checker for target error
        Supplier<Integer> cleSupplier = shooterTalon::getClosedLoopError;
        Predicate<Integer> clePredicate = clte -> (clte < 50 && clte > -50);
        pidFinishRPMTarget = new PIDFinished<>(cleSupplier, clePredicate);
    }

    /*
     Spinning Related
     */
    public void spinToRPM(int rpm) {
        shooterTalon.set(ControlMode.Velocity, RPMsToVel(rpm));
    }

    public void stop() {
        shooterTalon.set(ControlMode.Current, 0);
    }

    public void spinToDefaultRPM() {
        shooterTalon.set(ControlMode.Velocity, RPMsToVel(defaultRPM));
    }

    public void spinToMaxRPM() {
        shooterTalon.set(ControlMode.Velocity, RPMsToVel(maxRPM));
    }

    /*
    Checks
     */

    public boolean isTargetSet() {
        return (shooterTalon.getControlMode() == ControlMode.Velocity);
    }

    public boolean isRPMDerivativeSettled() {
        return pidFinishRPMDerivative.isStable();
    }

    public boolean isRPMTargetSettled() {
        return pidFinishRPMTarget.isStable();
    }

    public boolean isAtTargetRPM() {
        if (!isTargetSet()) {
            // with no target, just say yes
            return true;
        } else {
            // wait for these to say we're done
            return isRPMDerivativeSettled() && isRPMTargetSettled();
        }
    }

    /*
    Calcs
     */

    private double RPMsToVel(double RPMs) {
        return (RPMs * 2048.0) / 600;
    }

    private double velToRPM(double vel) {
        return (vel * 600.0) / 2048;
    }
}
