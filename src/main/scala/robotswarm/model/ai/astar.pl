//implement A* algorithm for path finding

% Definizione degli ostacoli
obstacle(3, 4).
obstacle(3, 5).
obstacle(4, 5).
obstacle(6, 7).
obstacle(6, 8).

heuristic(X1, Y1, X2, Y2, H) :-
    H is abs(X1 - X2) + abs(Y1 - Y2).


valid_cell(X, Y, Obstacles) :-
    X >= 0, X < 10, % X deve essere nei limiti della griglia 10x10
    Y >= 0, Y < 10, % Y deve essere nei limiti della griglia 10x10
    \+ member((X, Y), Obstacles). % La cella non deve essere un ostacolo

move(X, Y, NX, Y) :- NX is X + 1. % destra
move(X, Y, NX, Y) :- NX is X - 1. % sinistra
move(X, Y, X, NY) :- NY is Y + 1. % giù
move(X, Y, X, NY) :- NY is Y - 1. % su