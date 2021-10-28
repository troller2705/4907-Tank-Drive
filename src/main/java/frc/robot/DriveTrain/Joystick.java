package frc.robot.DriveTrain;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Joystick {

    private XboxController controller;
    private int port;
    private double yLeftAxis;
    private double xRightAxis;



    
    public Joystick(int port) {

        this.controller = new XboxController(port);
        this.port = port;

    }

    public void updateAxisValues() {
        this.yLeftAxis = controller.getY(Hand.kLeft);
        this.xRightAxis = controller.getX(Hand.kLeft);
    }
    
    public double getSpeedAxisLeft() {
        return this.yLeftAxis;
    }       

    public double getDirectionAxisRight() {
        return xRightAxis;
    }


    



    







}