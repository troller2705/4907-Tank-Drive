/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveTrain.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  // private static PowerDistributionPanel pdp;
  private TalonSRX bl, br;
  private VictorSPX fl, fr;
  private static DriveTrain tankdrive;
  private XboxController xbox;
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    // pdp = new PowerDistributionPanel(0);


    bl = new TalonSRX(3);
    br = new TalonSRX(4);
    br.setInverted(true);
    bl.setInverted(true);
    xbox = new XboxController(0);
    fl = new VictorSPX(1);
    fr = new VictorSPX(2);
    fl.setInverted(true);
    fr.setInverted(true);
    // tankdrive = new DriveTrain(fl, fr, bl, br);
    fl.follow(bl);
    fr.follow(br);
    bl.configContinuousCurrentLimit(40);
    br.configContinuousCurrentLimit(40);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

    // SmartDashboard.putNumber("Speed", tankdrive.getSpeed());
    // SmartDashboard.putNumber("Direction", tankdrive.getDirection());

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);
    if (xbox.getXButton() == true) {
      if (x < 0) {
        double direction = ((Math.sqrt(15) * (Math.sqrt(x * -1) / 60))); // Test for a exponential turning speed. Nominally (x / 60)
        if (direction < 0.1) direction = 0.1;
        double speed = (xbox.getY(Hand.kLeft) * 0.5);
        bl.set(TalonSRXControlMode.PercentOutput, speed + direction);
        br.set(TalonSRXControlMode.PercentOutput, (speed * -1) + direction);
      }
      else {
        double direction = ((Math.sqrt(30) * (Math.sqrt(x) / 60 * -1))); // Test for a exponential turning speed. Nominally (x / 60)
        double speed = (xbox.getY(Hand.kLeft) * 0.5);
        bl.set(TalonSRXControlMode.PercentOutput, speed + direction);
        br.set(TalonSRXControlMode.PercentOutput, (speed * -1) + direction);
      }


    }
    else {
      double speed = (xbox.getY(Hand.kLeft) * 0.5); 
      double direction = (xbox.getX(Hand.kRight) * -1 * 0.5);
      if (direction > 0.05) {
        bl.set(TalonSRXControlMode.PercentOutput, speed + direction);
        br.set(TalonSRXControlMode.PercentOutput, (speed * -1) + direction);
      }
      else {
        bl.set(TalonSRXControlMode.PercentOutput, (speed * 2)); // Allows full speed if not turning
        br.set(TalonSRXControlMode.PercentOutput, ((speed * -1)* 2)); // nominally (speed) & (speed * -1)
      }
    }




  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

}
