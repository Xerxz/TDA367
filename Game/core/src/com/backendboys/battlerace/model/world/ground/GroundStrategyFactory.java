package com.backendboys.battlerace.model.world.ground;

/**
 * Factory for creating ground strategies.
 */
public class GroundStrategyFactory {

    private GroundStrategyFactory() {
    }

    /**
     * @param numberVertices The amount of vertices the ground should be based on.
     * @param minHeight      Minimum value on y-axis.
     * @param step           The difference between each vertex on the x-axis.
     * @return
     */
    public static IGroundStrategy getSinStrategy(int numberVertices, int minHeight, double step) {
        return new SinStrategy(numberVertices, minHeight, step);
    }

    /**
     * @param numberVertices The amount of vertices the ground should be based on.
     * @param minHeight      Minimum value on y-axis.
     * @param step           The difference between each vertex on the x-axis.
     * @return
     */
    public static IGroundStrategy getSinCosATanStrategy(int numberVertices, int minHeight, double step) {
        return new SinCosATanStrategy(numberVertices, minHeight, step);
    }

    /**
     * @param numberVertices The amount of vertices the ground should be based on.
     * @param minHeight      Minimum value on y-axis.
     * @param step           The difference between each vertex on the x-axis.
     * @return
     */
    public static IGroundStrategy getFlatStrategy(int numberVertices, int minHeight, double step) {
        return new FlatStrategy(numberVertices, minHeight, step);
    }

}
