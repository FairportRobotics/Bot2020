package frc.team578.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team578.robot.Robot;
import frc.team578.robot.RobotMap;
import frc.team578.robot.commands.SpinInConveyorCommand;
import frc.team578.robot.subsystems.interfaces.Initializable;

public class ConveyorSubsystem extends Subsystem implements Initializable {

    private ShootMode shootMode;
    private WPI_TalonSRX talon;
    private DigitalInput intakeSensor, shooterSensor;
    private Joystick joystick;
    private long timeStartWaiting;
    private final double WAIT_TIME_SEC = 2;
    private final double power = .5;
    private final long TIME_AFTER_SHOOTING_SEC = 1;
    private long doneShootingTime;

    @Override
    public void initialize() {
        shooterSensor = new DigitalInput(RobotMap.CONVEYOR_SHOOTER_SENSOR);
        intakeSensor = new DigitalInput(RobotMap.CONVEYOR_INTAKE_SENSOR);
        talon = new WPI_TalonSRX(RobotMap.CONVEYOR_FEEDER_TALON);
        shootMode = ShootMode.INTAKE;
        timeStartWaiting = -1;
        doneShootingTime = -1;
    }

    enum ShootMode {
        WAITING,
        SHOOTING,
        WAITING_NEXT_BALL,
        BACKING_UP,
        CRADLE,
        INTAKE,
        INTAKING;

        public boolean isOrganizing() {
            return this == WAITING || this == SHOOTING || this == WAITING_NEXT_BALL || this == BACKING_UP || this == CRADLE;
        }
    }


    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new SpinInConveyorCommand());
    }

    public void periodic() {

        switch(shootMode) {
            case WAITING:
                if (timeStartWaiting == -1)
                    timeStartWaiting = System.currentTimeMillis();
                if (System.currentTimeMillis() - timeStartWaiting > (WAIT_TIME_SEC * 1000)) { // if timeout
                    timeStartWaiting = -1;
                    shootMode = ShootMode.SHOOTING;
                }
                forwardMove();
                if (!shooterSensor.get()) // if ball in back sensor
                    timeStartWaiting = -1;
                shootMode = ShootMode.SHOOTING;
                break;

            case SHOOTING:
                forwardMove();
                if (shooterSensor.get()) // if ball to be shot is no longer in back sensor
                    shootMode = ShootMode.BACKING_UP;
                break;

            case WAITING_NEXT_BALL:
                forwardMove();
                if (!shooterSensor.get()) // if next ball in back sensor
                    shootMode = ShootMode.BACKING_UP;
                break;

            case BACKING_UP:
                backwardMove();
                if (timeStartWaiting == -1)
                    timeStartWaiting = System.currentTimeMillis();
                if (System.currentTimeMillis() - timeStartWaiting > (WAIT_TIME_SEC * 1000)) { // if timeout
                    timeStartWaiting = -1;
                    stopMove();
                    shootMode = ShootMode.INTAKE;
                }
                if (!intakeSensor.get()) // if ball gets to front of intake
                    timeStartWaiting = -1;
                shootMode = ShootMode.CRADLE;
                break;

            case CRADLE:
                talon.set(ControlMode.PercentOutput, -.15);
                if (intakeSensor.get()) { // if ball no longer front sensor
                    stopMove();
                    shootMode = ShootMode.INTAKE;
                    initDoneAfterShooting();
                }
                break;

            case INTAKE:
                if (!shooterSensor.get()) {// if Ball in back sensor
                    stopMove();
                } else if (!intakeSensor.get()) // if ball in front sensor
                    shootMode = ShootMode.INTAKING;
                break;

            case INTAKING:
                forwardMove();
                if (!shooterSensor.get()) // if Ball in back sensor
                    shootMode = ShootMode.INTAKE;
                if (intakeSensor.get()) // if ball no longer in front sensor
                    shootMode = ShootMode.BACKING_UP;
                break;
        }

        shootAfterButtonPush();
    }

    public void forwardMove(){
        talon.set(ControlMode.PercentOutput, -power);
    }
    public void backwardMove(){
        talon.set(ControlMode.PercentOutput, power);
    }
    public void stopMove(){
        talon.set(ControlMode.PercentOutput, 0);
    }
    private void shootAfterButtonPush() {
        if(doneShootingTime != -1) {
            if (System.currentTimeMillis() >= doneShootingTime + TIME_AFTER_SHOOTING_SEC * 1000 && doneShootingTime != -1)
                doneShootingTime = -1;
            // TODO : shooter rev motor, actusally have it stop aboave method though since its more like a boolean
          //  subsistem.shootingmotorthing
        }
    }
    private void initDoneAfterShooting(){
        doneShootingTime = System.currentTimeMillis();
    }

    public void pollFeedInput() {
        periodic();
    }

    public void shootOnce() {


        // shoot button pushed and belt not organizing balls
        if (!shootMode.isOrganizing()) {
            Robot.shooterSubsystem.startUpShooter();
            shootMode = ShootMode.WAITING; // transition to waitingToShoot mode
        }
    }


}
