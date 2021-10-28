package frc.robot.DriveTrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Talon {
    
    private int address;
    //private double kP, kI, kD;
    private TalonSRX talon;
    private double speed;
    
    public Talon(int address) {
        
        this.address = address;
        talon = new TalonSRX(this.address);
        
    }

    public double getSpeed() {
        return speed;
    }
    
    public void setSpeed(double speed) {
        talon.set(ControlMode.PercentOutput, speed);
    }
    
}