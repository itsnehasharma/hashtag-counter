JFLAGS = -g
JCC = javac

default: hashtagCounter.class FibbTree.class Node.class

hashtagCounter.class: hashtagCounter.java
	$(JCC) $(JFLAGS) hashtagCounter.java

FibbTree.class: FibbTree.java
	$(JCC) $(JFLAGS) FibbTree.java

Node.class: Node.java
	$(JCC) $(JFLAGS) Node.java

clean:
	$(RM) *.class