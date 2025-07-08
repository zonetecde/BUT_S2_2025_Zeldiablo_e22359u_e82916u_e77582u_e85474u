package Zeldiablo.StrategieDeplacement;


import java.util.PriorityQueue;
import java.util.Stack;

public class AStar {

//Java Program to implement A* Search Algorithm

    //Here we're creating a shortcut for (int, int) pair
    public static class Pair {
        int x;
        int y;
        public Pair(int x, int second){
            this.x = x;
            this.y = second;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Pair && this.x == ((Pair)obj).x && this.y == ((Pair)obj).y;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "first=" + x +
                    ", second=" + y +
                    '}';
        }
    }

    // Creating a shortcut for tuple<int, int, int> type
    public static class Details {
        double value;
        int i;
        int j;

        public Details(double value, int i, int j) {
            this.value = value;
            this.i = i;
            this.j = j;
        }
    }

    // a Cell (node) structure
    public static class Cell {
        public Pair parent;
        // f = g + h, where h is heuristic
        public double f, g, h;
        Cell()
        {
            parent = new Pair(-1, -1);
            f = -1;
            g = -1;
            h = -1;
        }

        public Cell(Pair parent, double f, double g, double h) {
            this.parent = parent;
            this.f = f;
            this.g = g;
            this.h = h;
        }
    }

    // method to check if our cell (row, col) is valid
    boolean isValid(int[][] grid, int rows, int cols,
                    Pair point)
    {
        if (rows > 0 && cols > 0)
            return (point.x >= 0) && (point.x < rows)
                    && (point.y >= 0)
                    && (point.y < cols);

        return false;
    }

    //is the cell blocked?

    boolean isUnBlocked(int[][] grid, int rows, int cols,
                        Pair point)
    {
        return isValid(grid, rows, cols, point)
                && grid[point.x][point.y] == 1;
    }

    //Method to check if destination cell has been already reached
    boolean isDestination(Pair position, Pair dest)
    {
        return position == dest || position.equals(dest);
    }

    // Method to calculate heuristic function
    double calculateHValue(Pair src, Pair dest)
    {
        return Math.sqrt(Math.pow((src.x - dest.x), 2.0) + Math.pow((src.y - dest.y), 2.0));
    }

    // Method for tracking the path from source to destination

    Pair tracePath(
            Cell[][] cellDetails,
            int cols,
            int rows,
            Pair dest)
    {   //A* Search algorithm path

        Stack<Pair> path = new Stack<>();

        int row = dest.x;
        int col = dest.y;

        Pair nextNode = cellDetails[row][col].parent;
        do {
            path.push(new Pair(row, col));
            nextNode = cellDetails[row][col].parent;
            row = nextNode.x;
            col = nextNode.y;
        } while (cellDetails[row][col].parent != nextNode); // until src

        path.pop();
        return path.peek();
    }

// A main method, A* Search algorithm to find the shortest path

    Pair aStarSearch(int[][] grid,
                     int rows,
                     int cols,
                     Pair src,
                     Pair dest)
    {

        if (!isValid(grid, rows, cols, src)) {
            System.out.println("Source is invalid...");
            return null;
        }


        if (!isValid(grid, rows, cols, dest)) {
            System.out.println("Destination is invalid...");
            return null;
        }


        if (!isUnBlocked(grid, rows, cols, src)
                || !isUnBlocked(grid, rows, cols, dest)) {
            System.out.println("Source or destination is blocked...");
            return null;
        }


        if (isDestination(src, dest)) {
            System.out.println("We're already (t)here...");
            return null;
        }


        boolean[][] closedList = new boolean[rows][cols];//our closed list

        Cell[][] cellDetails = new Cell[rows][cols];

        int i, j;
        // Initializing of the starting cell
        i = src.x;
        j = src.y;
        cellDetails[i][j] = new Cell();
        cellDetails[i][j].f = 0.0;
        cellDetails[i][j].g = 0.0;
        cellDetails[i][j].h = 0.0;
        cellDetails[i][j].parent = new Pair( i, j );


        // Creating an open list


        PriorityQueue<Details> openList = new PriorityQueue<>((o1, o2) -> (int) Math.round(o1.value - o2.value));

        // Put the starting cell on the open list, set f.startCell = 0

        openList.add(new Details(0.0, i, j));

        while (!openList.isEmpty()) {
            Details p = openList.peek();
            // Add to the closed list
            i = p.i; // second element of tuple
            j = p.j; // third element of tuple

            // Remove from the open list
            openList.poll();
            closedList[i][j] = true;

            // Generating all the 8 neighbors of the cell
            int[] dx = {-1, 0, 1, 0};  // Up, Right, Down, Left
            int[] dy = {0, 1, 0, -1};

            for (int dir = 0; dir < 4; dir++) {
                Pair neighbour = new Pair(i + dx[dir], j + dy[dir]);
                if (neighbours(grid, rows, cols, dest, neighbour, i, j, cellDetails, closedList, openList)) {
                    if (isDestination(neighbour, dest)) {
                        return tracePath(cellDetails, rows, cols, dest);
                    }
                }
            }
        }

        System.out.println("Failed to find the Destination Cell");
        return null;
    }

    private boolean neighbours(int[][] grid, int rows, int cols, Pair dest,Pair neighbour, int i,
                               int j,
                               Cell[][] cellDetails, boolean[][] closedList, PriorityQueue<Details> openList) {

        if (isValid(grid, rows, cols, neighbour)) {
            if(cellDetails[neighbour.x] == null){ cellDetails[neighbour.x] = new Cell[cols]; }
            if (cellDetails[neighbour.x][neighbour.y] == null) {
                cellDetails[neighbour.x][neighbour.y] = new Cell();
            }

            if (isDestination(neighbour, dest)) {
                cellDetails[neighbour.x][neighbour.y].parent = new Pair (i, j);
                return true;
            }

            else if (!closedList[neighbour.x][neighbour.y]
                    && isUnBlocked(grid, rows, cols, neighbour)) {
                double gNew, hNew, fNew;
                gNew = cellDetails[i][j].g + 1.0;
                hNew = calculateHValue(neighbour, dest);
                fNew = gNew + hNew;

                if (cellDetails[neighbour.x][neighbour.y].f == -1
                        || cellDetails[neighbour.x][neighbour.y].f > fNew) {

                    openList.add(new Details(fNew, neighbour.x, neighbour.y));

                    // Update the details of this
                    // cell
                    cellDetails[neighbour.x][neighbour.y].g = gNew;
                    //heuristic function
                    cellDetails[neighbour.x][neighbour.y].h = hNew;
                    cellDetails[neighbour.x][neighbour.y].f = fNew;
                    cellDetails[neighbour.x][neighbour.y].parent = new Pair(i, j);
                }
            }
        }
        return false;
    }
}