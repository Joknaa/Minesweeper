import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Board extends JPanel {

    //<editor-fold desc="Ugly Vars">
    private final int SPRITES_COUNT = 13;
    private final int CELL_SIZE = 30;

    private final int CELL_UNDISCOVERED = 10;
    private final int CELL_MARKED = 10;
    private final int CELL_EMPTY = 0;
    private final int CELL_HAS_MINE = 9;
    private final int COVERED_MINE_CELL = CELL_HAS_MINE + CELL_UNDISCOVERED;
    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + CELL_MARKED;

    private final int DRAW_MINE = 9;
    private final int DRAW_COVER = 10;
    private final int DRAW_MARK = 11;
    private final int DRAW_WRONG_MARK = 12;

    private final int MINES_COUNT = 40;
    private final int ROWS_COUNT = 16;
    private final int COLUMNS_COUNT = 16;

    private final int BOARD_WIDTH = COLUMNS_COUNT * CELL_SIZE + 1;
    private final int BOARD_HEIGHT = ROWS_COUNT * CELL_SIZE + 1;
    private final JLabel statusbar;
    private int[] board;
    private boolean playing;
    private int minesLeft;
    private Image[] sprites;
    private int allCells;
    //</editor-fold>

    public Board(JLabel statusbar) {
        this.statusbar = statusbar;
        LoadSprites();
        SetupBoard();
        newGame();
    }

    private void SetupBoard() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        addMouseListener(new MinesAdapter());
    }

    private void LoadSprites() {
        sprites = new Image[SPRITES_COUNT];
        for (int i = 0; i < SPRITES_COUNT; i++) {
            sprites[i] = (new ImageIcon("resources/" + i + ".png")).getImage();
        }
    }

    private void newGame() {
        int cell;
        var random = new Random();
        playing = true;
        minesLeft = MINES_COUNT;

        allCells = ROWS_COUNT * COLUMNS_COUNT;
        board = new int[allCells];

        for (int i = 0; i < allCells; i++) {
            board[i] = CELL_UNDISCOVERED;
        }

        statusbar.setText("Mines Left: " + minesLeft);

        int i = 0;

        while (i < MINES_COUNT) {

            int position = (int) (allCells * random.nextDouble());

            if ((position < allCells) && (board[position] != COVERED_MINE_CELL)) {

                int current_col = position % COLUMNS_COUNT;
                board[position] = COVERED_MINE_CELL;
                i++;

                if (current_col > 0) {
                    cell = position - 1 - COLUMNS_COUNT;
                    if (cell >= 0) {
                        if (board[cell] != COVERED_MINE_CELL) {
                            board[cell] += 1;
                        }
                    }
                    cell = position - 1;
                    if (cell >= 0) {
                        if (board[cell] != COVERED_MINE_CELL) {
                            board[cell] += 1;
                        }
                    }

                    cell = position + COLUMNS_COUNT - 1;
                    if (cell < allCells) {
                        if (board[cell] != COVERED_MINE_CELL) {
                            board[cell] += 1;
                        }
                    }
                }

                cell = position - COLUMNS_COUNT;
                if (cell >= 0) {
                    if (board[cell] != COVERED_MINE_CELL) {
                        board[cell] += 1;
                    }
                }

                cell = position + COLUMNS_COUNT;
                if (cell < allCells) {
                    if (board[cell] != COVERED_MINE_CELL) {
                        board[cell] += 1;
                    }
                }

                if (current_col < (COLUMNS_COUNT - 1)) {
                    cell = position - COLUMNS_COUNT + 1;
                    if (cell >= 0) {
                        if (board[cell] != COVERED_MINE_CELL) {
                            board[cell] += 1;
                        }
                    }
                    cell = position + COLUMNS_COUNT + 1;
                    if (cell < allCells) {
                        if (board[cell] != COVERED_MINE_CELL) {
                            board[cell] += 1;
                        }
                    }
                    cell = position + 1;
                    if (cell < allCells) {
                        if (board[cell] != COVERED_MINE_CELL) {
                            board[cell] += 1;
                        }
                    }
                }
            }
        }
    }

    private void find_empty_cells(int j) {

        int current_col = j % COLUMNS_COUNT;
        int cell;

        if (current_col > 0) {
            cell = j - COLUMNS_COUNT - 1;
            if (cell >= 0) {
                if (board[cell] > CELL_HAS_MINE) {
                    board[cell] -= CELL_UNDISCOVERED;
                    if (board[cell] == CELL_EMPTY) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j - 1;
            if (cell >= 0) {
                if (board[cell] > CELL_HAS_MINE) {
                    board[cell] -= CELL_UNDISCOVERED;
                    if (board[cell] == CELL_EMPTY) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + COLUMNS_COUNT - 1;
            if (cell < allCells) {
                if (board[cell] > CELL_HAS_MINE) {
                    board[cell] -= CELL_UNDISCOVERED;
                    if (board[cell] == CELL_EMPTY) {
                        find_empty_cells(cell);
                    }
                }
            }
        }

        cell = j - COLUMNS_COUNT;
        if (cell >= 0) {
            if (board[cell] > CELL_HAS_MINE) {
                board[cell] -= CELL_UNDISCOVERED;
                if (board[cell] == CELL_EMPTY) {
                    find_empty_cells(cell);
                }
            }
        }

        cell = j + COLUMNS_COUNT;
        if (cell < allCells) {
            if (board[cell] > CELL_HAS_MINE) {
                board[cell] -= CELL_UNDISCOVERED;
                if (board[cell] == CELL_EMPTY) {
                    find_empty_cells(cell);
                }
            }
        }

        if (current_col < (COLUMNS_COUNT - 1)) {
            cell = j - COLUMNS_COUNT + 1;
            if (cell >= 0) {
                if (board[cell] > CELL_HAS_MINE) {
                    board[cell] -= CELL_UNDISCOVERED;
                    if (board[cell] == CELL_EMPTY) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + COLUMNS_COUNT + 1;
            if (cell < allCells) {
                if (board[cell] > CELL_HAS_MINE) {
                    board[cell] -= CELL_UNDISCOVERED;
                    if (board[cell] == CELL_EMPTY) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + 1;
            if (cell < allCells) {
                if (board[cell] > CELL_HAS_MINE) {
                    board[cell] -= CELL_UNDISCOVERED;
                    if (board[cell] == CELL_EMPTY) {
                        find_empty_cells(cell);
                    }
                }
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {

        int uncover = 0;

        for (int i = 0; i < ROWS_COUNT; i++) {

            for (int j = 0; j < COLUMNS_COUNT; j++) {

                int cell = board[(i * COLUMNS_COUNT) + j];

                if (playing && cell == CELL_HAS_MINE) {

                    playing = false;
                }

                if (!playing) {
                    if (cell == COVERED_MINE_CELL) {
                        cell = DRAW_MINE;
                    } else if (cell == MARKED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_WRONG_MARK;
                    } else if (cell > CELL_HAS_MINE) {
                        cell = DRAW_COVER;
                    }
                } else {

                    if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > CELL_HAS_MINE) {
                        cell = DRAW_COVER;
                        uncover++;
                    }
                }

                g.drawImage(sprites[cell], (j * CELL_SIZE),
                        (i * CELL_SIZE), this);
            }
        }

        if (uncover == 0 && playing) {

            playing = false;
            statusbar.setText("Game won");

        } else if (!playing) {
            statusbar.setText("Game lost");
        }
    }

    private class MinesAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;

            boolean doRepaint = false;

            if (!playing) {

                newGame();
                repaint();
            }

            if ((x < COLUMNS_COUNT * CELL_SIZE) && (y < ROWS_COUNT * CELL_SIZE)) {

                if (e.getButton() == MouseEvent.BUTTON3) {

                    if (board[(cRow * COLUMNS_COUNT) + cCol] > CELL_HAS_MINE) {

                        doRepaint = true;

                        if (board[(cRow * COLUMNS_COUNT) + cCol] <= COVERED_MINE_CELL) {

                            if (minesLeft > 0) {
                                board[(cRow * COLUMNS_COUNT) + cCol] += CELL_MARKED;
                                minesLeft--;
                                String msg = Integer.toString(minesLeft);
                                statusbar.setText("Mines Left: " + msg);
                            } else {
                                statusbar.setText("No marks left");
                            }
                        } else {

                            board[(cRow * COLUMNS_COUNT) + cCol] -= CELL_MARKED;
                            minesLeft++;
                            String msg = Integer.toString(minesLeft);
                            statusbar.setText("Mines Left: " + msg);
                        }
                    }

                } else {

                    if (board[(cRow * COLUMNS_COUNT) + cCol] > COVERED_MINE_CELL) {

                        return;
                    }

                    if ((board[(cRow * COLUMNS_COUNT) + cCol] > CELL_HAS_MINE)
                            && (board[(cRow * COLUMNS_COUNT) + cCol] < MARKED_MINE_CELL)) {

                        board[(cRow * COLUMNS_COUNT) + cCol] -= CELL_UNDISCOVERED;
                        doRepaint = true;

                        if (board[(cRow * COLUMNS_COUNT) + cCol] == CELL_HAS_MINE) {
                            playing = false;
                        }

                        if (board[(cRow * COLUMNS_COUNT) + cCol] == CELL_EMPTY) {
                            find_empty_cells((cRow * COLUMNS_COUNT) + cCol);
                        }
                    }
                }

                if (doRepaint) {
                    repaint();
                }
            }
        }
    }
}
