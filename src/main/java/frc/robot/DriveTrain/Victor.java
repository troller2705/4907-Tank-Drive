package frc.robot.DriveTrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Victor {
    
    private int address;
    //private double kP, kI, kD;
    private VictorSPX victor;
    private double speed;
    
    public Victor(int address) {
        
        this.address = address;
        victor = new VictorSPX(this.address);
        
    }

    public double getSpeed() {
        return speed;
    }
    
    public void setSpeed(double speed) {
        victor.set(ControlMode.PercentOutput, speed);
    }
    
}