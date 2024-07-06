/* Student Name : Januli Amandrika Kuruppu Nanayakkara
   Student ID   : 20221927
   UOW Number   : 19856555
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Sliding_puzzles {
    class Point {
        int row_position;
        int column_position;
        int weight;
        Point left;
        Point right;
        Point up;
        Point down;

        public Point(int row_position, int column_position) {
            this.row_position = row_position;
            this.column_position = column_position;
        }
    }

    //Counting no.of lines in string
    private int number_of_lines(String name) throws FileNotFoundException{
        File file = new File(name);
        Scanner reader = new Scanner(file);
        int number_of_rows = 0;

        while (reader.hasNextLine()){
            reader.nextLine();
            number_of_rows++;
        }
        return number_of_rows;
    }

    //Reading string maze and create a 2D array
    public String[][] readFile(String file_name) {

        try{
            File file = new File(file_name);
            Scanner reader = new Scanner(file);

            int line_count = number_of_lines(file_name);

            String[][] puzzle_text = new String[line_count][line_count];
            int line_number = 0;
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] line_data = line.split("");
                puzzle_text[line_number] = line_data;
                line_number++;
            }
            return puzzle_text;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Finding starting point of puzzle
    public int[] get_starting_point_index(String[][] puzzle_text, int number_of_rows, int number_of_columns) {
        int[] index = new int[2];
        for (int row = 0; row < number_of_rows; row++) {
            for (int column = 0; column < number_of_columns; column++) {
                System.out.println("Checking row " + row + ", column " + column);
                if (puzzle_text[row][column].equals("S")) {
                    puzzle_text[row][column] = ".";
                    index[0] = row;
                    index[1] = column;
                    return index;
                }
            }
        }
        return null;
    }

    //Finding ending point of puzzle
    public int[] get_ending_point_index(String[][] puzzle_text, int number_of_rows, int number_of_columns) {
        int[] index = new int[2];
        for (int row = 0; row < number_of_rows; row++) {
            for (int column = 0; column < number_of_columns; column++) {
                if (puzzle_text[row][column].equals("F")) {
                    puzzle_text[row][column] = ".";
                    index[0] = row;
                    index[1] = column;
                    return index;
                }
            }
        }
        return null;
    }

    //Building the puzzel from string array
    public Point[][] build_puzzle(String[][] puzzle_text, int number_of_rows, int number_of_columns){
        Point[][] puzzle = new Point[number_of_rows][number_of_columns];

        for (int row = 0; row < number_of_rows; row++) {
            for (int column = 0; column < number_of_columns; column++) {
                if (puzzle_text[row][column].equals(".")) {
                    Point current_pointer = new Point(row, column);
                    if (column - 1 >= 0 && puzzle_text[row][column - 1].equals(".")) {
                        current_pointer.left = puzzle[row][column - 1];
                        if (puzzle[row][column - 1] != null) {
                            puzzle[row][column - 1].right = current_pointer;
                        }
                    }

                    if (column + 1 < number_of_columns && puzzle_text[row][column + 1].equals(".")) {
                        current_pointer.right = puzzle[row][column + 1];
                        if (puzzle[row][column + 1] != null) {
                            puzzle[row][column + 1].left = current_pointer;
                        }
                    }

                    if (row - 1 >= 0 && puzzle_text[row - 1][column].equals(".")) {
                        current_pointer.up = puzzle[row - 1][column];
                        if (puzzle[row - 1][column] != null) {
                            puzzle[row - 1][column].down = current_pointer;
                        }
                    }

                    if (row + 1 < number_of_rows && puzzle_text[row + 1][column].equals(".")) {
                        current_pointer.down = puzzle[row + 1][column];
                        if (puzzle[row + 1][column] != null) {
                            puzzle[row + 1][column].up = current_pointer;
                        }
                    }
                    puzzle[row][column] = current_pointer;
                }
            }
        }
        return puzzle;
    }

    //Do sliding when choosing a direction
    private Point slide(Point sourcing_point, Point ending_Point, String direction){
        sourcing_point.weight = 0;
        Point current_pointer = sourcing_point;

        if (direction.equals("left")) {
            while (current_pointer.left!= null && current_pointer != ending_Point) {
                current_pointer = current_pointer.left;
                sourcing_point.weight++;
            }
        }

        if (direction.equals("right")) {
            while (current_pointer.right!= null && current_pointer != ending_Point) {
                current_pointer = current_pointer.right;
                sourcing_point.weight++;
            }
        }

        if (direction.equals("up")) {
            while (current_pointer.up!= null && current_pointer != ending_Point) {
                current_pointer = current_pointer.up;
                sourcing_point.weight++;
            }
        }

        if (direction.equals("down")) {
            while (current_pointer.down!= null && current_pointer != ending_Point) {
                current_pointer = current_pointer.down;
                sourcing_point.weight++;
            }
        }

        if (sourcing_point.weight != 0) {
            return current_pointer;
        }else {
            return null;
        }
    }

    //Back tracking the shortest path
    private void backtrack(Point start, Point current, HashMap<Point, Point> previous) {
        ArrayList<Point> path = new ArrayList<>();
        path.add(current);
        while (current != start) {
            Point temp = previous.get(current);
            path.add(temp);
            current = temp;
        }

        int j = path.size() - 2;
        System.out.println("1. Start at (" + (start.column_position + 1) + "," + (start.row_position + 1) + ")");
        while (j >= 0){
            if (path.get(j).column_position == path.get(j + 1).column_position) {
                if (path.get(j).row_position < path.get(j + 1).row_position) {
                    System.out.println((path.size() - j) + ". Move up to (" + (path.get(j).column_position + 1) + "," + (path.get(j).row_position + 1) + ")");
                } else {
                    System.out.println((path.size() - j) + ". Move down to (" + (path.get(j).column_position + 1) + "," + (path.get(j).row_position + 1) + ")");
                }
            } else {
                if (path.get(j).column_position < path.get(j + 1).column_position) {
                    System.out.println((path.size() - j) + ". Move left to (" + (path.get(j).column_position + 1) + "," + (path.get(j).row_position + 1) + ")");
                } else {
                    System.out.println((path.size() - j) + ". Move right to (" + (path.get(j).column_position + 1) + "," + (path.get(j).row_position + 1) + ")");
                }
            }
            j--;
        }
        System.out.println((path.size() - j) + ". Done");
    }

    public void dijkstra(Point starting_point, Point ending_point) {
        HashMap<Point, Integer> distance = new HashMap<>();
        distance.put(starting_point, 0);

        String[] directions = {"left", "right", "down", "up"};

        HashMap<Point, Point> previous = new HashMap<>();

        ArrayList<Point> k = new ArrayList<>();
        k.add(starting_point);

        while (k.size() > 0) {
            Point current = k.get(0);
            k.remove(0);

            for (int j = 0; j < 4; j++) {
                Point temp = slide(current, ending_point, directions[j]);
                if (temp != null && !distance.containsKey(temp)) {
                    distance.put(temp, distance.get(current) + current.weight);
                    previous.put(temp, current);
                    k.add(temp);
                } else if (temp != null && distance.get(current) + current.weight <= distance.get(temp)) {
                    distance.put(temp, distance.get(current) + current.weight);
                    previous.put(temp, current);
                }
            }
        }

        if (distance.containsKey(ending_point)) {
            backtrack(starting_point, ending_point, previous);
        }
    }
}
