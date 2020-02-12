package frc.team578.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team578.robot.RobotMap;
import frc.team578.robot.subsystems.interfaces.Initializable;
import frc.team578.robot.utils.Timer2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
Greg : I refactored some of this. The logic should be exactly the same, I just got rid of some of the redundant timer code by using
a new class called Timer2 (based off the WPI class Time).

Still have to hook in the shooter logic.
 */

public class ConveyorSubsystem extends Subsystem implements Initializable {

    private static final Logger log = LogManager.getLogger(ConveyorSubsystem.class);

    private ShootMode shootMode;
    private WPI_TalonSRX conveyorTalon;
    private DigitalInput intakeSensor, shooterSensor;
    private Timer2 timer = new Timer2();

    private final double WAIT_TIME_SEC = 2;
    private final double conveyorPower = .5;
    private final double smallSpeed = .15;


    @Override
    public void initialize() {
        conveyorTalon = new WPI_TalonSRX(RobotMap.CONVEYOR_FEEDER_TALON);
        conveyorTalon.configFactoryDefault();
        conveyorTalon.setNeutralMode(NeutralMode.Brake);
        shooterSensor = new DigitalInput(RobotMap.CONVEYOR_SHOOTER_SENSOR);
        intakeSensor = new DigitalInput(RobotMap.CONVEYOR_INTAKE_SENSOR);
        shootMode = ShootMode.WAITING_FOR_INTAKE_RELOAD;
    }

    enum ShootMode {
        SHOOT_SENSOR_WAITING_FOR_BALL,
        ADVANCE_THRU_SHOOT_SENSOR,
        MOVE_BALLS_TOWARDS_INTAKE_SENSOR,
        ADVANCE_BALLS_SMALL_AMOUNT,
        WAITING_FOR_INTAKE_RELOAD,
        BRING_IN_NEW_BALL;

        public boolean isOrganizing() {
            return this == SHOOT_SENSOR_WAITING_FOR_BALL
                    || this == ADVANCE_THRU_SHOOT_SENSOR
                    || this == MOVE_BALLS_TOWARDS_INTAKE_SENSOR
                    || this == ADVANCE_BALLS_SMALL_AMOUNT;
        }
    }

    @Override
    protected void initDefaultCommand() {
    }


    private ShootMode prevShootMode;

    // This inherited method is being called by the scheduler
    @Override
    public void periodic() {

        if (prevShootMode == null || shootMode != prevShootMode) {
            log.info("Shoot Mode : " + shootMode);
            prevShootMode = shootMode;
        }

        switch (shootMode) {
            // Get a ball up to the shooter sensor
            // Keep moving forward until a ball is sensed
            // Timeout will stop the movement and reset to intake
            // ball detected, move on to putting ball into shooter
            case SHOOT_SENSOR_WAITING_FOR_BALL:

                if (!timer.isRunning())
                    timer.start();
                moveTowardsShooterSensor();

                if (timer.hasPeriodPassed(WAIT_TIME_SEC)) { // if timeout
                    timer.stop();
                    shootMode = ShootMode.WAITING_FOR_INTAKE_RELOAD;
                }

                // detected that ball is in shooter sensor
                if (isBallInShooterSensor()) {
                    timer.stop();
                    shootMode = ShootMode.ADVANCE_THRU_SHOOT_SENSOR;
                }
                break;

            case ADVANCE_THRU_SHOOT_SENSOR:
                // TODO : Do we need a a timeout?

                moveTowardsShooterSensor();

                // wait for the ball to be shot is past the shooter sensor
                if (!isBallInShooterSensor())
                    shootMode = ShootMode.MOVE_BALLS_TOWARDS_INTAKE_SENSOR;
                break;

            case MOVE_BALLS_TOWARDS_INTAKE_SENSOR:
                if (!timer.isRunning())
                    timer.start();

                moveTowardsIntakeSensor();


                if (timer.hasPeriodPassed(WAIT_TIME_SEC)) { // if timeout
                    timer.stop();
                    stop();
                    shootMode = ShootMode.WAITING_FOR_INTAKE_RELOAD;
                }

                if (isBallInIntakeSensor()) // if ball gets to front of intake
                    timer.stop();
                shootMode = ShootMode.ADVANCE_BALLS_SMALL_AMOUNT;
                break;

            case ADVANCE_BALLS_SMALL_AMOUNT:

                advanceBallsSmallAmount();

                if (!isBallInIntakeSensor()) { // if ball no longer front sensor
                    stop();
                    shootMode = ShootMode.WAITING_FOR_INTAKE_RELOAD;
                }
                break;

            // This is the default state
            // Wait for a ball to come in
            // or some other external action to happen.
            case WAITING_FOR_INTAKE_RELOAD:
                // Check for All full
                // Can't push balls forward anymore
                if (isBallInShooterSensor()) {
                    // we might be filled
                    stop();
                } else if (isBallInIntakeSensor())
                    // ball is not in shooter and if ball in intake sensor
                    shootMode = ShootMode.BRING_IN_NEW_BALL;
                break;

            case BRING_IN_NEW_BALL:
                moveTowardsShooterSensor();
                // Stop everything when its filled up
                if (isBallInShooterSensor()) // if Ball in shooter sensor
                    shootMode = ShootMode.WAITING_FOR_INTAKE_RELOAD;

                // When ball is moved in, then move everything back
                if (!isBallInIntakeSensor()) // if ball no longer in intake sensor
                    shootMode = ShootMode.MOVE_BALLS_TOWARDS_INTAKE_SENSOR;
                break;
        }
    }

    private boolean isBallInShooterSensor() {
        return shooterSensor.get();
    }

    private boolean isBallInIntakeSensor() {
        return intakeSensor.get();
    }

    public void moveOneBallIntoShooter() {
        shootMode = ShootMode.SHOOT_SENSOR_WAITING_FOR_BALL;
    }

    // TODO : ?
    public void interruptShooting() {
        shootMode = ShootMode.MOVE_BALLS_TOWARDS_INTAKE_SENSOR;
    }

    // TODO : ?
    public boolean isOrganizing() {
        return shootMode.isOrganizing();
    }

    public void advanceBallsSmallAmount() {
        moveTowardsShooterSensor(smallSpeed);
    }

    public void moveTowardsShooterSensor() {
        moveTowardsShooterSensor(conveyorPower);
    }

    public void moveTowardsIntakeSensor() {
        moveTowardsIntakeSensor(conveyorPower);
    }

    public void moveTowardsShooterSensor(double power) {
        conveyorTalon.set(ControlMode.PercentOutput, power);
    }

    public void moveTowardsIntakeSensor(double power) {
        conveyorTalon.set(ControlMode.PercentOutput, -power);
    }

    public void stop() {
        conveyorTalon.set(ControlMode.PercentOutput, 0);
    }

}
