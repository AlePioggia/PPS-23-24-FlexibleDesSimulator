matrix([
  [0, 1, 0, 0, 0],
  [0, 1, 0, 1, 0],
  [0, 0, 0, 1, 0],
  [0, 1, 1, 1, 0],
  [0, 0, 0, 0, 0]
]).

% Movement directions: down, up, right, left
direction((1, 0)).
direction((-1, 0)).
direction((0, 1)).
direction((0, -1)).

% Predicate to access an element of the matrix
matrix_element(X, Y, Element) :-
    matrix(M),
    nth(X, M, Row),
    nth(Y, Row, Element).

% nth predicate: accesses the N-th element of a list (0-based index)
nth(0, [Head|_], Head).
nth(N, [_|Tail], Element) :-
    N > 0,
    N1 is N - 1,
    nth(N1, Tail, Element).

% Checks if a position is valid and not an obstacle
valid((X, Y)) :-
    X >= 0, Y >= 0,
    matrix_element(X, Y, 0).

% Main function to find the path
find_path(Start, End, Path) :-
    dfs(Start, End, [Start], Path).

% Depth-First Search (DFS)
dfs(End, End, Visited, Path) :-
    reverse(Visited, Path).
dfs(Start, End, Visited, Path) :-
    direction((DX, DY)),
    Start = (X, Y),
    NX is X + DX,
    NY is Y + DY,
    NewPosition = (NX, NY),
    valid(NewPosition),
    \+ member(NewPosition, Visited),
    dfs(NewPosition, End, [NewPosition|Visited], Path).