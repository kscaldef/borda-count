-- Copyright © 2013 Bart Massey
-- Tabulate PNW Enlightened Logo votes
-- using Borda Count (or thereabouts).

import Data.Ord
import Data.List
import Data.Char
import Text.Printf

main :: IO ()
main = do
  voteText <- getContents
  -- Strategy: transpose to get all the first-place votes,
  -- second-place votes, etc.; replicate each place the
  -- appropriate number of times; jam all the vote letters
  -- together; count the number of occurrences of
  -- each. Finally, sort the candidates in descending vote
  -- count order and print.
  let votes = sortBy (comparing (Down . snd)) $
              filter ((/= '-') . fst) $
              map (\l -> (head l, length l)) $ group $ sort $
              concat $ concat $ zipWith replicate [6,5..1] $
              transpose $ lines $ map toLower voteText
  mapM_ (\(c, v) -> printf "%c: %d\n" c v) votes

