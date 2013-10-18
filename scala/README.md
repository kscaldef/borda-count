# Borda Count in Scala
Copyright © 2013 Kevin Scaldeferri

*This work is made available under the MIT License: please
see the file COPYING for license details.*

Implementations of Borda Count in Scala

To run:

sbt 'run-main votecount.RunAlgebird ../votes.txt'

or

sbt 'run-main votecount.RunScalaz ../votes.txt'

or

sbt 'run-main votecount.RunVars ../votes.txt'


to benchmark all implementations (which don't overflow the stack when fed votes20.txt),

sbt 'run-main votecount.RunBenchmark'
