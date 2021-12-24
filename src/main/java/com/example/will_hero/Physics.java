package com.example.will_hero;

public class Physics {
    private static double gravity = -9.8;

    // CONVENTION FOR UNITS
    // HEIGHT = PIXELS
    // TIME - FRAMES = 1/60 seconds

    // v = u + at
    //velocity changed in one frame downwards
    public static double velocityChangeDownwards(double initialVelocity) {
        return initialVelocity + gravity;
    }
    //s = (v^2 - u^2) /( 2 * a )
    public static double jumpHeight(double u) {
        double v = 0;
        double a = gravity;
        return (Math.pow(v, 2) - Math.pow(u, 2)) /( 2 * a );
    }

    //helper function for displacement wrt gravity
    public static double dispGravity(double u, double t){
        return displacement(u, t, gravity);
    }

    // displacement in a second
    public static double dispSecond(double velocity, double a){
        return velocity + (0.5 * a);
    }

    //displacement against gravity in a second
    public static double dispGravSecond(double velocity){
        return dispSecond(velocity, gravity);
    }

    // s = ut + (at^2)/2
    public static double displacement(double velocity, double duration, double accn) {
        return velocity * duration + 0.5 * accn * Math.pow(duration, 2);
    }
}
