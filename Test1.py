import heapq
from collections import Counter

import storm
import collections


class TopWords:

        def __init__(self, word, count):
         self.word = word
         self.count = count
         self.freq = 0

        def access(self):
         self.freq += 1

        def __lt__(self, other):
         return self.count < other.count

        def __str__(self):
         return str("{} : {}".format(self.word, self.count))





class TopNFinderBolt(storm.BasicBolt):
    # Initialize this instance
    def initialize(self, conf, context):
        self._conf = conf
        self._context = context
        #self._largest = []
        self._finalDict = {}
        self._counter = Counter()
        self._emptyset = set()
        self._hinput = []
        self._inputStream = []



        storm.logInfo("TopNFInder bolt instance starting...")

        #TODO:
        # Task: set N
        self._topN = conf['top-N']
        # End

        # Hint: Add necessary instance variables and classes if needed


    def process(self, tup):
        '''
        TODO:
        Task: keep track of the top N words
        Hint: implement efficient algorithm so that it won't be shutdown before task finished
              the algorithm we used when we developed the auto-grader is maintaining a N size min-heap
        '''
        word = tup.values[0]
        count = tup.values[1]
        found =0

        if word !="":

          self._counter[word] = TopWords(word,count)
          if len(self._hinput) == 0:
            heapq.heappush(self._hinput,self._counter[word])
            heapq.heapify(self._hinput)
            self._emptyset.add(word)
            for items in list((self._hinput)):
                      storm.logInfo(str(items))
                    
          for item in self._counter:
            
            if (self._counter[item] not in self._emptyset and int(self._counter[item].count) > int(self._hinput[0].count)) ==0:
               storm.logInfo("readinf item %s" % self._counter[item])
               '''if len(self._hinput) == 0:
                    storm.logInfo("In First")
                    heapq.heappush(self._hinput,self._counter[item])
                    heapq.heapify(self._hinput)
                    self._emptyset.add(self._counter[item].word)
                    for items in list((self._hinput)):
                      storm.logInfo(str(items))'''

               if len(self._hinput)< 10 and (int(self._hinput[0].count) <= int(self._counter[item].count)):
                   if self._counter[item].word not in self._emptyset:
                            heapq.heappush(self._hinput, self._counter[item])
                            heapq.heapify(self._hinput)
                            self._emptyset.add(self._counter[item].word)
                            for items in list((self._hinput)):
                                storm.logInfo("Current item in heap %s" % str(items))


               elif int(self._hinput[0].count) < int(self._counter[item].count):
                   storm.logInfo("comparing")
                   storm.logInfo("lowest count %s" % self._hinput[0])
                   
                   if (self._hinput[0].count) < int(self._counter[self._hinput[0].word].count):
                     storm.logInfo("replacing lower count item with highest count %s" % self._counter[self._hinput[0].word])
                     #self._emptyset.remove(self._hinput[0].word)
                     storm.logInfo("pop %s"% heapq.heapreplace(self._hinput,self._counter[self._hinput[0].word]))
                     heapq.heapify(self._hinput)

                   else:
                        if self._counter[item].word  in self._emptyset:
                            storm.logInfo("item found in list %s" % self._counter[item])
                            heapq.heapify(self._hinput)

                        else:
                            storm.logInfo("new item %s" % item)
                            self._emptyset.remove(self._hinput[0].word)
                            storm.logInfo("pop %s"% heapq.heapreplace(self._hinput,self._counter[item]))
                            heapq.heapify(self._hinput)
                            self._emptyset.add(self._counter[item].word)

                   for items in list((self._hinput)):
                        storm.logInfo("Current item in heap %s" % str(items))

                   #except KeyError:
                   #storm.logInfo("key not found")
               storm.logInfo("item not added %s" % self._counter[item])
           storm.logInfo("after complete iteration")
           storm.logInfo(str(self._emptyset))

            size = 1
            wordList = ""
            for  v in self._emptyset:
                if size < len(self._emptyset):
                    wordList +=str(v) + ','
                    size += 1
                else:
                    wordList +=str(v)
                #wordList  = str(largest)
                storm.logInfo(wordList)
                storm.emit(["top-N", wordList])
                # End'''


# Start the bolt when it's invoked
TopNFinderBolt().run()