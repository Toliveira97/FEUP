gameMode(pvp).
gameMode(pvb).
gameMode(bvb).

gameState(whiteToMove).
gameState(blackToMove).
gameState(whiteVictorious).
gameState(blackVictorious).
gameState(tie).

%%% Game[Board, gameState, gameMode];

createPvPGame(Game):-
	stalemateBoard(Board),
	Game = [Board, whiteToMove, pvp], !,
	bb_put(blackCanTieFlag, 0).

createPvCGame(Game):-
	initialBoard(Board),
	random(0,2,Color),
	(
		Color == 0, Game = [Board, whiteToMove, pvcWhite], !;
		Game = [Board, whiteToMove, pvcBlack], !
	),
	bb_put(blackCanTieFlag, 0).

createCvCGame(Game):-
	initialBoard(Board),
	random(0,2,Color),
	(
		Color == 0, Game = [Board, whiteToMove, cvcWhite], !;
		Game = [Board, whiteToMove, cvcBlack], !
	),
	bb_put(blackCanTieFlag, 0).

getGameState(Game, GameState):-
	nth0(1,Game, GameState).

getGameMode(Game, GameMode):-
	nth0(2,Game, GameMode).

%Game Over
playGame(Game):-
	getGameState(Game, GameState),
	getBoard(Game, Board),
	(
		GameState == whiteVictorious -> clearConsole, printBoard(Board),
			(write('# Game over. White player won, congratulations!'), nl);
		GameState == blackVictorious -> clearConsole, printBoard(Board),
			(write('# Game over. Black player won, congratulations!'), nl);
		GameState == tie -> clearConsole, printBoard(Board),
			(write('# Game over. We got a tie, good game!'), nl);
		fail
	),
	pressEnterToContinue, !.

%Game Manager
playGame(Game):-
	getGameMode(Game, GameMode),
	(
		GameMode == pvp -> (getBoard(Game, Board), clearConsole, printBoard(Board), printGameInfo(Game), humanTurn(Game, ContinueGame), playGame(ContinueGame), !);
		GameMode == pvcWhite ->(
			getBoard(Game, Board), clearConsole, printBoard(Board), printGameInfo(Game), humanTurn(Game, ContinueGame),
			getBoard(ContinueGame, ContinueBoard), clearConsole, printBoard(ContinueBoard), printGameInfo(ContinueGame), nl,nl, pressEnterToContinue, somehowSmartBotTurn(ContinueGame, BotContinueGame),
			playGame(BotContinueGame), !);
		GameMode == pvcBlack -> (
			getBoard(Game, Board), clearConsole, printBoard(Board), printGameInfo(Game), nl,nl, pressEnterToContinue, somehowSmartBotTurn(Game, ContinueGame), %TODO: RandomBot Opponent
			getBoard(ContinueGame, ContinueBoard), clearConsole, printBoard(ContinueBoard), printGameInfo(ContinueGame), humanTurn(ContinueGame, HumanContinueGame),
			playGame(HumanContinueGame), !);
		GameMode == cvcWhite -> (
			getBoard(Game, Board), clearConsole, printBoard(Board), printGameInfo(Game), nl,nl, pressEnterToContinue, botTurn(Game, ContinueGame),
			getBoard(ContinueGame, ContinueBoard), clearConsole, printBoard(ContinueBoard), printGameInfo(ContinueGame), nl,nl, pressEnterToContinue, somehowSmartBotTurn(ContinueGame, BotContinueGame),
			playGame(BotContinueGame), !);
		GameMode == cvcBlack -> (
			getBoard(Game, Board), clearConsole, printBoard(Board), printGameInfo(Game), nl,nl, pressEnterToContinue, somehowSmartBotTurn(Game, ContinueGame),
			getBoard(ContinueGame, ContinueBoard), clearConsole, printBoard(ContinueBoard), printGameInfo(ContinueGame), nl,nl, pressEnterToContinue, botTurn(ContinueGame, BotContinueGame),
			playGame(BotContinueGame), !)
	).

printGameInfo(Game):-
	getGameState(Game, GameState),
	(
		GameState == whiteToMove ->
			(write('# White Player Turn '), nl, nl);
		GameState == blackToMove ->
			(write('# Black Player Turn '), nl, nl);
		fail
	).

%Game Cycle Human
humanTurn(Game, ContinueGame):-
	getBoard(Game, Board),
	repeat,
	getSourceCoords(SrcCol, SrcRow),
	convertToNumber(SrcCol, SrcColNumber),
	getPiece(Board, SrcColNumber, SrcRow, Piece),
	getGameState(Game, GameState),
	validateOwnershipWrapper(Piece, GameState, 1),
	getDestinyCoords(DestCol, DestRow),
	convertToNumber(DestCol, DestColNumber),
	validateMove(SrcColNumber, SrcRow, DestColNumber, DestRow, Board, 1),
	makeMove(Board, SrcColNumber, SrcRow, DestColNumber, DestRow, NextBoard),
	updateGameState(Game, NextBoard, ContinueGame).

somehowSmartBotTurn(Game, ContinueGame):-
	getGameState(Game, GameState),
	(
	GameState == whiteVictorious;
	GameState == blackVictorious;
	GameState == tie
	),
	write(GameState),
	ContinueGame = Game.

	%Game Cycle Smart Bot - tries somehow Smart move
somehowSmartBotTurn(Game, ContinueGame):-
	getBoard(Game, Board),
	getGameState(Game, GameState),
	(
		GameState == whiteToMove -> getPiece(Board, SrcCol, SrcRow, 'King', 'White');
		getPiece(Board, SrcCol, SrcRow, 'King', 'Black')
	),
	DestRow is SrcRow + 1,
	(
		%TODO: Make this tries in a random order
		(DestCol is SrcCol, validateMove(SrcCol, SrcRow, DestCol, DestRow, Board, 0));
		(DestCol is SrcCol + 1, validateMove(SrcCol, SrcRow, DestCol, DestRow, Board, 0));
		(DestCol is SrcCol - 1, validateMove(SrcCol, SrcRow, DestCol, DestRow, Board, 0))
	),
	makeMove(Board, SrcCol, SrcRow, DestCol, DestRow, NextBoard),
	updateGameState(Game, NextBoard, ContinueGame).

%Game Cycle Smart Bot - tries Random move
somehowSmartBotTurn(Game, ContinueGame):-
	botTurn(Game, ContinueGame).

%Check if Game as over in the first Play
botTurn(Game, ContinueGame):-
	getGameState(Game, GameState),
	(
	GameState == whiteVictorious;
	GameState == blackVictorious;
	GameState == tie
	),
	ContinueGame = Game.

%Game Cycle Random Bot
botTurn(Game, ContinueGame):-
	getBoard(Game, Board),
	repeat,
	random(1, 9, SrcRow),
	random(1, 9, SrcCol),
	getPiece(Board, SrcCol, SrcRow, Piece),
	getGameState(Game, GameState),
	validateOwnershipWrapper(Piece, GameState, 0),
	random(1, 9, DestRow),
	random(1, 9, DestCol),
	validateMove(SrcCol, SrcRow, DestCol, DestRow, Board, 0),
	makeMove(Board, SrcCol, SrcRow, DestCol, DestRow, NextBoard),
	updateGameState(Game, NextBoard, ContinueGame).

	%Interaction functions

getInputCoords(SrcCol, SrcRow):-
	getColChar(SrcCol),
	getRowInt(SrcRow),
	get_code(_).

getSourceCoords(SrcCol,SrcRow):-
	write('Coords of Piece To Move: '), nl,
	getInputCoords(SrcCol, SrcRow), nl.

getDestinyCoords(SrcCol,SrcRow):-
	write('Coords of Piece New Position: '), nl,
	getInputCoords(SrcCol, SrcRow), nl.

	%Validation functions

validateOwnershipWrapper(Piece, GameState, Flag):-
 validateOwnership(Piece, GameState, Flag), !.

validateOwnership(Piece, GameState, _):-
	GameState == whiteToMove,
	getPieceColor(Piece, Color),
	Color == 'White'.

validateOwnership(Piece, GameState, _):-
	GameState == blackToMove,
	getPieceColor(Piece, Color),
	Color == 'Black'.

validateOwnership(_, _, Flag):-
	Flag == 1,
	write('Invalid Piece!'), nl,
	pressEnterToContinue, !,
	fail.

validateOwnership(_, _, _):-
	fail.

validateMove(SrcCol, SrcRow, DestCol, DestRow, Board, Flag):-
	differentPositions(SrcCol, SrcRow, DestCol, DestRow, Board, Flag), !,
	differentColors(SrcCol, SrcRow, DestCol, DestRow, Board, Flag), !,
	getPiece(Board, SrcCol, SrcRow, Piece),
	getPieceName(Piece, PieceName),
	validBasicMove(PieceName, SrcCol, SrcRow, DestCol, DestRow, Flag), !,
	checkForJumping(PieceName, SrcCol, SrcRow, DestCol, DestRow, Board, Flag), !,
	makeMove(Board, SrcCol, SrcRow, DestCol, DestRow, TempBoard), !,
	checkForCheck(Board, TempBoard, Flag).

differentPositions(SrcCol, SrcRow, DestCol, DestRow, Board, _):-
	SrcRow =\= DestRow ; SrcCol =\= DestCol.

differentPositions(_, _, _, _, Board, Flag):-
	invalidMove(Board, Flag).

differentColors(SrcCol, SrcRow, DestCol, DestRow, Board, _):-
	getPiece(Board, SrcCol, SrcRow, PieceSrc),
	getPiece(Board, DestCol, DestRow, PieceDest),
	getPieceColor(PieceSrc, ColorSrc),
	getPieceColor(PieceDest, ColorDest),
	ColorSrc \== ColorDest.

differentColors(_, _, _, _, Board, Flag):-
	invalidMove(Board, Flag).

invalidMove(Board, Flag):-
	Flag == 1,
	clearConsole,
	printBoard(Board),
	write('Invalid Move!'), nl,
	pressEnterToContinue, !,
	fail.

invalidMove(_, _):-
	fail.

checkForJumping('Rook', SrcCol, SrcRow, DestCol, DestRow, Board, _):-
	SrcCol == DestCol,
	DiffRows is (DestRow-SrcRow),
	DiffRows < 0, %Down
	findPieceOnCol(SrcCol, DestRow, SrcRow, Board).

checkForJumping('Rook', SrcCol, SrcRow, DestCol, DestRow, Board, _):-
	SrcCol == DestCol,
	DiffRows is (DestRow-SrcRow),
	DiffRows > 0, %UP
	findPieceOnCol(SrcCol, SrcRow, DestRow, Board).

checkForJumping('Rook', SrcCol, SrcRow, DestCol, DestRow, Board, _):-
	SrcRow == DestRow,
	DiffCols is (DestCol-SrcCol),
	DiffCols > 0, %Right
	findPieceOnRow(SrcRow, SrcCol, DestCol, Board).

checkForJumping('Rook', SrcCol, SrcRow, DestCol, DestRow, Board, _):-
	SrcRow == DestRow,
	DiffCols is (DestCol-SrcCol),
	DiffCols < 0, %Left
	findPieceOnRow(SrcRow, DestCol, SrcCol, Board).

checkForJumping('Bishop', SrcCol, SrcRow, DestCol, DestRow, Board, _):-
	DiffRows is (DestRow - SrcRow),
	DiffRows > 0, %UP
	DiffCols is (DestCol-SrcCol),
	DiffCols < 0, %Left

	LowRow is (SrcRow+1),
	HighCol is (SrcCol-1),
	findPieceOnDiagonalLeft(LowRow, HighCol, DestRow, DestCol, Board).

checkForJumping('Bishop', SrcCol, SrcRow, DestCol, DestRow, Board, _):-
	DiffRows is (DestRow - SrcRow),
	DiffRows < 0, %Down
	DiffCols is (DestCol-SrcCol),
	DiffCols > 0, %Right

	LowRow is (DestRow+1),
	HighCol is (DestCol-1),
	findPieceOnDiagonalLeft(LowRow, HighCol, SrcRow, SrcCol, Board).

checkForJumping('Bishop', SrcCol, SrcRow, DestCol, DestRow, Board, _):-
	DiffRows is (DestRow - SrcRow),
	DiffRows > 0, %UP
	DiffCols is (DestCol-SrcCol),
	DiffCols > 0, %Right

	LowRow is (SrcRow+1),
	LowCol is (SrcCol+1),
	findPieceOnDiagonalRight(LowRow, LowCol, DestRow, DestCol, Board).

checkForJumping('Bishop', SrcCol, SrcRow, DestCol, DestRow, Board, _):-
	DiffRows is (DestRow - SrcRow),
	DiffRows < 0, %Down
	DiffCols is (DestCol-SrcCol),
	DiffCols < 0, %Left

	LowRow is (DestRow+1),
	LowCol is (DestCol+1),
	findPieceOnDiagonalRight(LowRow, LowCol, SrcRow, SrcCol, Board).

checkForJumping('Queen', SrcCol, SrcRow, DestCol, DestRow, Board, _):-
	SrcCol == DestCol,
	DiffRows is (DestRow-SrcRow),
	DiffRows < 0, %Down
	findPieceOnCol(SrcCol, DestRow, SrcRow, Board).

checkForJumping('Queen', SrcCol, SrcRow, DestCol, DestRow, Board, _):-
	SrcCol == DestCol,
	DiffRows is (DestRow-SrcRow),
	DiffRows > 0, %UP
	findPieceOnCol(SrcCol, SrcRow, DestRow, Board).

checkForJumping('Queen', SrcCol, SrcRow, DestCol, DestRow, Board, _):-
	SrcRow == DestRow,
	DiffCols is (DestCol-SrcCol),
	DiffCols > 0, %Right
	findPieceOnRow(SrcRow, SrcCol, DestCol, Board).

checkForJumping('Queen', SrcCol, SrcRow, DestCol, DestRow, Board, _):-
	SrcRow == DestRow,
	DiffCols is (DestCol-SrcCol),
	DiffCols < 0, %Left
	findPieceOnRow(SrcRow, DestCol, SrcCol, Board).

checkForJumping('Queen', SrcCol, SrcRow, DestCol, DestRow, Board, _):-
	DiffRows is (DestRow - SrcRow),
	DiffRows > 0, %UP
	DiffCols is (DestCol-SrcCol),
	DiffCols < 0, %Left

	LowRow is (SrcRow+1),
	HighCol is (SrcCol-1),
	findPieceOnDiagonalLeft(LowRow, HighCol, DestRow, DestCol, Board).

checkForJumping('Queen', SrcCol, SrcRow, DestCol, DestRow, Board, _):-
	DiffRows is (DestRow - SrcRow),
	DiffRows < 0, %Down
	DiffCols is (DestCol-SrcCol),
	DiffCols > 0, %Right

	LowRow is (DestRow+1),
	HighCol is (DestCol-1),
	findPieceOnDiagonalLeft(LowRow, HighCol, SrcRow, SrcCol, Board).

checkForJumping('Queen', SrcCol, SrcRow, DestCol, DestRow, Board, _):-
	DiffRows is (DestRow - SrcRow),
	DiffRows > 0, %UP
	DiffCols is (DestCol-SrcCol),
	DiffCols > 0, %Right

	LowRow is (SrcRow+1),
	LowCol is (SrcCol+1),
	findPieceOnDiagonalRight(LowRow, LowCol, DestRow, DestCol, Board).

checkForJumping('Queen', SrcCol, SrcRow, DestCol, DestRow, Board, _):-
	DiffRows is (DestRow - SrcRow),
	DiffRows < 0, %Down
	DiffCols is (DestCol-SrcCol),
	DiffCols < 0, %Left

	LowRow is (DestRow+1),
	LowCol is (DestCol+1),
	findPieceOnDiagonalRight(LowRow, LowCol, SrcRow, SrcCol, Board).

checkForJumping('King', SrcCol, SrcRow, DestCol, DestRow, Board, _).
checkForJumping('Knight', SrcCol, SrcRow, DestCol, DestRow, Board, _).

checkForJumping(_, _, _, _, _, Board, Flag):-
	invalidMove(Board, Flag).

makeMove(Board, SrcCol, SrcRow, DestCol, DestRow, TempBoard):-
	getPiece(Board, SrcCol, SrcRow, Piece),
	nonePiece(NonePiece),
	setPiece(Board, SrcCol, SrcRow, NonePiece, TempTempBoard),
	setPiece(TempTempBoard, DestCol, DestRow, Piece, TempBoard).

checkForCheck(Board, TempBoard, Flag):-
	getPiece(TempBoard, WhiteKingCol, WhiteKingRow, 'King', 'White'),
	getPiece(TempBoard, BlackKingCol, BlackKingRow, 'King', 'Black'),
	\+(makePseudoMoves('Black', TempBoard, WhiteKingCol, WhiteKingRow)),
	\+(makePseudoMoves('White', TempBoard, BlackKingCol, BlackKingRow)).

checkForCheck(Board, TempBoard, Flag):-
	invalidMove(Board, Flag),
	fail.

makePseudoMoves('Black', TempBoard, DestCol, DestRow):-
	getPiece(TempBoard, Col, Row, PieceName, PieceColor),
	PieceColor == 'Black',
	validBasicMove(PieceName, Col, Row, DestCol, DestRow, 0),
	checkForJumping(PieceName, Col, Row, DestCol, DestRow, TempBoard, 0).

makePseudoMoves('White', TempBoard, DestCol, DestRow):-
	getPiece(TempBoard, Col, Row, PieceName, PieceColor),
	PieceColor == 'White',
	validBasicMove(PieceName, Col, Row, DestCol, DestRow, 0),
	checkForJumping(PieceName, Col, Row, DestCol, DestRow, TempBoard, 0).

updateGameState(Game, NextBoard, ContinueGame):-
	gameOver(Game, NextBoard, ContinueGame).

updateGameState(Game, NextBoard, ContinueGame):-
	changeTurn(Game, NextBoard, ContinueGame).

gameOver(Game, NextBoard, ContinueGame):-
	getGameState(Game, GameState),
	GameState == whiteToMove,
	\+(canMoveAnyPiece('Black', NextBoard)),
	getGameMode(Game, GameMode),
	ContinueGame = [NextBoard, tie, GameMode],!.

gameOver(Game, NextBoard, ContinueGame):-
	getGameState(Game, GameState),
	GameState == blackToMove,
	\+(canMoveAnyPiece('White', NextBoard)),
	getGameMode(Game, GameMode),
	ContinueGame = [NextBoard, tie, GameMode],!.

gameOver(Game, NextBoard, ContinueGame):-
	kingOnLastRow('White', NextBoard),
	kingOnLastRow('Black', NextBoard),
	getGameMode(Game, GameMode),
	ContinueGame = [NextBoard, tie, GameMode],!.

gameOver(Game, NextBoard, ContinueGame):-
	kingOnLastRow('White', NextBoard),
	bb_get(blackCanTieFlag, BlackCanTieFlag),
	write(BlackCanTieFlag),
	BlackCanTieFlag == 1,
	getGameMode(Game, GameMode),
	ContinueGame = [NextBoard, whiteVictorious, GameMode], !.

gameOver(Game, NextBoard, ContinueGame):-
	kingOnLastRow('White', NextBoard),
	\+(blackCanTie(NextBoard)),
	getGameMode(Game, GameMode),
	ContinueGame = [NextBoard, whiteVictorious, GameMode], !.

gameOver(Game, NextBoard, ContinueGame):-
	kingOnLastRow('Black', NextBoard),
	getGameMode(Game, GameMode),
	ContinueGame = [NextBoard, blackVictorious, GameMode], !.

kingOnLastRow(Color, Board):-
	getPiece(Board, _, 8, 'King', Color).

blackCanTie(Board):-
	getPiece(Board, Col, 7, 'King', 'Black'),
	differentColors(Col, 7, Col, 8, Board, 0),
	makeMove(Board, Col, 7, Col, 8, NextBoard),
	checkForCheck(NextBoard, NextBoard, 0),      % Workaround
	bb_put(blackCanTieFlag, 1).

blackCanTie(Board):-
	getPiece(Board, Col, 7, 'King', 'Black'),
	NextCol is Col+1,
	differentColors(Col, 7, NextCol, 8, Board,0),
	makeMove(Board, Col, 7, NextCol, 8, NextBoard),
	checkForCheck(NextBoard, NextBoard, 0),
	bb_put(blackCanTieFlag, 1).

blackCanTie(Board):-
	getPiece(Board, Col, 7, 'King', 'Black'),
	LastCol is Col-1,
	differentColors(Col, 7, LastCol, 8, Board,0),
	makeMove(Board, Col, 7, LastCol, 8, NextBoard),
	checkForCheck(NextBoard, NextBoard, 0),
	bb_put(blackCanTieFlag, 1).

canMoveAnyPiece(Color, Board):-
	getPiece(Board, Col, Row, PieceName, PieceColor),
	PieceColor == Color,
	write(PieceName), nl,
	testAllCols(Col, Row, 1, Board).

testAllCols(SrcCol, SrcRow, DestCol, Board):-
	write(DestCol),
	DestCol > 0,
	DestCol < 9,
	testAllRows(SrcCol, SrcRow, DestCol, 1, Board).

testAllCols(SrcCol, SrcRow, DestCol, Board):-
	DestCol == 9, !,
	fail.

testAllCols(SrcCol, SrcRow, DestCol, Board):-
	NextCol is DestCol + 1,
	testAllCols(SrcCol, SrcRow, NextCol, Board).

testAllRows(SrcCol, SrcRow, DestCol, DestRow, Board):-
	DestRow > 0,
	DestRow < 9,
	write(DestCol), write(DestRow), nl,
	validateMove(SrcCol, SrcRow, DestCol, DestRow, Board, 0).

testAllRows(SrcCol, SrcRow, DestCol, DestRow, Board):-
	DestRow == 9, !,
	fail.

testAllRows(SrcCol, SrcRow, DestCol, DestRow, Board):-
	NextRow is DestRow + 1,
	testAllRows(SrcCol, SrcRow, DestCol, NextRow, Board).

changeTurn(Game, NextBoard, ContinueGame):-
	getGameMode(Game, GameMode),
	getGameState(Game, GameState),
	(
		GameState == whiteToMove ->
		NextGameState = blackToMove;
		NextGameState = whiteToMove
	),
	ContinueGame = [NextBoard, NextGameState, GameMode], !.
