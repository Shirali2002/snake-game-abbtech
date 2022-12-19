package com.codenjoy.dojo.snake.client;

import com.codenjoy.dojo.services.Direction;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Handler {

    private Handler() {
    }

    public static Direction getDirectionFromSchema(int[][] matrix, int startX, int startY, int endX, int endY) {
        try {
            List<PathPoint> path = getPath(matrix, startX, startY, endX, endY);
            if (path == null || path.isEmpty()) {
                return isOk(matrix, startX, startY);
            }

            PathPoint nextPoint = path.get(1);
            if (startX == endX) {
                if (startY < endY) {
                    return Direction.UP;
                } else {
                    return Direction.DOWN;
                }
            } else {
                if (startX < endX) {
                    return Direction.RIGHT;
                } else {
                    return Direction.LEFT;
                }
            }
        } catch (Exception e) {
            return isOk(matrix, startX, startY);
        }
    }

    private static Direction isOk(int[][] matrix, int x, int y){
        if (matrix[x+1][y] != 1) {
            return Direction.RIGHT;
        } else if (matrix[x-1][y] != 1) {
            return Direction.LEFT;
        } else if (matrix[x][y+1] != 1) {
            return Direction.UP;
        } else if (matrix[x][y-1] != 1) {
            return Direction.DOWN;
        }
        return Direction.RIGHT;
    }

    private static List<PathPoint> getPath(int[][] matrix, int startX, int startY, int endX, int endY) {
        int[][] visited = new int[matrix.length][matrix[0].length];
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        Queue<PathPoint> queue = new LinkedList<>();
        queue.add(new PathPoint(startX, startY));
        visited[startX][startY] = 1;
        while (!queue.isEmpty()) {
            PathPoint point = queue.poll();
            if (point.x == endX && point.y == endY) {
                point.path.add(point);
                return point.path;
            }
            for (int i = 0; i < 4; i++) {
                int newX = point.x + dx[i];
                int newY = point.y + dy[i];
                if (newX >= 0 && newX < matrix.length && newY >= 0 &&
                        newY < matrix[0].length && matrix[newX][newY] == 0
                        && visited[newX][newY] == 0) {
                    visited[newX][newY] = 1;
                    PathPoint newPoint = new PathPoint(newX, newY);
                    newPoint.path.addAll(point.path);
                    newPoint.path.add(point);
                    queue.add(newPoint);
                }
            }
        }
        return Collections.emptyList();
    }


    private static class PathPoint {
        int x;
        int y;
        List<PathPoint> path;

        public PathPoint(int x, int y) {
            this.x = x;
            this.y = y;
            path = new LinkedList<>();
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

}
