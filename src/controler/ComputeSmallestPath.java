package controler;

import model.Intersection;
import model.Map;
import model.Segment;

import java.util.*;

/**
 * Algorithm that compute the smallest path between two intersections.
 *
 * @author T-REXANOME
 */
public class ComputeSmallestPath {
    protected Map map;

    /**
     * Constructor
     *
     * @param map map
     */
    public ComputeSmallestPath(Map map) {
        this.map = map;
    }

    /**
     * Compute the smallest path between two intersections.
     *
     * @param from departure intersection
     * @param to   arrival intersection
     * @return shortest path
     */
    public List<Intersection> computeSmallestPath(Intersection from, Intersection to) {
        List<Intersection> computedPath;
        Queue<Intersection> openSet = new PriorityQueue<>();
        Intersection start = new Intersection(from, null, 0, computeCost(from, to));
        openSet.add(start);

        HashMap<Long, Intersection> closedMap = new HashMap<>();
        while (!openSet.isEmpty()) {
            Intersection next = openSet.poll();
            if (next.equals(to)) {
                computedPath = new ArrayList<>();
                Intersection current = next;
                do {
                    computedPath.add(0, current);
                    if (current.getPrevious() != null) {
                        current = closedMap.get(current.getPrevious().getId());
                    }
                } while (current != null && current.getPrevious() != null);
                return computedPath;
            }
            List<Segment> listNeighbours = next.getListSegments();
            for (Segment s : listNeighbours) {
                long idNextNode = s.getDestination();
                Intersection neighbour = this.map.getListIntersections().get(idNextNode);

                if (!(closedMap.containsKey(neighbour.getId()) || (openSet.contains(neighbour) && neighbour.getRouteScore() < next.getRouteScore() + 1))) {
                    neighbour.setPrevious(next);
                    neighbour.setRouteScore(next.getRouteScore() + 1);
                    neighbour.setEstimatedScore(neighbour.getRouteScore() + computeCost(neighbour, to));
                    openSet.add(neighbour);
                }
            }
            closedMap.put(next.getId(), next);
        }
        return null;
    }

    /**
     * calculate the geometric distance between two intersections.
     *
     * @param from first intersection
     * @param to   second intersection
     * @return distance between the two intersections
     */
    protected double computeCost(Intersection from, Intersection to) {
        double R = 6372.8; /* Earth's Radius, in kilometers */

        double dLat = Math.toRadians(to.getLatitude() - from.getLatitude());
        double dLon = Math.toRadians(to.getLongitude() - from.getLongitude());
        double lat1 = Math.toRadians(from.getLatitude());
        double lat2 = Math.toRadians(to.getLatitude());

        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));

        return R * c;
    }
}
