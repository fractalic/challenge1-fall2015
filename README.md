# CPEN 221 - Fall 2015 - MP1

#### CPEN221 Game


## Introduction
CPEN221 is a simple two-player strategy game played on an *n ⨉ n* board. Each player has a piece, and on each turn the player must move their piece in one of the four cardinal directions *North, South, East and West*, for only one grid per play (skipping grids is not allowed). Each previously occupied place (grid) becomes unavailable to both players, and the players can move their pieces only to available places. A player loses if their piece gets trapped in unavailable space (or unavailable space and the wall). 

However, your program must be able to detect the *earliest* time at which the game finishes; i.e., the first move after which the player will lose no matter what subsequent moves the player makes. Thus you program should declare that the game has ended as soon as this becomes known with certainty, even if both players have available places to move their pieces to; you should also declare the winning player as soon as the latter happens.

There will be two basic playing modes: **Two-player** and **Resume/Replay**. Two other modes, namely **Single-player** and **Movie**, are left as challenges.

##Part 1: Writing APIs and Specs 
The first goal of this assignment is to design a clean, minimal API that would allow players (or bots) to play the game. Those are classes and public member methods that define all the operations required by a bot to play the game, along with the functions required to declare that the game has concluded and announce the winner.

**First read Parts 2 and 3 to acquire a thorough understanding of what is required from your program. Do not think about implementation at this point; just think about the functionalities that you need to expose to “users” of your program; that is, the API.** 

Having read Parts 2 and 3, you are now asked to complete the partial API given. 
You are also asked to write the specs of every public member of your API. *Do not implement any methods here; just focus on the structure of your program, and what is expected from each method in terms of specs.* 

## Part 2: Implementing the Two-Player Mode

Here, two humans will play against each other through the console. Implement the API you wrote in Part 1 for the two player mode. You may find that you will need to add more functions to your API as you proceed. As you advance implementing the game, you will find yourself needing to answer the following questions (and more, of course): 

1. How to represent the state of the board? What data structures are useful ? Tree? Binary Matrix with coordinates? Lists? 
2. **Design Question**: Should a Board object contain the Players? or should each player object store a copy of the Board? How would players communicate (i.e., make their locations known and retrievable by the opponents ?)
3. How to keep track of available locations so that to efficiently retrieve the neighbouring available locations ?
4. How to implement isGameFinished() even when there is a location to move to ?

**A Note on Terminology:** A distinction should be made between *location* and *direction*. A location is specified by coordinates relative to the origin ([1,1], leftmost bottom row here), while direction is one of North, South, East, West, and is relative to the current location of the piece.

### The Graphical User Interface (GUI)
Players will interact and play through the **console**. The board is square, but its size is not fixed: Your program should receive as input the number of grids per side of the board, n, and your board should have *n ⨉ n* grids. You should draw a board on your console. Each grid is specified by coordinates **[row,column]**, where the leftmost bottom grid is the origin and has coordinates [1,1]. The coordinates extend to n in both directions; i.e., x and y axes. 

The player should be able to easily choose one of the four available locations,if any, to move next. That is, without spending much time trying to figure out the the coordinates of each grid. Your board should include an indication of the coordinates of every grid.

At game startup, the console should ask for the desired mode. Display a list of available modes; e.g,
 
1. Two Player
2. Resume/Replay,

where the user can enter either 1 or 2.

In two player mode, player1’s piece should initially be placed in the middle column of the bottom row, and player2’s piece in the middle of the top row. Use the following symbols to mark the board:

* P1, P2 : Location of Player 1 and 2’s pieces, respectively;
* X1, X2 : Spaces previously occupied (and thus made unavailable anymore) by players 1 and 2, respectively.

 
Players will specify their next move by entering one of *N, E, S, W* (case insensitive), corresponding to the four available locations. If the player enters an invalid direction, then your game should notify the player. If the player chooses to move to a location that is unavailable (marked as either X1 or X2), your program should notify the player about this and allow them to enter a different direction. Also, your console should indicate whose turn it is.

**Note:** Your console should NOT scroll down as the game goes on. This will make playing the game a horrible experience. Make sure the board is visible all the time on the console, and its location fixed as players are prompted to enter their directions.  

## Part 3: Implementing Resume Mode—Serialization and Parsing
Your program should “record’’ two-player games. After every move, your program should record the new board configuration and store this record in a file. All board configurations belonging to the same game should be stored in the same file, in chronological order. This will allow players to resume their game in case they decide to quit at any time during the game.   


###Game File — Format and Parse Rules
The following is the standard file format we will use to record a game.

```
START_CONFIG
	DIMENSION: 5
END_CONFIG

START_BOARD
	P1_LOCATION: [1,8]
	P2_LOCATION: [16,7]
END_BOARD

START_BOARD
	P1_LOCATION: [2,8]
	P2_LOCATION: [16,7]
END_BOARD
```

START\_CONFIG, DIMENSION, END\_CONFIG, START\_BOARD, END\_BOARD, P1\_LOCATION, P2\_LOCATION are keywords, and each should be written in its own line (to make it easier for you to parse the file line by line).
 
Each game file should start with the block

``` 
START_CONFIG
		DIMENSION: 5
END_CONFIG
```

The entry DIMENSION is mandatory, and its value should be an integer between 1 to *n*.

A BOARD block should always contain both P1\_LOCATION and P2\_LOCATION entries, each on a newline, and having the form `P1_LOCATION : [row, column]`
(the colon is mandatory, as well as the brackets [ and ]); row and column are integers, each between 1 and DIMENSION.

Every play, or board configuration, is recorded in a BOARD block. When parsing the file, your program should make sure that file is *valid*. The file should always start with a CONFIG block that contains the dimension of the board (number of grids per side of our square board).

The file should contain at least one BOARD block (the initial board) following the CONFIG block. Your parser should ignore empty lines and white spaces in the game file. Your parser should check that the syntax of the loaded game file is correct. Not only the syntax should be correct, but also the sequence of BOARD blocks should be valid; that is, for every consecutive pair of BOARD blocks, you should check that:

1. The two blocks are played by different players, and 
2. In the second block, the player’s move is *legal*; that is, given the set of BOARD blocks parsed so far, the player moves to a location that is both valid (i.e., within the board boundaries), and available.  

Your program should report that the given file is invalid if it does not syntactically follow our rules (you do NOT need to show where the error happened in the file). If the game file is syntactically correct but the game is invalid, your program should report so.

If the given file is valid, then you should load the game in memory; i.e., create all the objects, initialize all the data structures, and store all BOARD configurations to be able to save them later if the user desires so. The process of converting in-memory objects to strings is called Serialization. Deserialization is defined in the obvious way. See [Wikipedia's page on serialization](https://en.wikipedia.org/wiki/Serialization). 
   
### More on the GUI:
Your GUI should indicate a way to the user to “quit and save” the game and store it so that it can be resumed later on. If at any time during the game a player chooses to quit, the console should ask the user whether or not they would like to “save” the game, and if the user answers yes, then the user will be prompted to enter a file name. If the game to be saved is a reloaded (resumed) one; i.e., has been saved before, then give the user the original file name as the “default” option, but allow the user to change this file name. If the user changes the file name of a reloaded game, this change should be reflected on your file system (i.e., rename the file without duplicating it.) 

At startup, if the user chooses **Resume** mode, then the user is prompted for a file path. Your program should look up the given file, and verify that it is valid as described above. Then your program should ***simulate*** the given game a BOARD at a time: On your board, *show every move in the same sequence as in the file, with the occupied locations and pieces marked properly.*

Introduce some delay between every two moves in your simulation so that the replay is visually traceable (and appealing!). The game should subsequently resume, giving control back to the player whose turn is next. If the loaded game is finished, i.e., one player will lose with certainty no matter what moves they make (you are required to compute the earliest such time above), then your program should declare so and announce the winner, and no further plays should be allowed. This latter situation is the realization of the **Replay** mode.


To reload a game, you should implement a **Parser** that works according to rules above. You are given skeleton code you can make use of to scan a given file line by line. It is a partial implementation of the method `bool open(String fileName)` in class *Game*.



## Part 4: Challenge: Implementing Single-Player and Movie Modes — Bots and Strategies 
In this part you will implement bots, i.e., automated players, and their strategies. A bot can use all of the opponent’s moves since the beginning of the game, as well as its own moves, in making its next move decision. The more information a strategy utilizes, the better it is in terms of winning, but the more computationally expensive it is to compute.

Define a new class, called *Strategy*. Include a method in class Player that sets its strategy if the mode is either Single-Player or Movie; e.g., `void setStrategy(Strategy strategy).` 

You are asked to create **two** different strategies (as subclasses of *Strategy*, e.g; *RandomizedStrategy*). The two strategies should implement two different **difficulty levels**: *Easy and Hard*. 

In the Single-Player mode, one player is a human that plays through the console, and the other is a bot. In the Movie mode, the two players are bots.


##Testing

You are asked to develop testing strategies to ensure at least the following:

1. Unavailable locations are never occupied again;
2. Resume/Replay mode works correctly, including both parsing and simulation. We will test your program against our own game files;
3. Your program can correctly detect the first move at which the game is done and the winner becomes known with certainty;
4. Test that your 2 strategies are really of varying difficulty. 



