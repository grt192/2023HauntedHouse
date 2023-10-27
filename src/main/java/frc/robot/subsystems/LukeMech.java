package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LukeMech extends SubsystemBase{
    private Timer timer;
    private Solenoid verSolenoid;
    private Solenoid horSolenoid;

    private double vertUp;
    private double vertDown;
    private double horOut;
    private double horIn;
    private int state = 0;


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

        
        if(timer.hasElapsed(vertUp) && state == 0){
            verSolenoid.set(true);
            state = 1;
        }
        else if (timer.hasElapsed(horOut) && state == 1){
            horSolenoid.set(true);
            state = 2;
        }
        else if (timer.hasElapsed(horIn) && state == 2){
            horSolenoid.set(false);
            state = 3;
        }
        else if (timer.advanceIfElapsed(vertDown)){
            verSolenoid.set(false);
            state = 0;
        }


    }
}
