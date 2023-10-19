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
  private final SolenoidMech christineMech;
  private final SolenoidMech gregMech;
  private final SolenoidMech williamMech;



  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    christineMech = new SolenoidMech(pcm1, 0, 5, 5);
    gregMech = new SolenoidMech(pcm1, 4, 5, 5);
    williamMech = new SolenoidMech(pcm1, 5, 5, 5);
    seanMech = new SolenoidMech(pcm2, 0, 5, 5);
    riyaMech = new SolenoidMech(pcm2, 1, 5, 5);
    vivienMech = new SolenoidMech(pcm2, 2, 5, 5);
    // Configure the trigger bindings
  }

}
