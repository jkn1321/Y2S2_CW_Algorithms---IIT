/* Student Name : Januli Amandrika Kuruppu Nanayakkara
   Student ID   : 20221927
   UOW Number   : 19856555
 */

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        //Changing folder name to run test cases in puzzles folder. Changed as "test_cases/puzzles/"
        String file_path = "test_cases/puzzles/";
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the file name in'"+file_path+"': ");
        String file_name = sc.nextLine();

        System.out.println("File Name: " +file_name);
        System.out.println();

        Sliding_puzzles slidingPuzzles = new Sliding_puzzles();

        String[][] puzzle_text = slidingPuzzles.readFile(file_path + file_name);
        long start = System.currentTimeMillis();

        int[] starting_point_index = slidingPuzzles.get_starting_point_index(puzzle_text, puzzle_text[0].length, puzzle_text.length);
        int[] ending_point_index = slidingPuzzles.get_ending_point_index(puzzle_text, puzzle_text[0].length, puzzle_text.length);

        Sliding_puzzles.Point[][] puzzle = slidingPuzzles.build_puzzle(puzzle_text, puzzle_text[0].length, puzzle_text.length);

        Sliding_puzzles.Point starting_point = puzzle[starting_point_index[0]][starting_point_index[1]];
        Sliding_puzzles.Point ending_point = puzzle[ending_point_index[0]][ending_point_index[1]];

        slidingPuzzles.dijkstra(starting_point, ending_point);
        long end = System.currentTimeMillis();
        long elapsed_time = end - start;

        System.out.println("Time taken for file (ms) ");
        System.out.println(String.format("%20s %20s \r\n", file_name, elapsed_time + "ms"));

    }
}