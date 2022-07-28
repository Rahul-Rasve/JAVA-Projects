package com.company;
import java.util.*;
import java.util.LinkedList;

class Board{
    final int RowCnt, ColCnt;
    private Cell[][] cells;

    public Board(int RowCnt, int ColCnt){
        this.RowCnt = RowCnt;
        this.ColCnt = ColCnt;

        cells = new Cell[RowCnt][ColCnt];
        for(int i=0; i<RowCnt; i++){
            for(int j=0; j<ColCnt; j++){
                cells[i][j] = new Cell(i,j);
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void generateFood(){
        int row,col;
        System.out.println("Generating Food!");
        while(true){
            row = (int)(Math.random()*RowCnt);
            col = (int)(Math.random()*ColCnt);
            if(cells[row][col].getCellType() != CellType.SNAKE_NODE){ //snake ate the food
                break;
            }
        }
        cells[row][col].setCellType(CellType.FOOD);
        System.out.println("Food Generated At : " + row + " " + col);
    }
}

class Snake{
    private LinkedList<Cell> snakeBody = new LinkedList<>();
    private Cell head;

    public LinkedList<Cell> getSnakeBody() {
        return snakeBody;
    }

    public void setSnakeBody(LinkedList<Cell> snakeBody) {
        this.snakeBody = snakeBody;
    }

    public Cell getHead() {
        return head;
    }

    public void setHead(Cell head) {
        this.head = head;
    }

    public Snake(Cell initPos){
        head = initPos;
        snakeBody.add(head);
        head.setCellType(CellType.SNAKE_NODE);
    }

    public void grow(){
        snakeBody.add(head);
    }

    public void move(Cell nextCell){
        System.out.println("Snake Moving to " + nextCell.getRow() + " " + nextCell.getCol());
        Cell tail = snakeBody.removeLast();
        tail.setCellType(CellType.EMPTY);

        head = nextCell;
        head.setCellType(CellType.SNAKE_NODE);
        snakeBody.addFirst(head);
    }

    public boolean crash(Cell nextCell){
        System.out.println("Checking for Crash!");
        for(Cell element : snakeBody){
            if(element == nextCell){
                return true;
            }
        }
        return false;
    }
}

enum CellType{
    EMPTY,
    FOOD,
    SNAKE_NODE;
}

class Cell{
    private int row,col;
    private CellType cellType;

    Cell(int row, int col){
        this.row = row;
        this.col = col;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}

public class Snake_Game {
    public static int NONE=0,RIGHT=6,LEFT=4,UP=8,DOWN=2;
    private Snake snake;
    private Board board;
    private int direction;
    private boolean GameOver;

    public Snake_Game(Snake snake, Board board){
        this.snake = snake;
        this.board = board;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean isGameOver(){
        return GameOver;
    }

    public void setGameOver(boolean gameOver) {
        GameOver = gameOver;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    private Cell getNextCell(Cell currPos){
        System.out.println("Finding Next Cell!");
        int row = currPos.getRow();
        int col = currPos.getCol();

        if(direction == RIGHT){
            col++;
        }
        else if(direction == LEFT){
            col--;
        }
        else if(direction == UP){
            row--;
        }
        else if(direction == DOWN){
            row++;
        }

        Cell nextCell = board.getCells()[row][col];
        return nextCell;
    }

    public void update(){
        System.out.println("Updating Game!");
        if(!GameOver){
            if(direction != NONE){
                Cell nextCell = getNextCell(snake.getHead());
                if(snake.crash(nextCell)){
                    setDirection(NONE);
                    GameOver = true;
                }
                else{
                    snake.move(nextCell);
                    if(nextCell.getCellType() == CellType.FOOD){
                        snake.grow();
                        board.generateFood();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Staring the Game!");

        Cell initPos = new Cell(0,0);
        Snake initSnake = new Snake(initPos);
        Board board = new Board(10,10);
        Snake_Game newGame = new Snake_Game(initSnake, board);
        newGame.GameOver = false;

        newGame.direction = RIGHT;
        for(int i=0; i<5; i++){
            if(i == 2){
                newGame.board.generateFood();
            }
            newGame.update();
            if(i == 3){
                newGame.direction = RIGHT;
            }
            if(newGame.GameOver == true){
                break;
            }
        }
    }
}
