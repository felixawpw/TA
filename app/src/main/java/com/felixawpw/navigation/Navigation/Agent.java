package com.felixawpw.navigation.Navigation;

import android.util.Log;

public class Agent {
    private static final String TAG = "Agent::class";
    public Path path;
    public PathFinder pathFinder;
    int currentStep;
    int currX, currY;

    public Agent() {

    }

    public Agent(GridMap map, int xStart, int yStart) {
        this.currX = xStart;
        this.currY = yStart;
        this.pathFinder = new PathFinder(map, 500, false);
    }

    public void GoTo(int x, int y) {
        currentStep = 0;
        path = pathFinder.findPath(currX, currY, x, y);
        Log.i(TAG, "Start Navigation, Go To : " + x + y);
        if (path != null) {
            Log.i(TAG, "Steps : " + path.steps.size());

            for (Object step : path.steps)
                Log.i(TAG, String.format("%s %s", ((Step)step).getX(), ((Step)step).getY()));

            if (path.steps.isEmpty()) {

            }
            else {

            }
        }
        else
            Log.e(TAG, "Cannot find path");
    }
}