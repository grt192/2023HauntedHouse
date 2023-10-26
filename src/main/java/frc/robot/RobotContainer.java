// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.SolenoidMech;
import edu.wpi.first.wpilibj.PneumaticsControlModule;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private final PneumaticsControlModule pcm1 = new PneumaticsControlModule(0);
  private final PneumaticsControlModule pcm2 = new PneumaticsControlModule(1);
  
  // The robot's subsystems and commands are defined here...
  private final SolenoidMech vivienMech;
  private final SolenoidMech seanMech;
  private final SolenoidMech riyaMech;
  private final SolenoidMech aryaLEDs;
  private final SolenoidMech gregMech;
  private final SolenoidMech williamMech;


  public RobotContainer() {
    aryaLEDs = new SolenoidMech(pcm1.makeSolenoid(2), 5, 5);
    gregMech = new SolenoidMech(pcm1.makeSolenoid(4), 20, 20);
    williamMech = new SolenoidMech(pcm1.makeSolenoid(5), 1.5, 1.5);
    seanMech = new SolenoidMech(pcm2.makeSolenoid(0), 7, 5);
    riyaMech = new SolenoidMech(pcm2.makeSolenoid(2), 7.5, 14);
    vivienMech = new SolenoidMech(pcm2.makeSolenoid(4), 5, 5);
   
  }

}
