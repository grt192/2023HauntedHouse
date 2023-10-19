// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SolenoidMech extends SubsystemBase {
  
  private double uptime;
  private double downtime;
  private double randomness = 0.0;
  private Timer timer;
  private Solenoid solenoid;


  public SolenoidMech(int channel, double uptime, double downtime) {
    this.uptime = uptime;
    this.downtime = downtime;
    solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, channel);
    timer = new Timer();
    timer.start();
  }

  public SolenoidMech(int channel, double uptime, double downtime, double randomness) {
    this(channel, uptime, downtime);
    this.randomness = randomness;
  }



  double timeUntilNextEvent = 0.0;
  boolean up = false;
  
  public void periodic() {
    if(timer.advanceIfElapsed(timeUntilNextEvent)){
      up = !up;
      timeUntilNextEvent = (up ? uptime : downtime) * (1 + (Math.random() * 2 - 1) * randomness);
    }

    solenoid.set(up);
  }

  
}
