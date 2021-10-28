// Copyright Alex 2020 -- Software Piracy Punishable by Death

package frc.robot.DriveTrain;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class DriveTrain {
    
    private VictorSPX frontLeft, frontRight;
    private TalonSRX backLeft, backRight;
    private double speed, direction;
    private Joystick joystick;
    
    public DriveTrain(VictorSPX fl, VictorSPX fr, TalonSRX bl, TalonSRX br) {
        this.frontLeft = fl;
        this.frontRight = fr;
        this.backLeft = bl;
        this.backRight = br;
    }
    
    public double getSpeed() {
        return speed;
    }

    public double getDirection() {
        return direction;
    }
    
    public void setSpeedAndDirection(double speed, double direction) {
        this.speed = speed;
        this.direction = direction;
    }
    
    public void execute() {
        joystick.updateAxisValues();

        this.speed =  joystick.getSpeedAxisLeft();
        this.direction = joystick.getDirectionAxisRight();

        double leftSpeed, rightSpeed;

        if (this.speed > 0) {
            leftSpeed = (this.speed + this.direction >= 1) ? 1 : this.speed + this.direction;
            rightSpeed = (this.speed - this.direction <= -1) ? -1 : this.speed - this.direction;
        }
        else {

            rightSpeed = (this.speed + this.direction >= 1) ? 1 : this.speed + this.direction;
            leftSpeed = (this.speed - this.direction <= -1) ? -1 : this.speed - this.direction;

        }

        this.frontLeft.set(VictorSPXControlMode.PercentOutput, leftSpeed);
        this.backLeft.set(TalonSRXControlMode.PercentOutput, leftSpeed);
        this.frontRight.set(VictorSPXControlMode.PercentOutput, rightSpeed);
        this.backRight.set(TalonSRXControlMode.PercentOutput, rightSpeed);


    }
    
}