#!/bin/sh
# Copyright © 2013 Bart Massey
# Double the votes.txt file a bunch of
# times to get large test instances.
cat votes.txt votes.txt >votes2.txt
for i in `seq 2 19`
do
    cat votes$i.txt votes$i.txt >votes`expr $i + 1`.txt
done    
