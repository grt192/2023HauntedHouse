package frc.robot.subsystems;

import edu.wpi.first.hal.FRCNetComm.tInstances;
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
    private int oldState = 3;


    public LukeMech(Solenoid verSolenoid, Solenoid horSolenoid){
        this.verSolenoid = verSolenoid;
        this.horSolenoid = horSolenoid;

        //ik this looks sus but its for ease of change
        vertUp = 7;
        horOut = 5;
        horIn = 3;
        vertDown = 3;

        timer= new Timer();
        timer.start();

    }

    public void periodic() {

        if((state == 1 || state == 2) && !verSolenoid.get()){
            state = 0;
        }
        
        
        if(timer.hasElapsed(vertUp) && state == 0){
            verSolenoid.set(true);
            state = 1;
            timer.reset();
            timer.start();
        }
        else if (timer.hasElapsed(horOut) && state == 1){
            if(verSolenoid.get()){ 
                horSolenoid.set(true);
            }
            state = 2;
            timer.reset();
            timer.start();
        }
        else if (timer.hasElapsed(horIn) && state == 2){
            if(verSolenoid.get()){ 
                horSolenoid.set(false);
            }
            state = 3;
            timer.reset();
            timer.start();
        }
        else if (timer.hasElapsed(vertDown) && state == 3){
            if(!horSolenoid.get()){
                verSolenoid.set(false);
            }
            state = 0;
            timer.reset();
            timer.start();
        }

        // System.out.println((((int) (timer.get() * 10)) / 10.) +" " + state + "," + verSolenoid.get() + " , " + horSolenoid.get());


    }
}
