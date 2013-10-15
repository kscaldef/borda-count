# Copyright © 2013 Bart Massey
# [This program is licensed under the "MIT License"]
# Please see the file COPYING in the source
# distribution of this software for license terms.

# Makefile for Borda Count Haskell demos

HC = ghc -Wall -O2
TARGETS = tab-iterate tab-map tab-transpose tab-fast

all: $(TARGETS)

tab-iterate: tab-iterate.hs
	$(HC) --make tab-iterate.hs

tab-map: tab-map.hs
	$(HC) --make tab-map.hs

tab-transpose: tab-transpose.hs
	$(HC) --make tab-transpose.hs

tab-fast: tab-fast.hs
	$(HC) --make tab-fast.hs

clean:
	-rm -f $(TARGETS) *.o *.hi
