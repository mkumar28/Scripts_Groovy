import string
import sys
 
def find_shortest_path( start, end):
            def graph = {'A': ['B', 'C'], 'B': ['C', 'D'],'C': ['D'], 'D': ['C'],'E': ['F'],'F': ['C']}

            dist = {start: [start]}
            q = deque(start)
            while len(q):
                at = q.popleft()
                for next in graph[at]:
                    if next not in dist:
                        dist[next] = [dist[at], next]
                        q.append(next)
            return dist.get(end)

find_shortest_path(sys.argv[1], sys.argv[2])


ma