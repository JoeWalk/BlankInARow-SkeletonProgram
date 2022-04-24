package com.company;

public class Main {

    public static class SkeletonProgram {

        char board[][];
        Player playerOne;
        Player playerTwo;

        Console console = new Console();

        public SkeletonProgram() {
            console.printLeaderBoard();

            int nInARow = console.readInteger("How many rows and columns would you like there to be? ");
            board = new char[nInARow][nInARow];

            playerOne = new Player(console.readLine("What is the name of player one? "));
            playerTwo = new Player(console.readLine("What is the name of player two? "));
            playerOne.setScore(0);
            playerTwo.setScore(0);

            do {
                playerOne.setSymbol(console.readChar((playerOne.getName()
                        + " what symbol do you wish to use X or O? ")));
                if (playerOne.getSymbol() != 'X' && playerOne.getSymbol() != 'O') {
                    console.println("Symbol to play must be uppercase X or O");
                }
            } while (playerOne.getSymbol() != 'X' && playerOne.getSymbol() != 'O');

            if (playerTwo.getSymbol() == 'X') {
                playerTwo.setSymbol('O');
            } else {
                playerTwo.setSymbol('X');
            }

            char startSymbol = 'X';
            char replay;

            do {
                int noOfMoves = 0;
                boolean gameHasBeenDrawn = false;
                boolean gameHasBeenWon = false;
                clearBoard(nInARow);
                console.println();
                displayBoard(nInARow);
                if (startSymbol == playerOne.getSymbol()) {
                    console.println(playerOne.getName() + " starts playing " + startSymbol);
                } else {
                    console.println(playerTwo.getName() + " starts playing " + startSymbol);
                }
                console.println();
                char currentSymbol = startSymbol;
                boolean validMove;
                Coordinate coordinate;
                do {

                    do {

                        coordinate = getMoveCoordinates();
                        validMove = checkValidMove(coordinate, board, nInARow);
                        if (!validMove) {
                            console.println("Coordinates invalid, please try again");
                        }
                    } while (!validMove);

                    board[coordinate.getX()][coordinate.getY()] = currentSymbol;
                    displayBoard(nInARow);
                    gameHasBeenWon = checkXOrOHasWon(nInARow);
                    noOfMoves++;

                    if (!gameHasBeenWon) {

                        if (noOfMoves == (nInARow * nInARow)) {
                            gameHasBeenDrawn = true;
                        } else {

                            if (currentSymbol == 'X') {
                                currentSymbol = 'O';
                            } else {
                                currentSymbol = 'X';
                            }

                        }
                    }

                } while (!gameHasBeenWon && !gameHasBeenDrawn);

                if (gameHasBeenWon) {
                    if (playerOne.getSymbol() == currentSymbol) {
                        console.println(playerOne.getName() + " congratulations you win!");
                        playerOne.addScore();
                    } else {
                        console.println(playerTwo.getName() + " congratulations you win!");
                        playerTwo.addScore();
                    }
                } else {
                    console.println("A draw this time!");
                }

                console.println("\n" + playerOne.getName() + " your score is: " + String.valueOf(playerOne.getScore()));
                console.println(playerTwo.getName() + " your score is: " + String.valueOf(playerTwo.getScore()));
                console.println();
                if (startSymbol == playerOne.getSymbol()) {
                    startSymbol = playerTwo.getSymbol();
                } else {
                    startSymbol = playerOne.getSymbol();
                }
                replay = console.readChar("\n Another game Y/N? ");
            } while (replay != 'N' && replay != 'n');

            console.writeFile(playerOne.toString());
            console.writeFile(playerTwo.toString());
        }

        int displayBoard(int nInARow) {
            int row;
            int column;
            console.print("  |");
            for (int i = 1; i <= nInARow; i++){
                console.print(" " + i);
            }
            console.print("\n--+-");
            for (int i = 1; i <= nInARow; i++)
                console.print("--");
            console.println("");
            for (row = 0; row < nInARow; row++) {
                console.write((row + 1) + " | ");
                for (column = 0; column < nInARow; column++) {
                    console.write(board[column][row] + " ");
                }
                console.println();
            }
            return (nInARow);
        }

        void clearBoard(int nInARow) {
            int row;
            int column;
            for (row = 0; row < nInARow; row++) {
                for (column = 0; column < nInARow; column++) {
                    board[column][row] = ' ';
                }
            }
        }

        Coordinate getMoveCoordinates() {
            Coordinate coordinate = new Coordinate((console.readInteger("Enter x Coordinate: ") - 1), (console.readInteger("Enter y Coordinate: ")) - 1);
            return coordinate;
        }

        boolean checkValidMove(Coordinate coordinate, char[][] board, int nInARow) {
            boolean validMove;
            validMove = true;
            if ((coordinate.getX() < 0) || (coordinate.getX() > (nInARow + 1)) || (coordinate.getY() < 0 || coordinate.getY() > (nInARow + 1))) {
                validMove = false;
            }
            else{
                if (board[coordinate.getX()][coordinate.getY()] != ' '){
                    validMove = false;
                }
            }
            return validMove;
        }

        boolean checkXOrOHasWon(int nInARow) {
            boolean xOrOHasWon;
            boolean check = false;
            int row;
            int column;
            xOrOHasWon = false;

            for (column = 0; column < nInARow; column++) {
                check = true;
                for (int i = 0; i < (nInARow - 1); i++) {
                    if ((board[column][i] != board[column][i + 1]) || ((board[column][i] != 'X') && (board[column][i] != 'Y'))) {
                        check = false;
                    }
                }
                if (check == true) {
                    xOrOHasWon = true;
                }
            }
            for (row = 0; row < nInARow; row++) {
                check = true;
                for (int i = 0; i < (nInARow - 1); i++) {
                    if ((board[i][row] != board[i + 1][row]) || ((board[i][row] != 'X') && (board[i][row] != 'Y'))) {
                        check = false;
                    }
                }
                if (check == true) {
                    xOrOHasWon = true;
                }
            }
            check = true;

            for (int i = 0; i < (nInARow - 1); i++) {
                if ((board[i][i] != board[i + 1][i + 1]) || ((board[i][i] != 'X') && (board[i][i] != 'Y'))){
                    check = false;
                }
            }
            if (check == true) {
                xOrOHasWon = true;
            }

            return xOrOHasWon;
        }
    }

    public static void main(String[] args) {
        new SkeletonProgram();
    }
}