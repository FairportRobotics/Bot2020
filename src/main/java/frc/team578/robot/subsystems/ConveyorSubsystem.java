package frc.team578.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team578.robot.RobotMap;
import frc.team578.robot.commands.debug.ConveyorDebugSpinForwardCommand;
import frc.team578.robot.subsystems.interfaces.Initializable;
import frc.team578.robot.utils.Timer2;

/*
Greg : I refactored some of this. The logic should be exactly the same, I just got rid of some of the redundant timer code by using
a new class called Timer2 (based off the WPI class Time).

Still have to hook in the shooter logic.
 */

public class ConveyorSubsystem extends Subsystem implements Initializable {

    private ShootMode shootMode;
    private WPI_TalonSRX talon;
    private DigitalInput intakeSensor, shooterSensor;


    private Timer2 timeStartWaiting = new Timer2();
    private Timer2 doneShootingTime = new Timer2();
    private final long TIME_AFTER_SHOOTING_SEC = 1;
    private final double WAIT_TIME_SEC = 2;
    private final double conveyorPower = .5;
    private final double cradleSpeed =  -.15;


    @Override
    public void initialize() {
        talon = new WPI_TalonSRX(RobotMap.CONVEYOR_FEEDER_TALON);
        shooterSensor = new DigitalInput(RobotMap.CONVEYOR_SHOOTER_SENSOR);
        intakeSensor = new DigitalInput(RobotMap.CONVEYOR_INTAKE_SENSOR);
        shootMode = ShootMode.INTAKE;
    }

    enum ShootMode {
        WAITING,
        SHOOTING,
        WAITING_NEXT_BALL,
        BACKING_UP,
        CRADLE,
        INTAKE,
        INTAKING;

        // TODO : Not Being Called?
        public boolean isOrganizing() {
            return this == WAITING || this == SHOOTING || this == WAITING_NEXT_BALL || this == BACKING_UP || this == CRADLE;
        }
    }


    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ConveyorDebugSpinForwardCommand());
    }

    public void periodic() {

        switch(shootMode) {
            case WAITING:
                if (!timeStartWaiting.isRunning())
                    timeStartWaiting.start();
                if (timeStartWaiting.hasPeriodPassed(WAIT_TIME_SEC)) { // if timeout
                    timeStartWaiting.stop();
                    shootMode = ShootMode.SHOOTING;
                }
                moveForward();
                if (!shooterSensor.get()) // if ball in back sensor
                    timeStartWaiting.stop();
                shootMode = ShootMode.SHOOTING;
                break;

            case SHOOTING:
                moveForward();
                if (shooterSensor.get()) // if ball to be shot is no longer in back sensor
                    shootMode = ShootMode.BACKING_UP;
                break;

            case WAITING_NEXT_BALL:
                moveForward();
                if (!shooterSensor.get()) // if next ball in back sensor
                    shootMode = ShootMode.BACKING_UP;
                break;

            case BACKING_UP:
                moveBackward();
                if (!timeStartWaiting.isRunning())
                    timeStartWaiting.start();
                if (timeStartWaiting.hasPeriodPassed(WAIT_TIME_SEC)) { // if timeout
                    timeStartWaiting.stop();
                    stop();
                    shootMode = ShootMode.INTAKE;
                }
                if (!intakeSensor.get()) // if ball gets to front of intake
                    timeStartWaiting.stop();
                shootMode = ShootMode.CRADLE;
                break;

            case CRADLE:
                talon.set(ControlMode.PercentOutput,cradleSpeed);
                if (intakeSensor.get()) { // if ball no longer front sensor
                    stop();
                    shootMode = ShootMode.INTAKE;
                    initDoneAfterShooting();
                }
                break;

            case INTAKE:
                if (!shooterSensor.get()) {// if Ball in back sensor
                    stop();
                } else if (!intakeSensor.get()) // if ball in front sensor
                    shootMode = ShootMode.INTAKING;
                break;

            case INTAKING:
                moveForward();
                if (!shooterSensor.get()) // if Ball in back sensor
                    shootMode = ShootMode.INTAKE;
                if (intakeSensor.get()) // if ball no longer in front sensor
                    shootMode = ShootMode.BACKING_UP;
                break;
        }

        shootAfterButtonPush();
    }

    public void moveForward(){
        talon.set(ControlMode.PercentOutput, -conveyorPower);
    }
    public void moveBackward(){
        talon.set(ControlMode.PercentOutput, conveyorPower);
    }
    public void stop(){
        // TODO : Should this be setting any state enums?
        talon.set(ControlMode.PercentOutput, 0);
    }
    private void shootAfterButtonPush() {
        if(doneShootingTime.isRunning() && doneShootingTime.hasPeriodPassed(TIME_AFTER_SHOOTING_SEC)) {
            // if (System.currentTimeMillis() >= doneShootingTime + TIME_AFTER_SHOOTING_SEC * 1000 && doneShootingTime != -1)
                doneShootingTime.stop();
            // TODO : shooter rev motor, actusally have it stop aboave method though since its more like a boolean
          //  subsistem.shootingmotorthing
        }
    }
    private void initDoneAfterShooting(){
        doneShootingTime.start();
    }

    public void pollFeedInput() {
        periodic();
    }

    public void shootOnce() {
//        // shoot button pushed and belt not organizing balls
//        if (!shootMode.isOrganizing()) {
//            Robot.shooterSubsystem.spinToMaxRPM();
//            shootMode = ShootMode.WAITING; // transition to waitingToShoot mode
//        }
    }
}
