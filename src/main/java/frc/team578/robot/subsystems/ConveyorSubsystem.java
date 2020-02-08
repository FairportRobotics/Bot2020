package frc.team578.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team578.robot.subsystems.interfaces.Initializable;

public class ConveyorSubsystem extends Subsystem implements Initializable {

    private ShootMode shootMode;
    private WPI_TalonSRX talon;
    private DigitalInput frontSensor, backSensor;
    private Joystick joystick;
    private long timeStartWaiting;
    private final double WAIT_TIME_SEC = 2;
    private final double power = .5;

    @Override
    public void initialize() {
        backSensor = new DigitalInput(8);
        frontSensor = new DigitalInput(9);
        talon = new WPI_TalonSRX(4);
        shootMode = ShootMode.INTAKE;
        joystick = new Joystick(1);
        timeStartWaiting = -1;
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

    }

    public void periodic() {

        if(joystick.getRawButton(1) && !shootMode.isOrganizing()){ // shoot button pushed and belt not organizing balls
            shootMode = ShootMode.WAITING;   // transition to waitingToShoot mode
        }

        switch(shootMode){
            case WAITING:
                if(timeStartWaiting == -1)
                    timeStartWaiting = System.currentTimeMillis();
                if(System.currentTimeMillis() - timeStartWaiting > (WAIT_TIME_SEC * 1000)){ // if timeout
                    timeStartWaiting = -1;
                    shootMode = ShootMode.SHOOTING;
                }
                forwardMove();
                if(!backSensor.get()) // if ball in back sensor
                    timeStartWaiting = -1;
                shootMode = ShootMode.SHOOTING;
                break;

            case SHOOTING:
                forwardMove();
                if(backSensor.get()) // if ball to be shot is no longer in back sensor
                    shootMode = ShootMode.BACKING_UP;
                break;

            case WAITING_NEXT_BALL:
                forwardMove();
                if(!backSensor.get()) // if next ball in back sensor
                    shootMode = ShootMode.BACKING_UP;
                break;

            case BACKING_UP:
                backwardMove();
                if(timeStartWaiting == -1)
                    timeStartWaiting = System.currentTimeMillis();
                if(System.currentTimeMillis() - timeStartWaiting > (WAIT_TIME_SEC * 1000)){ // if timeout
                    timeStartWaiting = -1;
                    stopMove();
                    shootMode = ShootMode.INTAKE;
                }
                if(!frontSensor.get()) // if ball gets to front of intake
                    timeStartWaiting = -1;
                shootMode = ShootMode.CRADLE;
                break;

            case CRADLE:
                talon.set(ControlMode.PercentOutput, -.15);
                if(frontSensor.get()){ // if ball no longer front sensor
                    stopMove();
                    shootMode = ShootMode.INTAKE;
                }
                break;

            case INTAKE:
                if(!backSensor.get()){// if Ball in back sensor
                    stopMove();
                } else if(!frontSensor.get()) // if ball in front sensor
                    shootMode = ShootMode.INTAKING;
                break;

            case INTAKING:
                forwardMove();
                if(!backSensor.get()) // if Ball in back sensor
                    shootMode = ShootMode.INTAKE;
                if(frontSensor.get()) // if ball no longer in front sensor
                    shootMode = ShootMode.BACKING_UP;
                break;
        }
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


}
