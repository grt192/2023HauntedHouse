package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class LukeMech {
    private Timer timer;
    private Solenoid verSolenoid;
    private Solenoid horSolenoid;

    private double vertUp;
    private double vertDown;
    private double horOut;
    private double horIn;


    public LukeMech(Solenoid verSolenoid, Solenoid horSolenoid){
        this.verSolenoid = verSolenoid;
        this.horSolenoid = horSolenoid;

        //ik this looks sus but its for ease of change
        vertUp = 7;
        horOut = vertUp + 2;
        horIn = horOut + 3;
        vertDown = horIn + 2;

        timer= new Timer();
        timer.start();

    }

    public void periodic() {
        if(timer.hasElapsed(vertUp)){
            verSolenoid.set(true);
        }
        else if (timer.hasElapsed(horOut)){
            horSolenoid.set(true);
        }
        else if (timer.hasElapsed(horIn)){
            horSolenoid.set(false);
        }
        else if (timer.advanceIfElapsed(vertDown)){
            verSolenoid.set(false);
        }


    }
}
