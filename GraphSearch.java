public abstract class GraphSearch {
protected Queue<Node> fringe;
protected HashSet<Node> closedList;
public GraphSearch() {
fringe = createFringe();
closedList = new HashSet<Node>(100);
}
protected abstract Queue<Node> createFringe();
public int getNodeExpanded() {
return closedList.size();
}
@Override
public Solution search(Puzzle puzzle) {
fringe.add(new Node(puzzle.getInitialState(), null, null));
while (!fringe.isEmpty()) {
Node selectedNode = fringe.poll();
if (puzzle.getGoalTest().isGoalState(selectedNode.getState())) {
return new Solution(selectedNode, getNodeExpanded());
}
closedList.add(selectedNode);
LinkedList<Node> expansion = selectedNode.expandNode();
for (Node n : expansion) {
if (!closedList.contains(n) && !fringe.contains(n))
fringe.add(n);
}
}
return new Solution(null, getNodeExpanded());
}
}
This is my A* code:
public class AStar extends GraphSearch implements InformedSearch {
private Heuristic heuristic;
public AStar(Heuristic heuristic) {
setHeuristic(heuristic);
}
public Heuristic getHeuristic() {
return heuristic;
}
@Override
public void setHeuristic(Heuristic heuristic) {
this.heuristic = heuristic;
}
@Override
protected Queue<Node> createFringe() {
return new PriorityQueue<Node>(1000, new Comparator<Node>() {
@Override
public int compare(Node o1, Node o2) {
o1.setH(heuristic.h(o1));
o2.setH(heuristic.h(o2));
o1.setF(o1.getG() + o1.getH());
o2.setF(o2.getG() + o2.getH());
if (o1.getF() < o2.getF())
return -1;
if (o1.getF() > o2.getF())
return 1;
return 0;
}
});
}
}
And this is my Manhattan Heuristic code:
@Override
public int h(Node n) {
int distance = 0;
ArrayList<Integer> board = n.getState().getBoard();
int[][] multiBoard = new int[N][N];
for (int i = 0; i < N; i++) {
for (int j = 0; j < N; j++) {
multiBoard[i][j] = board.get(i * N + j);
}
}
for (int i = 0; i < N; i++) {
for (int j = 0; j < N; j++) {
int value = multiBoard[i][j];
if (multiBoard[i][j] != 0) {
int targetX = (value - 1) / N;
int targetY = (value - 1) % N;
distance += Math.abs(i - targetX) + Math.abs(j - targetY);
}
}
}
return distance;
}