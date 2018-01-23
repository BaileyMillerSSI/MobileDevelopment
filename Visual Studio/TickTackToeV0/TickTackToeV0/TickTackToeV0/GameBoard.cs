using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;

namespace TickTackToeV0
{
    /// <summary>
    /// The main game board for the tick tack toe game
    /// Stores the game pieces and allows for game movement
    /// </summary>
    public class GameBoard
    {
        private GamePiece[,] Board = new GamePiece[3, 3];
        private GamePiece _LastMovement = GamePiece.FreeSpace;


        public GameBoard()
        {
            ResetBoard();
        }


        public int Rows
        {
            get
            {
                return Board.GetLength(0);
            }
        }

        public int Columns
        {
            get
            {
                return Board.GetLength(1);
            }
        }
        
        /// <summary>
        /// Stores FreeSpace blocks in every place on the game board
        /// </summary>
        private void ResetBoard()
        {
            // Reset Rows
            for (int i = 0; i < Rows; i++)
            {
                Board[i, 0] = GamePiece.FreeSpace;
            }

            // Reset Columns
            for (int i = 0; i < Columns; i++)
            {
                Board[0, i] = GamePiece.FreeSpace;
            }
        }

        /// <summary>
        /// Checks if the player won against rows and columns
        /// </summary>
        /// <returns></returns>
        private Winner CheckWin()
        {
            Winner win;

            // Do check against Player1
            if (CheckRows(GamePiece.Player1))
            {
                // They won because of rows
                win = new Winner(GamePiece.Player1, null);
                return win;
            }
            else if (CheckColumns(GamePiece.Player1))
            {
                win = new Winner(GamePiece.Player1, null);
                return win;
            }
            // Do check against Player2
            else if (CheckRows(GamePiece.Player2))
            {
                // They won because of rows
                win = new Winner(GamePiece.Player2, null);
                return win;
            }
            else if (CheckColumns(GamePiece.Player2))
            {
                win = new Winner(GamePiece.Player2, null);
                return win;
            }

            return null;

        }

        private bool CheckRows(GamePiece Player)
        {
            GamePiece[] Spots = new GamePiece[3];
            // Check that for each row did this player have 3 next to each other
            for (int i = 0; i < Rows; i++)
            {
                // Within each row check if any one has 3 next to each other
                // Check against the columns
                CheckColumn(Player, i, Spots);

                if (AllMatch(Spots, Player))
                {
                    // They won!
                    // In Row i they have all the pieces lined up
                    return true;
                }
            }
            
            // In none of the rows they had pieces next to each other
            return false;
        }

        private bool CheckColumns(GamePiece Player)
        {
            GamePiece[] Spots = new GamePiece[3];
            // Check that for each row did this player have 3 next to each other
            for (int i = 0; i < Columns; i++)
            {
                // Within each row check if any one has 3 next to each other
                // Check against the columns
                CheckRow(Player, i, Spots);

                if (AllMatch(Spots, Player))
                {
                    // They won!
                    // In Column i they have all the pieces lined up
                    return true;
                }
            }

            // In none of the rows they had pieces next to each other
            return false;
        }

        private bool AllMatch(GamePiece[] Spots, GamePiece Player)
        {
            var checks = new bool[Spots.Length];
            for (int i = 0; i < Spots.Length; i++)
            {
                checks[i] = Spots[i] == Player;
            }

            return !checks.Contains(false);
        }

        private void CheckColumn(GamePiece Player, int Row, GamePiece[] Spots)
        {
            for (int i = 0; i < Columns; i++)
            {
                Spots[i] = Board[Row, i];
            }
        }

        private void CheckRow(GamePiece Player, int Column, GamePiece[] Spots)
        {
            for (int i = 0; i < Rows; i++)
            {
                Spots[i] = Board[i, Column];
            }
        }


        /// <summary>
        /// Sets the spot on the game board for a specific player
        /// </summary>
        /// <param name="Spot">Location on game board</param>
        /// <param name="Player">Player that is placing their spot</param>
        public void SetMovement(GameLocation Spot, GamePiece Player)
        {
            // Makes sure that the location can be used
            if (Spot.X <= Columns && Spot.Y <= Rows)
            {
                Board[Spot.Y, Spot.X] = Player;
                _LastMovement = Player;
            }
        }

        public Winner HasWinner()
        {

            // Check all the Player1 Spots for a win


            // Check all the Player2 Spots for a win


            return null;
        }

        /// <summary>
        /// Returns who the next turn belongs to
        /// </summary>
        /// <returns></returns>
        public GamePiece GetNextPlayer()
        {
            switch (_LastMovement)
            {
                case GamePiece.Player1:
                    return GamePiece.Player2;
                case GamePiece.Player2:
                    return GamePiece.Player1;
                default:
                    return GamePiece.Player1;
            }
        }


    }

    public class Winner
    {
        public GameLocation[] WinningSpots { get; private set; }
        public GamePiece WinningPlayer { get; private set; }

        public Winner(GamePiece Player, GameLocation[] Spots)
        {
            WinningPlayer = Player;
            WinningSpots = Spots;
        }
    }

    /// <summary>
    /// Location object for identifying location on game board
    /// </summary>
    public class GameLocation
    {
        /// <summary>
        /// The Column location on the game board
        /// </summary>
        public int X { get; private set; }

        /// <summary>
        /// The Row location on the game board
        /// </summary>
        public int Y { get; private set; }

        public GameLocation(int x, int y)
        {
            this.X = x;
            this.Y = y;
        }
    }

    public enum GamePiece
    {
        Player1,
        Player2,
        FreeSpace
    }
}
