map_build([(NodeKey, NodeValue) | Tail], NewTree) :- map_build(Tail, Tree),
                                                     map_put(Tree, NodeKey, NodeValue, NewTree).
map_build([], nil) :- !.


map_get(tree_map(NodeKey, _, NodeLeft, _, _), Key, NewTree) :- Key < NodeKey,
                                                               map_get(NodeLeft, Key, NewTree).
map_get(tree_map(NodeKey, _, _, NodeRight, _), Key, NewTree) :- Key > NodeKey,
                                                                map_get(NodeRight, Key, NewTree).
map_get(tree_map(NodeKey, NodeValue, _, _, _), NodeKey, NodeValue) :- !.


map_put(tree_map(NodeKey, NodeValue, NodeLeft, NodeRight, NodeHeight), Key, Value, NewTree) :- Key < NodeKey,
                                                                                               map_put(NodeLeft, Key, Value, NewLeft),
                                                                                               subtract(tree_map(NodeKey, NodeValue, NewLeft, NodeRight, NodeHeight), NewTree).
map_put(tree_map(NodeKey, NodeValue, NodeLeft, NodeRight, NodeHeight), Key, Value, NewTree) :- Key > NodeKey,
                                                                                               map_put(NodeRight, Key, Value, NewRight),
                                                                                               subtract(tree_map(NodeKey, NodeValue, NodeLeft, NewRight, NodeHeight), NewTree).
map_put(tree_map(NodeKey, NodeValue, NodeLeft, NodeRight, NodeHeight), NodeKey, NewValue, tree_map(NodeKey, NewValue, NodeLeft, NodeRight, NodeHeight)) :- !.
map_put(nil, NewKey, NewValue, tree_map(NewKey, NewValue, nil, nil, 1)) :- !.

map_find(tree_map(NodeKey, _, _, _, _), NodeKey) :- !.
map_find(tree_map(NodeKey, _, NodeLeft, _, _), Key) :- Key < NodeKey, 
                                                       map_find(NodeLeft, Key).
map_find(tree_map(NodeKey, _, _, NodeRight, _), Key) :- Key > NodeKey,
                                                        map_find(NodeRight, Key).

map_putIfAbsent(Tree, Key, Value, NewTree) :- not map_find(Tree, Key),
                                              map_put(Tree, Key, Value, NewTree).
map_putIfAbsent(Tree, Key, Value, Tree) :- map_find(Tree, Key).

subtract(Tree, NewTree) :- calc_new_height(Tree, NewTree),
                          get_subtract(NewTree, Subtract),
                          not (Subtract = 2),
                          not (Subtract = -2).
subtract(Tree, NewTree) :- calc_new_height(Tree, tree_map(NodeKey, NodeValue, NodeLeft, NodeRight, NodeHeight)),
                          get_subtract(Tree, -2),
                          get_subtract(NodeLeft, SubtractLeft),
                          (SubtractLeft > 0 -> left_rotate(NodeLeft, NewLeft); NewLeft = NodeLeft),
                          right_rotate(tree_map(NodeKey, NodeValue, NewLeft, NodeRight, NodeHeight), NewTree).
subtract(Tree, NewTree) :- calc_new_height(Tree, tree_map(NodeKey, NodeValue, NodeLeft, NodeRight, NodeHeight)),
                          get_subtract(Tree, 2),
                          get_subtract(NodeRight, SubtractRight),
                          (SubtractRight < 0 -> right_rotate(NodeRight, NewRight); NewRight = NodeRight),
                          left_rotate(tree_map(NodeKey, NodeValue, NodeLeft, NewRight, NodeHeight), NewTree).


calc_new_height(tree_map(NodeKey, NodeValue, NodeLeft, NodeRight, _), tree_map(NodeKey, NodeValue, NodeLeft, NodeRight, NewHeight)) :- node_height(NodeLeft, FirstHeight),
                                                                                                                                  node_height(NodeRight, SecondHeight),
                                                                                                                                  get_new_height(FirstHeight, SecondHeight, NewHeight).


right_rotate(tree_map(NodeKey, NodeValue, tree_map(NodeLeftKey, NodeLeftValue, NodeLeftLeft, NodeLeftRight, NodeLeftHeight), NodeRight, NodeHeight), NewTree) :- calc_new_height(tree_map(NodeKey, NodeValue, NodeLeftRight, NodeRight, NodeHeight), NewRight),
                                                                                                                                                                 calc_new_height(tree_map(NodeLeftKey, NodeLeftValue, NodeLeftLeft, NewRight, NodeLeftHeight), NewTree).

left_rotate(tree_map(NodeKey, NodeValue, NodeLeft, tree_map(NodeRightKey, NodeRightValue, NodeRightLeft, NodeRightRight, NodeRightHeight), NodeHeight), NewTree) :- calc_new_height(tree_map(NodeKey, NodeValue, NodeLeft, NodeRightLeft, NodeHeight), NewLeft),
                                                                                                                                                                    calc_new_height(tree_map(NodeRightKey, NodeRightValue, NewLeft, NodeRightRight, NodeRightHeight), NewTree).


get_subtract(nil, 0) :- !.
get_subtract(tree_map(_, _, NodeLeft, NodeRight, _), Subtract) :- node_height(NodeLeft, FirstHeight),
                                                                node_height(NodeRight, SecondHeight),
                                                                Subtract is SecondHeight - FirstHeight.

get_new_height(X, Y, Z) :- X < Y, Z is Y + 1, !.
get_new_height(X, Y, Z) :- Z is X + 1, !.

node_height(tree_map(_, _, _, _, NodeHeight), NodeHeight) :- !.
node_height(nil, 0) :- !.


map_remove(tree_map(NodeKey, _, NodeLeft, NodeRight, _), NodeKey, NewTree) :- minimal_subtree(NodeRight, Minimal),
                                                                              Minimal = tree_map(MinimalKey, MinimalValue, _, _, MinimalHeight),
                                                                              minimal_subtree_remove(NodeRight, NewRight),
                                                                              subtract(tree_map(MinimalKey, MinimalValue, NodeLeft, NewRight, MinimalHeight), NewTree).
map_remove(tree_map(NodeKey, NodeValue, NodeLeft, NodeRight, NodeHeight), Key, NewTree) :- Key < NodeKey,
                                                                                           map_remove(NodeLeft, Key, NewLeft),
                                                                                           subtract(tree_map(NodeKey, NodeValue, NewLeft, NodeRight, NodeHeight), NewTree).
map_remove(tree_map(NodeKey, NodeValue, NodeLeft, NodeRight, NodeHeight), K, NewTree) :- K > NodeKey,
                                                                                         map_remove(NodeRight, K, NewRight),
                                                                                         subtract(tree_map(NodeKey, NodeValue, NodeLeft, NewRight, NodeHeight), NewTree).
map_remove(tree_map(NodeKey, NodeValue, NodeLeft, nil, NodeHeight), NodeKey, NodeLeft) :- !.
map_remove(nil, Key, nil) :- !.

minimal_subtree(tree_map(_, _, NodeLeft, _, _), NewTree) :- minimal_subtree(NodeLeft, NewTree).
minimal_subtree(tree_map(NodeKey, NodeValue, nil, NodeRight, NodeHeight), tree_map(NodeKey, NodeValue, nil, NodeRight, NodeHeight)) :- !.

minimal_subtree_remove(tree_map(NodeKey, NodeValue, NodeLeft, NodeRight, NodeHeight), NewTree) :- minimal_subtree_remove(NodeLeft, NewLeft),
                                                                                                  subtract(tree_map(NodeKey, NodeValue, NewLeft, NodeRight, NodeHeight), NewTree).
minimal_subtree_remove(tree_map(_, _, nil, NodeRight, _), NodeRight) :- !.

    
