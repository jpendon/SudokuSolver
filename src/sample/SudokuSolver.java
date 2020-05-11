package sample;

public class SudokuSolver {
    private int length;

    public SudokuSolver(int length)
    {
        this.length = length;
    }

    public boolean isValid(int[][] grid, int row, int col, int val)
    {
        // Check if the value already exists in row
        for (int i = 0; i < length; i++)
        {
            if (grid[row][i] == val)
                return false;
        }

        // Check if the value already exists in column
        for (int i = 0; i < length; i++)
        {
            if (grid[i][col] == val)
                return false;
        }

        // Check if the box already contains val
        int sqrt = (int) Math.sqrt(length);
        int rowStart = row - row % sqrt;
        int colStart = col - col % sqrt;

        for (int i = rowStart; i < rowStart + sqrt; i++)
        {
            for (int j = colStart; j < colStart + sqrt; j++)
            {
                if (grid[i][j] == val)
                    return false;
            }
        }

        return true;
    }

    public boolean sudokuSolver(int[][] grid)
    {
        int row = 0;
        int col = 0;
        boolean emptyVal = true;

        for (int i = 0; i < length; i++)
        {
            for (int j = 0; j < length; j++)
            {
                if (grid[i][j] == 0)
                {
                    row = i;
                    col = j;
                    emptyVal = false;

                    break;
                }
            }
            if (!emptyVal)
                break;
        }

        if (emptyVal)
            return true;

        //Find suitable value for space
        for (int i = 1; i <= length; i++)
        {
            if (isValid(grid, row, col, i))
            {
                grid[row][col] = i;

                if (sudokuSolver(grid))
                    return true;

                grid[row][col] = 0;
            }
        }
        return false;
    }
}

