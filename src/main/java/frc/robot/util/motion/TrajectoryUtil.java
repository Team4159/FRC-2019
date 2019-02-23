package frc.robot.util.motion;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class TrajectoryUtil {

    public static Trajectory reversePath(Trajectory original) {

        ArrayList<Trajectory.Segment> segments = new ArrayList<>(Arrays.asList(original.segments));
        Collections.reverse(segments);

        double distance = segments.get(0).position;

        return new Trajectory(segments.stream()
                .map(segment -> new Trajectory.Segment(segment.dt, segment.x, segment.y, distance - segment.position, -segment.velocity, -segment.acceleration, -segment.jerk, segment.heading))
                .toArray(Trajectory.Segment[]::new));

    }

    public static Trajectory getTrajectoryFromName(String trajectoryName) throws IOException {

        File trajectoryFile = new File("/home/lvuser/deploy/traj/" + trajectoryName);

        Trajectory trajectory = trajectoryFile.exists() ? Pathfinder.readFromFile(trajectoryFile) : null;

        // Gets a trajectory CSV from user's computer
//        if(trajectory == null){
//            trajectoryFile = new File("~/Desktop/traj/" + trajectoryName);
//            trajectory = trajectoryFile.exists() ? Pathfinder.readFromFile(trajectoryFile): null;
//        }

        return trajectory;

    }
}